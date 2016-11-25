package com.limitless.services.payment.PaymentService.resources;

import java.io.StringReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.limitless.services.engage.upi.UpiOrderResponseBean;
import com.limitless.services.engage.upi.dao.UpiOrder;
import com.limitless.services.engage.upi.dao.UpiOrderManager;
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
				upiOrder.setOrderAmount(bean.getOrderAmount());
				
				UpiOrderManager manager = new UpiOrderManager();
				manager.persist(upiOrder);
				
				int orderId = upiOrder.getOrderId();
				
				//Save Order in UPI
				String inputString = "APIKey="+ICICIUtil.APIKEY+"&SDKVersion="+ICICIUtil.APIVERSION+"&Package=com.limitless.engage&OrderId="+orderId+"&MID="+ICICIUtil.MERCHANTID+"&TAmt="+bean.getOrderAmount()+"&Currency=INR&CustomField1=&CustomField2=&CustomField3=";
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
				
				System.out.println(output);
				
				//Response XML Parsing
				SAXReader reader = new SAXReader();
				Document document = reader.read(new InputSource(new StringReader(output)));
				Element rootElement = document.getRootElement();
				List<Node> nodeList = document.selectNodes("MB/RS");
				if(nodeList.size()==1){
					for(Node node : nodeList){
						upiOrderResp.setOrderId(orderId);
						upiOrderResp.setMessage("Success");
					}
				}
				
			} catch (Exception e) {
				logger.error("API Error", e);
				throw new Exception("Internal Server Error");
			}
			return upiOrderResp;
		}

}
