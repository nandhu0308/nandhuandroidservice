package com.limitless.services.payment.PaymentService.resources;

import java.io.StringReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.limitless.services.engage.upi.UpiOrderBean;
import com.limitless.services.engage.upi.UpiOrderBean.OrderStatus;
import com.limitless.services.engage.upi.UpiOrderResponseBean;
import com.limitless.services.engage.upi.dao.UpiOrder;
import com.limitless.services.engage.upi.dao.UpiOrderManager;
import com.limitless.services.payment.PaymentService.PaymentTxnBean;
import com.limitless.services.payment.PaymentService.TxnResponseBean;
import com.limitless.services.payment.PaymentService.dao.PaymentTxn;
import com.limitless.services.payment.PaymentService.dao.PaymentTxnManager;
import com.limitless.services.payment.PaymentService.util.ICICIUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//UPI
@Path("/upi")
public class UpiOrderResource {
	
		final static Logger logger = Logger.getLogger(UpiOrderResource.class);
		Client client = RestClientUtil.createClient();

		@GET
		@Path("/getVersion")
		public Response getVersion() {
			String output = "1.0.0";
			return Response.status(200).entity(output).build();
		}

		@GET
		@Path("/order/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public UpiOrderBean getUpiOrder(@PathParam("id") int id) throws Exception {
			UpiOrderBean bean = new UpiOrderBean();
			try {
				logger.info("Id:" + id);
				UpiOrderManager manager = new UpiOrderManager();
				UpiOrder upiOrder = manager.findById(id);

				bean.setOrderId(upiOrder.getOrderId());
				bean.setCustomerId(upiOrder.getCustomerId());
				bean.setOrderAmount(upiOrder.getOrderAmount());
			} catch (Exception e) {
				logger.error("API Error", e);
				throw new Exception("Internal Server Error");
			}
			return bean;
		}

		@POST
		@Path("/order")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public UpiOrderResponseBean addUpiOrder(UpiOrderBean bean) throws Exception {
			UpiOrderResponseBean upiOrderResp = new UpiOrderResponseBean();

			try {
				UpiOrder upiOrder = new UpiOrder();
				upiOrder.setCustomerId(bean.getCustomerId());
				upiOrder.setSellerId(bean.getSellerId());
				upiOrder.setOrderAmount(bean.getOrderAmount());
				upiOrder.setOrderStatus("PAYMENT_INITIATED");
				
				UpiOrderManager manager = new UpiOrderManager();
				manager.persist(upiOrder);
				
				int orderId = upiOrder.getOrderId();
				
				//Save Order in UPI
				String inputString = "APIKey="+ICICIUtil.APIKEY+"&SDKVersion="+ICICIUtil.APIVERSION+"&Package=com.autodidact.android.paylimitless&OrderId="+orderId+"&MID="+ICICIUtil.MERCHANTID+"&TAmt="+bean.getOrderAmount()+"&Currency=INR&CustomField1=&CustomField2=&CustomField3=";
				String encryptedInputString = ICICIUtil.encrypt(ICICIUtil.RANDOMKEY, inputString);
				String salt = ICICIUtil.encrypt(ICICIUtil.SALTKEY, ICICIUtil.RANDOMKEY);
				String chkSum = ICICIUtil.generateChecksum(encryptedInputString);
				
				WebResource webResource = client.resource(ICICIUtil.ICICIUPIURL);
				
				MultivaluedMap<String, String> map = new MultivaluedMapImpl();
				map.add("OPERATIONID", "ISDK0008");
				map.add("inputString", encryptedInputString);
				map.add("salt", salt);
				map.add("chkSum", chkSum);
				
				ClientResponse response = webResource.type("application/x-www-form-urlencoded").
						post(ClientResponse.class, map);
				
				String output = response.getEntity(String.class).toString();
				
				System.out.println("Output : "+output);
				
				//Response XML Parsing
				SAXReader reader = new SAXReader();
				Document document = reader.read(new InputSource(new StringReader(output)));
				Element rootElement = document.getRootElement();
				List<Node> nodeList = document.selectNodes("MB/RS/OperationCode");
				if(nodeList.size()==1 && nodeList.get(0).getStringValue().equals("0000") ){
					upiOrderResp.setOrderId(orderId);
					upiOrderResp.setMessage("Success");
				} else {
					upiOrderResp.setOrderId(0);
					upiOrderResp.setMessage("Failure");
				}
			} catch (Exception e) {
				logger.error("API Error", e);
				throw new Exception("Internal Server Error");
			}
			return upiOrderResp;
		}
		
		@PUT
		@Path("/order")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public UpiOrderResponseBean updateUPIOrder(UpiOrderBean upiOrderBean) throws Exception {
			UpiOrderResponseBean upiOrderResponseBean = new UpiOrderResponseBean();

			try {
				UpiOrderManager manager = new UpiOrderManager();
				UpiOrder upiOrder;
				if(upiOrderBean.getOrderStatus().equals(OrderStatus.PAYMENT_SUCCESSFUL)){
					upiOrder = manager.updateUPIOrderSuccess(upiOrderBean.getOrderId(), upiOrderBean.getIciciTxnNo(), 
							upiOrderBean.getIciciTxnTime(), upiOrderBean.getOrderStatus().toString(), upiOrderBean.getOrderPaymentType());
				} else {
					upiOrder = manager.updateUPIOrderFailure(upiOrderBean.getOrderId(), upiOrderBean.getOrderStatus().toString(), upiOrderBean.getErrorCode());	
				}
				
				if(upiOrder != null){
					upiOrderResponseBean.setOrderId(upiOrder.getOrderId());
					upiOrderResponseBean.setMessage("Success");
				}
				
				}catch(Exception e){
					logger.error("API Error", e);
					throw new Exception("Internal Server Error");
				}
			return upiOrderResponseBean;
		}
		
		public static void main(String[] args) throws Exception{
			/*String output = "<MB><RQ><salt><![CDATA[2Toz%2FR0LVlZF5AGjQKSEhVLstuiuQLBTeS09awUS3yk%3D]]></salt><chkSum><![CDATA[5d04e6a401c39416e885681678938922]]></chkSum><inputString><![CDATA[hhhvKZxTSmSzeuMe9vdE0CxpNgEpcqtRqcN909LmSXgkMLVglmeyLT38Js7P32pnHJPGLIkgxmBK%0D%0AzQU789R1tSlA6JB8w%2FgLxRVesgoWxuZi9U7YcFbR%2BWLIDvMIG1UoVz0ECFFzce8ULic9h9rDbpT3%0D%0AmKHUFivwtn2SB5rrjmwoyuox3cHMk%2FU%2F1Rge7rcffD8kG68wbAM98Ou1sFNO9A%3D%3D]]></inputString><OPERATIONID><![CDATA[ISDK0008]]></OPERATIONID></RQ><RS><OperationStatus>0000</OperationStatus><OperationCode>0000</OperationCode><OperationErrorMessage>Order details saved successfully for merchant.				</OperationErrorMessage></RS></MB>";
			SAXReader reader = new SAXReader();
			Document document = reader.read(new InputSource(new StringReader(output)));
			Element rootElement = document.getRootElement();
			List<Node> nodeList = document.selectNodes("MB/RS");
			
			System.out.println(nodeList.get(0).toString());
			
			List<Node> nodeList2 = document.selectNodes("MB/RS/OperationCode");
			
			System.out.println(nodeList2.get(0).toString());
			
			if(nodeList.size()==1){
				upiOrderResp.setOrderId(orderId);
				upiOrderResp.setMessage("Success");
				
				System.out.println(nodeList2.get(0).getStringValue());
			}*/
		}
}
