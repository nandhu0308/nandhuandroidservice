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
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>   
<%  
response.setContentType("application/json");     
String accessKey = "XWMHQPX39XYF8NRBAQSM";     
String secretKey = "3d4621078f24fd82af3fce23dc74bbc4e334cbf4";     
String returnUrl = "http://ec2-54-186-117-110.us-west-2.compute.amazonaws.com:8080/LLCWeb/limitlessru";     

String amount = request.getParameter("amount");
//TODO
String buyerName = request.getParameter("bname");
String buyerEmail = request.getParameter("bemail");
String buyerPhone = request.getParameter("bphone");
String sellerId = request.getParameter("sid");

//Make Add Txn API call
ClientConfig clientConfig = new DefaultClientConfig();              
clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);     
Client client = Client.create(clientConfig);

WebResource webResource = client.resource("http://localhost:8080/LLCWeb/payment/trans");

PaymentTxnBean bean = new PaymentTxnBean();
bean.setSellerId(Integer.parseInt(sellerId));
//TODO
bean.setSellerName("Nandhana");
bean.setTxnAmount(Float.parseFloat(amount));
bean.setTxnStatus(TxnStatus.PAYMENT_INITIATED);

TxnResponseBean txnResponse = webResource.type("application/json").post(TxnResponseBean.class, bean);
System.out.println("Txn Id: " + txnResponse.getTxnId());

int txnId = txnResponse.getTxnId();

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