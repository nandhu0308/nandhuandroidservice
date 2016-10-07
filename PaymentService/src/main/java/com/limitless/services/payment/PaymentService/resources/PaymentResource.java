
package com.limitless.services.payment.PaymentService.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageCustomerManager;
import com.limitless.services.payment.PaymentService.PaymentTxnBean;
import com.limitless.services.payment.PaymentService.PaymentTxnBean.TxnStatus;
import com.limitless.services.payment.PaymentService.SellerTxnHistoryBean;
import com.limitless.services.payment.PaymentService.SplitRequestBean;
import com.limitless.services.payment.PaymentService.SplitResponseBean;
import com.limitless.services.payment.PaymentService.TxnHistoryBean;
import com.limitless.services.payment.PaymentService.TxnResponseBean;
import com.limitless.services.payment.PaymentService.dao.PaymentTxn;
import com.limitless.services.payment.PaymentService.dao.PaymentTxnManager;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/*
 * @author veejay.developer@gmail.com
 * ©www.limitlesscircle.com 
 */
import java.util.HashMap;

//CITRUSPAY
@Path("/payment")
public class PaymentResource {
	
		final static Logger logger = Logger.getLogger(PaymentResource.class);
	
		@GET
		@Path("/getVersion")
		public Response getVersion() {
			String output = "1.0.0";
			return Response.status(200).entity(output).build();
		}
		
		@GET
	    @Path("/trans/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public PaymentTxnBean getPaymentTxn(@PathParam("id") int id) throws Exception{
			PaymentTxnBean bean = new PaymentTxnBean();
			try {
				logger.info("Id:" + id);
				PaymentTxnManager manager = new PaymentTxnManager();
				PaymentTxn paymentTxn = manager.findById(id);
				
				bean.setTxnId(paymentTxn.getTxnId());
				bean.setSellerId(paymentTxn.getSellerId());
				bean.setTxnAmount(paymentTxn.getTxnAmount());
				bean.setSellerName(paymentTxn.getSellerName());
				
			} catch (Exception e) {
				logger.error("API Error", e);
				throw new Exception("Internal Server Error");
			}
			return bean;
		}
		
		@POST
	    @Path("/trans")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public TxnResponseBean addTxn(PaymentTxnBean bean) throws Exception{
			TxnResponseBean txnResp = new TxnResponseBean();
			
			try {
				PaymentTxn paymentTxn = new PaymentTxn();
				paymentTxn.setEngageCustomerId(bean.getEngageCustomerId());
				paymentTxn.setSellerId(bean.getSellerId());
				paymentTxn.setTxnAmount(bean.getTxnAmount());
				paymentTxn.setSellerName(bean.getSellerName());
				paymentTxn.setTxnStatus(bean.getTxnStatus().toString());
				paymentTxn.setSellerDeviceId(bean.getSellerDeviceId());
				
				PaymentTxnManager manager = new PaymentTxnManager();
				manager.persist(paymentTxn);
				
				txnResp.setTxnId(paymentTxn.getTxnId());
				txnResp.setMessage("Success");
			} catch (Exception e) {
				logger.error("API Error", e);
				throw new Exception("Internal Server Error");
			}
			return txnResp;
		}
		
		@PUT
	    @Path("/trans")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public TxnResponseBean updateTxn(PaymentTxnBean paymentTxnBean) throws Exception{
			TxnResponseBean txnResp = new TxnResponseBean();
			
			try {
				//TODO - currently only the Status
				PaymentTxnManager manager = new PaymentTxnManager();
				PaymentTxn paymentTxn = manager.updateTxn(paymentTxnBean.getTxnId(), paymentTxnBean.getTxnStatus().toString());
				
				EngageCustomerManager customerManager = new EngageCustomerManager();
				EngageCustomer customer = customerManager.findById(paymentTxn.getEngageCustomerId());
				
				txnResp.setTxnId(paymentTxn.getTxnId());
				txnResp.setMessage("Success");
				txnResp.setAmount(paymentTxn.getTxnAmount());
				txnResp.setDate(paymentTxn.getTxnUpdatedTime().toString());
				txnResp.setName(customer.getCustomerName());
				txnResp.setSellerDeviceId(paymentTxn.getSellerDeviceId());
			} catch (Exception e) {
				logger.error("API Error", e);
				throw new Exception("Internal Server Error");
			}
			return txnResp;
		}
		
		@GET
		@Path("/trans/customer/{customerId}")
		@Produces(MediaType.APPLICATION_JSON)
		public List<TxnHistoryBean> getHistory(@PathParam("customerId") int customerId) throws Exception{
			List<TxnHistoryBean> historyBeanList = new ArrayList<TxnHistoryBean>();
			
			try {
				PaymentTxnManager manager = new PaymentTxnManager();
				List<PaymentTxn> paymentHistory = manager.getTxnHistory(customerId);
				
				if(paymentHistory != null){
					System.out.println("Size : " + paymentHistory.size());
				} else {
					System.out.println("List Null");
				}
				
				for(PaymentTxn bean : paymentHistory){
					TxnHistoryBean historyBean = new TxnHistoryBean();
					historyBean.setTxnId(bean.getTxnId());
					historyBean.setCustomerId(bean.getEngageCustomerId());
					historyBean.setSellerId(bean.getSellerId());
					historyBean.setSellerName(bean.getSellerName());
					historyBean.setTxtAmount(bean.getTxnAmount());
					historyBean.setCitrusMpTxnId(bean.getCitrusMpTxnId());
					historyBean.setSplitId(bean.getSplitId());
					historyBean.setTxtStatus(bean.getTxnStatus().toString());
					historyBean.setTxnTime(bean.getTxnUpdatedTime().toString());
					historyBeanList.add(historyBean);
					historyBean = null;
				}
			} catch (Exception e) {
				logger.error("API Error", e);
				throw new Exception("Internal Server Error");
			}
			return historyBeanList;
		}
		
		@GET
		@Path("/trans/seller/{sellerId}")
		@Produces(MediaType.APPLICATION_JSON)
		public SellerTxnHistoryBean getSellerTxns(@PathParam("sellerId") int sellerId)throws Exception{
			SellerTxnHistoryBean bean = null;
			try{
				PaymentTxnManager manager = new PaymentTxnManager();
				bean = manager.getSellerTxns(sellerId);
			}
			catch(Exception e){
				logger.error("API Error", e);
				throw new Exception("Internal Server Exception");
			}
			return bean;
		}
		
		@PUT
	    @Path("/trans/{id}/split")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public SplitResponseBean splitTxn(@PathParam("id") int id, SplitRequestBean splitReq) throws Exception{
			SplitResponseBean splitResp = new SplitResponseBean();
			int citrusMpTxnId = splitReq.getCitrusMpTxnId();
			try{
				logger.info("Id:" + id);
				
				PaymentTxnManager manager = new PaymentTxnManager();
				PaymentTxn paymentTxn = manager.findById(id);
				
				int txnId = paymentTxn.getTxnId();
				int sellerId = paymentTxn.getSellerId();
				String sellerDeviceId = paymentTxn.getSellerDeviceId();
				int customerId = paymentTxn.getEngageCustomerId();
				String txnDate = paymentTxn.getTxnUpdatedTime().toString();
				
				EngageCustomerManager customerManager = new EngageCustomerManager();
				EngageCustomer engageCustomer = customerManager.findById(customerId);
				
				String customerName = engageCustomer.getCustomerName();				
				
				String merchantSplitRef = paymentTxn.getSellerName() + "_" + System.currentTimeMillis();
				//TODO
				double feePercent = 2.00;
				double txnAmount = paymentTxn.getTxnAmount();
				//TODO
				double feeAmount = 0.00;//(txnAmount * feePercent) / 100;
				double splitAmount = txnAmount - feeAmount; 
				
				//Make Split API call
				ClientConfig clientConfig = new DefaultClientConfig();              
				clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);     
				Client client = Client.create(clientConfig);
				
				Map<String,Object> splitRequest = new HashMap<String,Object>();
				splitRequest.put("trans_id", citrusMpTxnId);
				splitRequest.put("seller_id", sellerId);
				splitRequest.put("merchant_split_ref", merchantSplitRef);
				splitRequest.put("split_amount", splitAmount);
				splitRequest.put("fee_amount", feeAmount);
				splitRequest.put("auto_payout", 1);
				
				WebResource webResource = client.resource("https://splitpay.citruspay.com/marketplace/split");

				ClientResponse splitResponse = webResource.accept("application/json").
						type("application/json")
						.header("auth_token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2Nlc3Nfa2V5IjoiOTZLMzRRNEE4SThOUkVIN05WT1oiLCJleHBpcmVzIjoiMjAxNi0xMC0xMVQxMTo0MTo1MS44NTRaIiwiY2FuX3RyYW5zYWN0IjoxLCJhZG1pbiI6MH0.c3JEDaOW6is2xAk2uwuiHSt3LGza4Iq2XMjJEpvA8Bg")
						.post(ClientResponse.class, splitRequest);
				
				String splitResponseStr = splitResponse.getEntity(Object.class).toString();
				logger.info(splitResponseStr);
				
				//TODO
				/*JSONObject jsonObject = new JSONObject(splitResponseStr);
				int splitId = Integer.parseInt(jsonObject.get("split_id").toString());*/
				
				//TODO
				splitResponseStr = splitResponseStr.substring(1,splitResponseStr.length()-2);
				splitResponseStr = splitResponseStr.split(",")[0];
				String[] splitArr =  splitResponseStr.split("=");
				int splitId = 0;
				if(splitArr[0].equals("split_id")){
					splitId = Integer.parseInt(splitArr[1]);
				}

				//TODO
				paymentTxn = manager.updateSplitId(txnId, citrusMpTxnId, splitId, TxnStatus.PAYMENT_SUCCESSFUL.toString());
				
				splitResp.setSplitId(paymentTxn.getSplitId());
				splitResp.setMessage("Success");
				splitResp.setName(customerName);
				splitResp.setAmount(txnAmount);
				splitResp.setDate(txnDate);
				splitResp.setSellerDeviceId(sellerDeviceId);
				
			} catch(Exception e){
				logger.error("API Error", e);
				throw new Exception("Internal Server Error");
			}
			
			return splitResp;
		}
		
}
