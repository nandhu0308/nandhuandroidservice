<%@page import="com.limitless.services.engage.SellerDeviceIdRespBean"%>
<%@page import="com.limitless.services.payment.PaymentService.CreditRespBean"%>
<%@page import="com.limitless.services.payment.PaymentService.CreditBean"%>
<%@page import="com.limitless.services.payment.PaymentService.PaymentTxnBean.TxnStatus"%>
<%@page import="com.limitless.services.payment.PaymentService.TxnResponseBean"%>
<%@page import="com.sun.jersey.api.client.ClientResponse"%>
<%@page import="com.sun.jersey.api.json.JSONConfiguration"%>
<%@page import="com.sun.jersey.api.client.config.DefaultClientConfig"%>
<%@page import="com.limitless.services.payment.PaymentService.PaymentTxnBean"%>
<%@page import="com.sun.jersey.api.client.WebResource"%>
<%@page import="com.sun.jersey.api.client.Client"%>
<%@page import="com.sun.jersey.api.client.config.ClientConfig"%>
<%@page import="java.text.Format" %>  
<%@page import="javax.crypto.Mac" %>   
<%@page import="javax.crypto.spec.SecretKeySpec" %>   
<%@page import="java.util.Random" %>  
<%@page import="com.limitless.services.payment.PaymentService.util.RestClientUtil" %>  
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>   
<%  
response.setContentType("application/json");     
String accessKey = "96K34Q4A8I8NREH7NVOZ";     
String secretKey = "bc3fb974fd550bd26083862dda1591c80cf5da8f";     
//String returnUrl = "http://ec2-54-186-117-110.us-west-2.compute.amazonaws.com:8080/LLCWeb/limitlessru";
String returnUrl = "https://services.beinglimitless.in/limitlessru";

//String returnUrl = "http://awseb-e-u-awsebloa-1kbd6p0esshsu-1368873989.us-west-2.elb.amazonaws.com/limitlessru";

String amount = request.getParameter("amount");
//TODO
String buyerName = request.getParameter("bname");
String buyerEmail = request.getParameter("bemail");
String buyerPhone = request.getParameter("bphone");
String sellerId = request.getParameter("sid");
String citrusSellerId = request.getParameter("csid");
String buyerId = request.getParameter("bid");
String sellerName = request.getParameter("sname");
//String sellerDeviceId = request.getParameter("sdid");
String userString = System.getProperty("AUTH_STRING");
//String userString = "MTAwMDAwOjJlNjJhMjI0YjQxNDRkZDFiZjdmZWU3YTJlM2M1NjliMzI1MzQyYTIwODE4NjU4ZTdlMjMyNmRlMWM4YzZlZWE=";
String orderId = request.getParameter("orderId");
if(orderId == null){
	orderId = "0";
}
String creditAmountStr = request.getParameter("credamt");
String debitAmountStr = request.getParameter("debamt");
String txnNotes = request.getParameter("notes");
if(txnNotes==null)
{
	txnNotes="NA";
}

//Make Add Txn API call
//ClientConfig clientConfig = new DefaultClientConfig();              
//clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);     
Client client = RestClientUtil.createClient();

//Retrieve Seller Device ID from Engage Seller Table
WebResource sellerResource = client.resource("https://services.beinglimitless.in/engage/merchant/seller/" + sellerId + "/deviceidbg");
SellerDeviceIdRespBean sellerDeviceIdRespBean = sellerResource.accept("application/json").header("Authorization","Basic " + userString).get(SellerDeviceIdRespBean.class);
String sellerDeviceId = sellerDeviceIdRespBean.getSellerDeviceId();
if(sellerDeviceId == null){
	sellerDeviceId = "NA";
}

WebResource webResource = client.resource("https://services.beinglimitless.in/engage/payment/trans");


PaymentTxnBean bean = new PaymentTxnBean();
bean.setSellerId(Integer.parseInt(sellerId));
bean.setCitrusSellerId(Integer.parseInt(citrusSellerId));
//TODO
bean.setEngageCustomerId(Integer.parseInt(buyerId));
bean.setSellerName(sellerName);
bean.setTxnAmount(Float.parseFloat(amount));
bean.setTxnStatus(TxnStatus.PAYMENT_INITIATED);
bean.setSellerDeviceId(sellerDeviceId);
bean.setOrderId(Integer.parseInt(orderId));
if(txnNotes.equals("NA")){
	bean.setTxnNotes("NA");
}
else{
	bean.setTxnNotes(txnNotes);
}

TxnResponseBean txnResponse = webResource.type("application/json").header("Authorization","Basic " + userString).post(TxnResponseBean.class, bean);
System.out.println("Txn Id: " + txnResponse.getTxnId());

int txnId = txnResponse.getTxnId();

//Credit Call
if(creditAmountStr != null || debitAmountStr != null){
	float creditAmount = 0;
	float debitAmount = 0;
	if(creditAmountStr != null){
		creditAmount = Float.parseFloat(creditAmountStr);
	}
	if(debitAmountStr != null){
		debitAmount = Float.parseFloat(debitAmountStr);
	}
	
	if(creditAmount > 0 || debitAmount > 0){
		WebResource creditResource = client.resource("https://services.beinglimitless.in/engage/payment/credit");
		
		CreditBean creditBean = new CreditBean();
		creditBean.setTxnId(txnId);
		creditBean.setCreditTemp(creditAmount);
		creditBean.setDebitTemp(debitAmount);
		creditBean.setSellerId(Integer.parseInt(citrusSellerId));
		creditBean.setCustomerId(Integer.parseInt(buyerId));
		creditBean.setMerchantId(Integer.parseInt(sellerId));
		
		CreditRespBean creditResponse = creditResource.type("application/json").header("Authorization","Basic " + userString).post(CreditRespBean.class, creditBean);
		System.out.println("Credit Id: " + creditResponse.getCreditId());
	}
}
//End of Credit Call

String dataString = "merchantAccessKey=" + accessKey + "&transactionId=" + txnId + "&amount=" + amount;    
SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");    
Mac mac = Mac.getInstance("HmacSHA1");     
mac.init(secretKeySpec);     
byte []hmacArr = mac.doFinal(dataString.getBytes());    
StringBuilder build = new StringBuilder();     
 for (byte b : hmacArr) {         
	 build.append(String.format("%02x", b));     
  }   
String hmac = build.toString();     
StringBuilder amountBuilder = new StringBuilder();     
amountBuilder.append("\"value\":\"").append(amount).append("\"").append(",\"currency\":\"INR\"");    
StringBuilder resBuilder = new StringBuilder("{");    
resBuilder.append("\"merchantTxnId\"").append(":").append("\"").append(txnId).append("\"")        
	  .append(",")        
	  .append("\"requestSignature\"").append(":").append("\"").append(hmac).append("\"")        
	  .append(",")        
	  .append("\"merchantAccessKey\"").append(":").append("\"").append(accessKey).append("\"")        
	  .append(",")        
	  .append("\"returnUrl\"").append(":").append("\"").append(returnUrl).append("\"")        
	  .append(",")        
	  .append("\"amount\"").append(":").append("{").append(amountBuilder).append("}")        
	  .append("}");  
	  out.print(resBuilder);   %>