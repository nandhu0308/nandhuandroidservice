package com.limitless.services.payment.PaymentService.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.limitless.services.payment.PaymentService.PaymentTxnBean;
import com.limitless.services.payment.PaymentService.SplitResponseBean;
import com.limitless.services.payment.PaymentService.dao.PaymentTxn;
import com.limitless.services.payment.PaymentService.dao.PaymentTxnManager;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

//CITRUSPAY
@Path("/payment")
public class PaymentResource {
	
		@GET
		@Path("/getVersion")
		public Response getVersion() {
			String output = "1.0.0";
			return Response.status(200).entity(output).build();
		}
		
		@GET
	    @Path("/trans/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public PaymentTxnBean getPaymentTxn(@PathParam("id") int id){
			System.out.println("Id:" + id);
			PaymentTxnManager manager = new PaymentTxnManager();
			PaymentTxn paymentTxn = manager.findById(id);
			
			PaymentTxnBean bean = new PaymentTxnBean();
			bean.setTxnId(paymentTxn.getTxnId());
			bean.setSellerId(paymentTxn.getSellerId());
			bean.setTxnAmount(paymentTxn.getTxnAmount());
			bean.setSellerName(paymentTxn.getSellerName());
			
			return bean;
		}
		
		@POST
	    @Path("/trans")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response addTxn(PaymentTxnBean bean){
			
			PaymentTxn paymentTxn = new PaymentTxn();
			paymentTxn.setTxnId(bean.getTxnId());
			paymentTxn.setSellerId(bean.getSellerId());
			paymentTxn.setTxnAmount(bean.getTxnAmount());
			paymentTxn.setSellerName(bean.getSellerName());
			
			PaymentTxnManager manager = new PaymentTxnManager();
			manager.persist(paymentTxn);
			
			return Response.status(200).entity("success").build();
		}
		
		@POST
	    @Path("/trans/{id}/split")
		@Produces(MediaType.APPLICATION_JSON)
		public SplitResponseBean splitTxn(@PathParam("id") int id){
			SplitResponseBean splitResp = new SplitResponseBean();
			try{
				System.out.println("Id:" + id);
				
				PaymentTxnManager manager = new PaymentTxnManager();
				PaymentTxn paymentTxn = manager.findById(id);
				
				int txnId = paymentTxn.getTxnId();
				int sellerId = paymentTxn.getSellerId();
				String merchantSplitRef = paymentTxn.getSellerName() + "_" + System.currentTimeMillis();
				//TODO
				double feePercent = 2.00;
				double txnAmount = paymentTxn.getTxnAmount();
				double feeAmount = (txnAmount * feePercent) / 100;
				double splitAmount = txnAmount - feeAmount; 
				
				//Make Split API call
				ClientConfig clientConfig = new DefaultClientConfig();              
				clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);     
				Client client = Client.create(clientConfig);
				
				Map<String,Object> splitRequest = new HashMap<String,Object>();
				splitRequest.put("trans_id", txnId);
				splitRequest.put("seller_id", sellerId);
				splitRequest.put("merchant_split_ref", merchantSplitRef);
				splitRequest.put("split_amount", splitAmount);
				splitRequest.put("fee_amount", feeAmount);
				splitRequest.put("auto_payout", 1);
				
				WebResource webResource = client.resource("https://splitpaysbox.citruspay.com/marketplace/split");

				ClientResponse splitResponse = webResource.accept("application/json").
						type("application/json")
						.header("auth_token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2Nlc3Nfa2V5IjoiWFdNSFFQWDM5WFlGOE5SQkFRU00iLCJleHBpcmVzIjoiMjAxNi0wOC0yN1QxNzo1MzowMC42NzBaIiwiY2FuX3RyYW5zYWN0IjoxLCJhZG1pbiI6MH0.eUKRRTvUioJlnm_c9fFRhc_kwU2yvHkCyYzab2p19mo")
						.post(ClientResponse.class, splitRequest);
				
				String splitResponseStr = splitResponse.getEntity(Object.class).toString();
				System.out.println(splitResponseStr);
				
				/*JSONObject jsonObject = new JSONObject(splitResponseStr);
				String splitIdStr = jsonObject.get("split_id").toString();*/
				//TODO
				splitResponseStr = splitResponseStr.substring(1,splitResponseStr.length()-2);
				splitResponseStr = splitResponseStr.split(",")[0];
				String splitIdStr =  splitResponseStr.split("=")[1];
						
				int splitId = Integer.parseInt(splitIdStr);
				
				//TODO
				paymentTxn = manager.updateSplitId(txnId, splitId);
				
				splitResp.setSplitId(paymentTxn.getSplitId());
				splitResp.setMessage("Success");
			} catch(Exception e){
				e.printStackTrace();
			}
			
			return splitResp;
		}
		
}
