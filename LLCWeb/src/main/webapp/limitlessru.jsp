<%@page import="com.limitless.services.payment.PaymentService.PaymentTxnBean.TxnStatus"%>
<%@page import="com.limitless.services.payment.PaymentService.PaymentTxnBean"%>
<%@page import="com.limitless.services.payment.PaymentService.TxnResponseBean"%>
<%@page import="com.limitless.services.payment.PaymentService.SplitRequestBean"%>
<%@page import="com.limitless.services.payment.PaymentService.SplitResponseBean"%>
<%@page import="com.sun.jersey.api.client.WebResource"%>
<%@page import="com.sun.jersey.api.client.Client"%>
<%@page import="com.sun.jersey.api.json.JSONConfiguration"%>
<%@page import="com.sun.jersey.api.client.config.DefaultClientConfig"%>
<%@page import="com.sun.jersey.api.client.config.ClientConfig"%>
<%@page import="java.util.Set" %>
<%@page import="java.util.Hashtable" %>   
<%@page import="javax.crypto.Mac" %>   
<%@page import="javax.crypto.spec.SecretKeySpec" %>   
<%@page import="java.util.Enumeration" %>   
<%@page import="java.util.HashMap" %>   
<%@page import="com.limitless.services.payment.PaymentService.util.RestClientUtil" %>
<%@page import="org.json.*" %>
<%@page import="com.sun.jersey.api.client.ClientResponse" %>
<%@page import="java.util.List" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>  
<% 
String secretKey = "bc3fb974fd550bd26083862dda1591c80cf5da8f";   	
Hashtable<String, String> reqValMap = new Hashtable<String, String>(){          
public synchronized String toString() {            
Enumeration<String> keys = keys();            
StringBuffer buff = new StringBuffer("{");            
while(keys.hasMoreElements()){                
String key = keys.nextElement();                  
String value = get(key);   				                  
buff.append("'").append(key).append("'")
	.append(":")
	.append("'").append(value).append("'")
	.append(',');             
}              
buff = new StringBuffer(buff.toString().substring(0, buff.toString().length()-1));              
buff.append("}");              
return buff.toString();         
}     
};	      
Enumeration<String> parameterList = request.getParameterNames();      
while(parameterList.hasMoreElements())
{
String paramName = parameterList.nextElement();
String paramValue = request.getParameter(paramName);          
reqValMap.put(paramName, paramValue);      
}      
String dataString = new StringBuilder()                              
				.append(request.getParameter("TxId"))                             
				.append(request.getParameter("TxStatus"))                              
				.append(request.getParameter("amount"))                              
				.append(request.getParameter("pgTxnNo"))                                                         
				.append(request.getParameter("issuerRefNo"))                               
				.append(request.getParameter("authIdCode"))                              
				.append(request.getParameter("firstName"))                              
				.append(request.getParameter("lastName"))                             
				.append(request.getParameter("pgRespCode"))                              
				.append(request.getParameter("addressZip"))                              
				.toString();   	      
SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");     
Mac mac = Mac.getInstance("HmacSHA1");      
mac.init(secretKeySpec);      
byte []hmacArr = mac.doFinal(dataString.getBytes());      
StringBuilder build = new StringBuilder();      
for (byte b : hmacArr) {          
build.append(String.format("%02x", b));      
}      
String hmac = build.toString();      
String reqSignature = request.getParameter("signature");      
System.out.println("txn ID : " + request.getParameter("TxId"));  	
System.out.println("RESPONSE " + reqValMap.toString());  
System.out.println("RESPONSE " + "THIS IS TEST"); 

//Backend Payment Service Call

//ClientConfig clientConfig = new DefaultClientConfig();
//clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);     
Client client = RestClientUtil.createClient();

String respCode = request.getParameter("pgRespCode");
String txnIdStr = request.getParameter("TxId");
String userString = System.getProperty("AUTH_STRING");

if(respCode.equals("0")){
	SplitRequestBean splitReqbean = new SplitRequestBean();
	String citrusMpTxnIdStr = request.getParameter("marketplaceTxId");
	int citrusMpTxnId = Integer.parseInt(citrusMpTxnIdStr);
	System.out.println("citrusMpTxnId : " + citrusMpTxnId);
	splitReqbean.setCitrusMpTxnId(citrusMpTxnId);

	WebResource webResource = client.resource("https://services.beinglimitless.in/engage/payment/trans").path(txnIdStr).path("split");
	SplitResponseBean splitRespBean = webResource.type("application/json").header("Authorization", "Basic " + userString).accept("application/json").put(SplitResponseBean.class, splitReqbean);
	
	System.out.println("Split Id" + splitRespBean.getSplitId());
	
	List<String> sellerDeviceIds = splitRespBean.getSellerDeviceIds();
	
	for(String deviceId : sellerDeviceIds){
		String customerName = splitRespBean.getName();
		double amount = splitRespBean.getAmount();
		String date = splitRespBean.getDate();
		String msg = splitRespBean.getMessage();
		
		String body = "Recieved Rs. "+amount+" from "+customerName;
		
		JSONObject notification = new JSONObject();
		notification.put("title", msg);
		notification.put("body", body);
		
		JSONObject data = new JSONObject();
		data.put("customerName", customerName);
		data.put("amount", amount);
		data.put("date", date);
		
		JSONObject payload = new JSONObject();
		payload.put("to", deviceId);
		payload.put("notification", notification);
		payload.put("data", data);
		
		WebResource webResource2 = client.resource("https://fcm.googleapis.com/fcm/send");
		ClientResponse clientResponse = webResource2.type("application/json").header("Authorization","key=AIzaSyCE49LX2u8Op-LbqidMJfcKlH4Bh5opUos").post(ClientResponse.class, payload.toString());
	    System.out.println(clientResponse.getStatus());
	    System.out.println(clientResponse.getEntity(String.class));
	}
	
} else {
	WebResource webResource = client.resource("https://services.beinglimitless.in/engage/payment/trans");
	
	PaymentTxnBean paymentTxnBean = new PaymentTxnBean();
	paymentTxnBean.setTxnId(Integer.parseInt(txnIdStr));
	paymentTxnBean.setTxnStatus(TxnStatus.PAYMENT_FAILED);
	TxnResponseBean txnResponse = webResource.type("application/json").header("Authorization", "Basic " + userString).accept("application/json").put(TxnResponseBean.class, paymentTxnBean);
	System.out.println("Txn Id: " + txnResponse.getTxnId() + " Status: " + txnResponse.getMessage());
	
	String name = txnResponse.getName();
	String date = txnResponse.getDate();
	double amount = txnResponse.getAmount();
	String sellerDeviceId = txnResponse.getSellerDeviceId();
	
	String body = "Failed to Recieve Rs. "+amount+" from "+name;
	
	JSONObject notification = new JSONObject();
	notification.put("title", "Transaction Not Successful");
	notification.put("body", body);
	
	JSONObject data = new JSONObject();
	data.put("name", name);
	data.put("amount", amount);
	data.put("date", date);
	
	JSONObject payload = new JSONObject();
	payload.put("to", sellerDeviceId);
	payload.put("data", data);
	payload.put("notification", notification);
	
	WebResource webResource2 = client.resource("https://fcm.googleapis.com/fcm/send");
	ClientResponse clientResponse = webResource2.type("application/json").header("Authorization","key=AIzaSyCE49LX2u8Op-LbqidMJfcKlH4Bh5opUos").post(ClientResponse.class, payload.toString());
    System.out.println(clientResponse.getStatus());
    System.out.println(clientResponse.getEntity(String.class));
}


%>   

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" http://www.w3.org/TR/html4/loose.dtd">   
<html>      
<head>          
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">          
<title>Redirect URL</title>          
<script type="text/javascript">          
function postResponse(data) {                  
document.write(data);                  
CitrusResponse.pgResponse(data);          
}          
function postResponseiOS() {                  
return jsonObject;          
}                 
document.write(JSON.stringify(<%=reqValMap%>));              
var jsonObject;              
if('<%=hmac%>' === '<%=request.getParameter("signature")%>')
{                  
jsonObject = JSON.stringify(<%=reqValMap%>);                  
postResponse(jsonObject);                  
document.write(jsonObject);             
}else
{                     
var responseObj = {                         
"Error" : "Transaction Failed",                         
"Reason" : "Signature Verification Failed"                     
};                     
jsonObject = JSON.stringify(responseObj);
postResponse(jsonObject);
document.write(jsonObject);
}      
</script>      
</head>      
<body>        
</body>   
</html>