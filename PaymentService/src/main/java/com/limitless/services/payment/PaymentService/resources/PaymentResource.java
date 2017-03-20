package com.limitless.services.payment.PaymentService.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.limitless.services.engage.MerchantRequestListBean;
import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageCustomerManager;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.dao.EngageSellerManager;
import com.limitless.services.engage.order.OrderMailResponseBean;
import com.limitless.services.engage.order.OrderPaymentModeUpdateResponseBean;
import com.limitless.services.engage.order.OrderStatusResponseBean;
import com.limitless.services.engage.order.dao.OrdersManager;
import com.limitless.services.engage.restaurants.RestaurantOrderStatusUpdateResponseBean;
import com.limitless.services.engage.restaurants.dao.RestaurantManager;
import com.limitless.services.payment.PaymentService.CreditBean;
import com.limitless.services.payment.PaymentService.CreditReminderRequestBean;
import com.limitless.services.payment.PaymentService.CreditReminderResponseBean;
import com.limitless.services.payment.PaymentService.CreditRespBean;
import com.limitless.services.payment.PaymentService.CreditTransRequestBean;
import com.limitless.services.payment.PaymentService.CreditTransResponseBean;
import com.limitless.services.payment.PaymentService.CustomerCreditResponseBean;
import com.limitless.services.payment.PaymentService.CustomerTxnHistoryBean;
import com.limitless.services.payment.PaymentService.DataBean;
import com.limitless.services.payment.PaymentService.GeneralSellerTxnHistoryBean;
import com.limitless.services.payment.PaymentService.InventoryUpdateResponseBean;
import com.limitless.services.payment.PaymentService.MasterTxnRequestBean;
import com.limitless.services.payment.PaymentService.MasterTxnResponseBean;
import com.limitless.services.payment.PaymentService.MessageBean;
import com.limitless.services.payment.PaymentService.MessageResponseBean;
import com.limitless.services.payment.PaymentService.NotificationRequestBean;
import com.limitless.services.payment.PaymentService.NotificationResponseBean;
import com.limitless.services.payment.PaymentService.PaymentTxnBean;
import com.limitless.services.payment.PaymentService.PaymentTxnBean.TxnStatus;
import com.limitless.services.payment.PaymentService.SellerCreditsResponseBean;
import com.limitless.services.payment.PaymentService.SellerTxnHistoryBean;
import com.limitless.services.payment.PaymentService.SplitRequestBean;
import com.limitless.services.payment.PaymentService.SplitResponseBean;
import com.limitless.services.payment.PaymentService.TxnHistoryBean;
import com.limitless.services.payment.PaymentService.TxnMailRequestBean;
import com.limitless.services.payment.PaymentService.TxnMailResponseBean;
import com.limitless.services.payment.PaymentService.TxnResponseBean;
import com.limitless.services.payment.PaymentService.TxnSettlementResponseBean;
import com.limitless.services.payment.PaymentService.dao.MasterTxnManager;
import com.limitless.services.payment.PaymentService.dao.PaymentCredit;
import com.limitless.services.payment.PaymentService.dao.PaymentCreditManager;
import com.limitless.services.payment.PaymentService.dao.PaymentSettlementManager;
import com.limitless.services.payment.PaymentService.dao.PaymentTxn;
import com.limitless.services.payment.PaymentService.dao.PaymentTxnManager;
import com.limitless.services.payment.PaymentService.util.PaymentConstants;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
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
	Client client = RestClientUtil.createClient();

	@GET
	@Path("/getVersion")
	public Response getVersion() {
		String output = "1.0.0";
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/trans/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PaymentTxnBean getPaymentTxn(@PathParam("id") int id) throws Exception {
		PaymentTxnBean bean = new PaymentTxnBean();
		try {
			logger.info("Id:" + id);
			PaymentTxnManager manager = new PaymentTxnManager();
			PaymentTxn paymentTxn = manager.findById(id);

			bean.setTxnId(paymentTxn.getTxnId());
			bean.setSellerId(paymentTxn.getSellerId());
			bean.setCitrusSellerId(paymentTxn.getCitrusSellerId());
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
	public TxnResponseBean addTxn(PaymentTxnBean bean) throws Exception {
		TxnResponseBean txnResp = new TxnResponseBean();

		try {
			PaymentTxn paymentTxn = new PaymentTxn();
			paymentTxn.setEngageCustomerId(bean.getEngageCustomerId());
			paymentTxn.setSellerId(bean.getSellerId());
			paymentTxn.setCitrusSellerId(bean.getCitrusSellerId());
			paymentTxn.setTxnAmount(bean.getTxnAmount());
			paymentTxn.setSellerName(bean.getSellerName());
			paymentTxn.setTxnStatus(bean.getTxnStatus().toString());
			paymentTxn.setSellerDeviceId(bean.getSellerDeviceId());
			paymentTxn.setTxnNotes(bean.getTxnNotes());
			paymentTxn.setOrderId(bean.getOrderId());
			paymentTxn.setTxnType(bean.getTxnType());

			PaymentTxnManager manager = new PaymentTxnManager();
			manager.persist(paymentTxn);

			txnResp.setTxnId(paymentTxn.getTxnId());
			txnResp.setMessage("Success");
			
			MasterTxnRequestBean masterTxnRequestBean = new MasterTxnRequestBean();
			masterTxnRequestBean.setCitrusTxnId(paymentTxn.getTxnId());
			masterTxnRequestBean.setIciciUorderId(0);
			masterTxnRequestBean.setSellerId(paymentTxn.getSellerId());
			masterTxnRequestBean.setCustomerId(paymentTxn.getEngageCustomerId());
			
			MasterTxnManager masterManager = new MasterTxnManager();
			MasterTxnResponseBean masterTxnResponseBean = masterManager.createMasterTxn(masterTxnRequestBean);
			
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
	public TxnResponseBean updateTxn(PaymentTxnBean paymentTxnBean) throws Exception {
		TxnResponseBean txnResp = new TxnResponseBean();

		try {
			// TODO - currently - Status,mail,SMS
			PaymentTxnManager manager = new PaymentTxnManager();
			PaymentTxn paymentTxn = manager.updateTxn(paymentTxnBean.getTxnId(),
					paymentTxnBean.getTxnStatus().toString());

			EngageCustomerManager customerManager = new EngageCustomerManager();
			EngageCustomer customer = customerManager.findById(paymentTxn.getEngageCustomerId());

			txnResp.setTxnId(paymentTxn.getTxnId());
			txnResp.setMessage("Success");
			txnResp.setAmount(paymentTxn.getTxnAmount());
			txnResp.setDate(paymentTxn.getTxnUpdatedTime().toString());
			txnResp.setName(customer.getCustomerName());
			txnResp.setSellerDeviceId(paymentTxn.getSellerDeviceId());
			
			OrderStatusResponseBean orderStatusResponseBean = new OrderStatusResponseBean();
			OrderMailResponseBean orderMailResponseBean = new OrderMailResponseBean();
			RestaurantOrderStatusUpdateResponseBean restaurantOrderStatusUpdateResponseBean = new RestaurantOrderStatusUpdateResponseBean();
			OrderPaymentModeUpdateResponseBean updateResponseBean = new OrderPaymentModeUpdateResponseBean();
			if(paymentTxn.getTxnType().equals("eCommerce")){
				if(paymentTxn.getOrderId()>0){
					OrdersManager ordersManager = new OrdersManager();
					orderStatusResponseBean = ordersManager.orderStatusUpdate(paymentTxn.getOrderId(), 5);
					System.out.println("Order status : "+orderStatusResponseBean.getCurrentStatus()+" for order id : " + orderStatusResponseBean.getOrderId());
					orderMailResponseBean = ordersManager.sendMailOrderTxn(paymentTxn.getOrderId());
					System.out.println("Status: " + orderMailResponseBean.getMessage());
					updateResponseBean = ordersManager.updatePaymentMode(paymentTxn.getOrderId(), "FAILED");
				}
			}
			else if(paymentTxn.getTxnType().equals("restaurant")){
				if(paymentTxn.getOrderId()>0){
					RestaurantManager restaurantManager = new RestaurantManager();
					restaurantOrderStatusUpdateResponseBean = restaurantManager.orderStatusUpdate(paymentTxn.getOrderId(),2);
					System.out.println("Restaurant Order Id :" + restaurantOrderStatusUpdateResponseBean.getOrderId());
					restaurantManager.sendOrderMail(paymentTxn.getOrderId());
				}
			}

			MessageBean messageBean = new MessageBean();
			messageBean.setCustomerId(paymentTxn.getEngageCustomerId());
			messageBean.setSellerCitrusId(paymentTxn.getCitrusSellerId());
			messageBean.setSellerId(paymentTxn.getSellerId());
			messageBean.setTxnAmount(paymentTxn.getTxnAmount());
			messageBean.setTxnStatus(paymentTxn.getTxnStatus());
			messageBean.setTxnId(paymentTxn.getTxnId());

			MessageResponseBean messageResponseBean = manager.sendMessage(messageBean);

			TxnMailRequestBean mailBean = new TxnMailRequestBean();
			mailBean.setCustomerId(paymentTxn.getEngageCustomerId());
			mailBean.setSellerId(paymentTxn.getSellerId());
			mailBean.setTxnId(paymentTxn.getTxnId());
			mailBean.setTxnAmount(paymentTxn.getTxnAmount());

			TxnMailResponseBean mailResponseBean = manager.sendMail(mailBean);

		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return txnResp;
	}

	@GET
	@Path("/trans/customer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerTxnHistoryBean getHistory(@PathParam("customerId") int customerId) throws Exception {
		List<TxnHistoryBean> historyBeanList = new ArrayList<TxnHistoryBean>();
		CustomerTxnHistoryBean respBean = new CustomerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			List<PaymentTxn> paymentHistory = manager.getTxnHistory(customerId);

			if (paymentHistory.size() > 0) {
				System.out.println("Size : " + paymentHistory.size());
				respBean.setMessage("Success");
			} else {
				System.out.println("List Null");
				respBean.setMessage("No Record Found");
			}

			for (PaymentTxn bean : paymentHistory) {
				TxnHistoryBean historyBean = new TxnHistoryBean();
				historyBean.setTxnId(bean.getTxnId());
				historyBean.setCustomerId(bean.getEngageCustomerId());
				historyBean.setSellerId(bean.getSellerId());
				historyBean.setSellerName(bean.getSellerName());
				historyBean.setTxtAmount(bean.getTxnAmount());
				historyBean.setCreditAmount(manager.getCreditAmount(bean.getTxnId()));
				historyBean.setCitrusMpTxnId(bean.getCitrusMpTxnId());
				historyBean.setSplitId(bean.getSplitId());
				historyBean.setTxtStatus(bean.getTxnStatus().toString());
				historyBean.setTxnNotes(bean.getTxnNotes());
				String gmtTime = bean.getTxnUpdatedTime().toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(gmtTime);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.HOUR, 5);
				calendar.add(Calendar.MINUTE, 30);
				String localTime = sdf.format(calendar.getTime());
				historyBean.setTxnTime(localTime);
				historyBeanList.add(historyBean);
				historyBean = null;
			}
			respBean.setHistoryBean(historyBeanList);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return respBean;
	}

	@GET
	@Path("/trans/customer/{customerId}/{firstTxnId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerTxnHistoryBean getHistoryPangination(@PathParam("customerId") int customerId,
			@PathParam("firstTxnId") int firstTxnId) throws Exception {
		List<TxnHistoryBean> historyBeanList = new ArrayList<TxnHistoryBean>();
		CustomerTxnHistoryBean respBean = new CustomerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			List<PaymentTxn> paymentHistory = manager.getTxnHistoryPagination(customerId, firstTxnId);

			if (paymentHistory.size() > 0) {
				System.out.println("Size : " + paymentHistory.size());
				respBean.setMessage("Success");
			} else {
				System.out.println("List Null");
				respBean.setMessage("No More Records Found");
			}

			for (PaymentTxn bean : paymentHistory) {
				TxnHistoryBean historyBean = new TxnHistoryBean();
				historyBean.setTxnId(bean.getTxnId());
				historyBean.setCustomerId(bean.getEngageCustomerId());
				historyBean.setSellerId(bean.getSellerId());
				historyBean.setSellerName(bean.getSellerName());
				historyBean.setTxtAmount(bean.getTxnAmount());
				historyBean.setCreditAmount(manager.getCreditAmount(bean.getTxnId()));
				historyBean.setCitrusMpTxnId(bean.getCitrusMpTxnId());
				historyBean.setSplitId(bean.getSplitId());
				historyBean.setTxtStatus(bean.getTxnStatus().toString());
				historyBean.setTxnNotes(bean.getTxnNotes());
				String gmtTime = bean.getTxnUpdatedTime().toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(gmtTime);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.HOUR, 5);
				calendar.add(Calendar.MINUTE, 30);
				String localTime = sdf.format(calendar.getTime());
				historyBean.setTxnTime(localTime);
				historyBeanList.add(historyBean);
				historyBean = null;
			}
			respBean.setHistoryBean(historyBeanList);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return respBean;
	}

	@GET
	@Path("/trans/seller/{citrusSellerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean getSellerTxns(@PathParam("citrusSellerId") int citrusSellerId) throws Exception {
		SellerTxnHistoryBean bean = null;
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			bean = manager.getSellerTxns(citrusSellerId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Exception");
		}
		return bean;
	}

	@GET
	@Path("/trans/seller/{citrusSellerId}/{firstTxnId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean getSellerTxnsPagination(@PathParam("citrusSellerId") int citrusSellerId,
			@PathParam("firstTxnId") int firstTxnId) throws Exception {
		SellerTxnHistoryBean bean = new SellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			bean = manager.sellerTxnHistoryPagination(citrusSellerId, firstTxnId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Exception");
		}
		return bean;
	}

	@PUT
	@Path("/trans/{id}/split")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SplitResponseBean splitTxn(@PathParam("id") int id, SplitRequestBean splitReq) throws Exception {
		SplitResponseBean splitResp = new SplitResponseBean();
		int citrusMpTxnId = splitReq.getCitrusMpTxnId();
		double feePercent = 0;
		double txnAmount = 0;
		double feeAmount = 0;
		int sellerSettlePref = 0;
		try {
			logger.info("Id:" + id);

			PaymentTxnManager manager = new PaymentTxnManager();
			PaymentTxn paymentTxn = manager.findById(id);
			EngageSellerManager sellerMgr = new EngageSellerManager();
			EngageSeller seller = sellerMgr.findById(paymentTxn.getSellerId());
			int orderId = paymentTxn.getOrderId();
			String txnType = paymentTxn.getTxnType();
			
			List<String> sellerDeviceIds = sellerMgr.getAllSellerDeviceId(paymentTxn.getSellerId());

			PaymentCreditManager creditManager = new PaymentCreditManager();
			CreditRespBean respBean = creditManager.updateCreditDebitTrans(id);

			if (respBean.getMessage().equals("Success")) {
				System.out.println("Credit/Debit Trans Success");
			} else if (respBean.getMessage().equals("Failed")) {
				System.out.println("Not Credit/Debit Trans");
			}

			int txnId = paymentTxn.getTxnId();
			int citrusSellerId = paymentTxn.getCitrusSellerId();

			String sellerDeviceId = paymentTxn.getSellerDeviceId();
			int customerId = paymentTxn.getEngageCustomerId();
			String txnDate = paymentTxn.getTxnUpdatedTime().toString();

			EngageCustomerManager customerManager = new EngageCustomerManager();
			EngageCustomer engageCustomer = customerManager.findById(customerId);

			String customerName = engageCustomer.getCustomerName();

			String merchantSplitRef = paymentTxn.getSellerName() + "_" + System.currentTimeMillis();

			if (seller != null) {
				feePercent = seller.getSellerSplitPercent();
				txnAmount = paymentTxn.getTxnAmount();
				feeAmount = txnAmount * (feePercent / 100);
				// round off to 2 decimal
				feeAmount = Math.round(feeAmount * 100) / 100D;
				sellerSettlePref = seller.getSellerSettlePref();
			} else {
				txnAmount = paymentTxn.getTxnAmount();
				feeAmount = 0;
			}

			// Make Split API call
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);

			Map<String, Object> splitRequest = new HashMap<String, Object>();
			splitRequest.put("trans_id", citrusMpTxnId);
			splitRequest.put("seller_id", citrusSellerId);
			splitRequest.put("merchant_split_ref", merchantSplitRef);
			splitRequest.put("split_amount", txnAmount);
			splitRequest.put("fee_amount", feeAmount);
			splitRequest.put("auto_payout", 1);

			PaymentConstants constants = PaymentConstants.getInstance();

			WebResource webResource = client.resource("https://splitpay.citruspay.com/marketplace/split");

			ClientResponse splitResponse = webResource.accept("application/json").type("application/json")
					.header("auth_token", constants.getAuth_Token()).post(ClientResponse.class, splitRequest);

			String splitResponseStr = splitResponse.getEntity(Object.class).toString();
			System.out.println("Slipt Response: " + splitResponseStr);

			splitResponseStr = splitResponseStr.substring(1, splitResponseStr.length() - 2);
			splitResponseStr = splitResponseStr.split(",")[0];
			String[] splitArr = splitResponseStr.split("=");
			int splitId = 0;
			if (splitArr[0].equals("split_id")) {
				splitId = Integer.parseInt(splitArr[1]);
			}
			//int splitId = 0;
			paymentTxn = manager.updateSplitId(txnId, citrusMpTxnId, splitId, TxnStatus.PAYMENT_SUCCESSFUL.toString());

			splitResp.setSplitId(paymentTxn.getSplitId());
			splitResp.setMessage("Success");
			splitResp.setName(customerName);
			splitResp.setAmount(txnAmount);
			splitResp.setDate(txnDate);
			splitResp.setSellerDeviceIds(sellerDeviceIds);
			
			OrderStatusResponseBean orderStatusResponseBean = new OrderStatusResponseBean();
			OrderMailResponseBean orderMailResponseBean = new OrderMailResponseBean();
			RestaurantOrderStatusUpdateResponseBean restaurantOrderStatusUpdateResponseBean = new RestaurantOrderStatusUpdateResponseBean();
			OrderPaymentModeUpdateResponseBean updateResponseBean = new OrderPaymentModeUpdateResponseBean();
			if(txnType.equals("eCommerce")){
				if(orderId>0){
					OrdersManager ordersManager = new OrdersManager();
					updateResponseBean = ordersManager.updatePaymentMode(orderId, "PAID");
					orderStatusResponseBean = ordersManager.orderStatusUpdate(orderId, 1);
					System.out.println("Order status : "+orderStatusResponseBean.getCurrentStatus()+" for order id : " + orderStatusResponseBean.getOrderId());
					//InventoryUpdateResponseBean inventoryUpdateResponseBean = ordersManager.updateInventory(orderId);
					//System.out.println("Inventory updated : " + inventoryUpdateResponseBean.getOrderId());
					orderMailResponseBean = ordersManager.sendMailOrderTxn(orderId);
					System.out.println("Status: " + orderMailResponseBean.getMessage());
				}
			}
			else if(txnType.equals("restaurant")){
				if(orderId>0){
					RestaurantManager restaurantManager = new RestaurantManager();
					restaurantOrderStatusUpdateResponseBean = restaurantManager.orderStatusUpdate(orderId,1);
					System.out.println("Restaurant Order Id :" + restaurantOrderStatusUpdateResponseBean.getOrderId());
					restaurantManager.sendOrderMail(orderId);
				}
			}

			TxnSettlementResponseBean txnSettlementResponseBean = new TxnSettlementResponseBean();
			if (sellerSettlePref == 1) {
				PaymentSettlementManager settlementManager = new PaymentSettlementManager();
				txnSettlementResponseBean = settlementManager.settleTxnById(txnId);
				System.out.println("Settle Id : " + txnSettlementResponseBean.getPsId());
			}

			MessageBean messageBean = new MessageBean();
			messageBean.setCustomerId(paymentTxn.getEngageCustomerId());
			messageBean.setSellerCitrusId(paymentTxn.getCitrusSellerId());
			messageBean.setSellerId(paymentTxn.getSellerId());
			messageBean.setTxnAmount(paymentTxn.getTxnAmount());
			messageBean.setTxnStatus(paymentTxn.getTxnStatus());
			messageBean.setTxnId(paymentTxn.getTxnId());

			MessageResponseBean messageResponseBean = manager.sendMessage(messageBean);
			System.out.println("Message : " + messageResponseBean.getMessage());

			TxnMailRequestBean mailBean = new TxnMailRequestBean();
			mailBean.setCustomerId(paymentTxn.getEngageCustomerId());
			mailBean.setSellerId(paymentTxn.getSellerId());
			mailBean.setTxnId(paymentTxn.getTxnId());
			mailBean.setTxnAmount(paymentTxn.getTxnAmount());

			TxnMailResponseBean mailResponseBean = manager.sendMail(mailBean);

		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}

		return splitResp;
	}

	@POST
	@Path("/credit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CreditRespBean addCredit(CreditBean bean) throws Exception {
		CreditRespBean creditResp = new CreditRespBean();

		try {
			PaymentCredit paymentCredit = new PaymentCredit();
			paymentCredit.setTxnId(bean.getTxnId());
			paymentCredit.setCreditTemp(bean.getCreditTemp());
			paymentCredit.setDebitTemp(bean.getDebitTemp());
			paymentCredit.setSellerId(bean.getSellerId());
			paymentCredit.setCustomerId(bean.getCustomerId());
			paymentCredit.setMerchantId(bean.getMerchantId());

			PaymentCreditManager manager = new PaymentCreditManager();
			manager.persist(paymentCredit);

			creditResp.setCreditId(paymentCredit.getCreditId());
			creditResp.setMessage("Success");
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return creditResp;
	}

	@GET
	@Path("/credit/seller/{sellerId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<SellerCreditsResponseBean> sellerCredits(@PathParam("sellerId") int sellerId) throws Exception {
		List<SellerCreditsResponseBean> responseBean = new ArrayList<SellerCreditsResponseBean>();
		try {
			PaymentCreditManager manager = new PaymentCreditManager();
			responseBean = manager.sellerCreditsList(sellerId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@POST
	@Path("/notify")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public NotificationResponseBean sendNotification(NotificationRequestBean request) throws Exception {
		NotificationResponseBean response = new NotificationResponseBean();
		try {
			EngageSellerManager sellerManager = new EngageSellerManager();
			EngageSeller seller = sellerManager.findById(Integer.parseInt(request.getTo()));

			request.setTo(seller.getSellerDeviceId());

			DataBean data = request.getData();
			if (data == null)
				data = new DataBean();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, 5);
			calendar.add(Calendar.MINUTE, 30);
			String localTime = sdf.format(calendar.getTime());
			date = sdf.parse(localTime);

			data.setTime(date);

			WebResource webResource2 = client.resource("https://fcm.googleapis.com/fcm/send");
			ClientResponse clientResponse = webResource2.type("application/json")
					.header("Authorization", "key=AIzaSyCE49LX2u8Op-LbqidMJfcKlH4Bh5opUos")
					.post(ClientResponse.class, request);
			System.out.println(clientResponse.getStatus());
			System.out.println(clientResponse.getEntity(String.class));
			response.setMessage(String.valueOf(clientResponse.getStatus()));
		} catch (Exception e) {
			logger.error("API Error", e);			

		}

		return response;
	}

	@GET
	@Path("/credit/customer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CustomerCreditResponseBean> customerCredits(@PathParam("customerId") int customerId) throws Exception {
		List<CustomerCreditResponseBean> responseBean = new ArrayList<CustomerCreditResponseBean>();
		try {
			PaymentCreditManager manager = new PaymentCreditManager();
			responseBean = manager.customerCreditsList(customerId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@POST
	@Path("/trans/credit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CreditTransResponseBean creditTrans(CreditTransRequestBean requestBean) throws Exception {
		CreditTransResponseBean responseBean = new CreditTransResponseBean();
		try {

			EngageSellerManager sellerManager = new EngageSellerManager();
			EngageSeller seller = sellerManager.findById(requestBean.getSellerId());

			PaymentTxn trans = new PaymentTxn();
			trans.setEngageCustomerId(requestBean.getCustomerId());
			trans.setSellerId(requestBean.getSellerId());
			trans.setCitrusSellerId(seller.getCitrusSellerId());
			trans.setSellerName(requestBean.getSellerName());
			trans.setSellerDeviceId(seller.getSellerDeviceId());
			trans.setTxnStatus(requestBean.getStatus());
			trans.setTxnAmount(requestBean.getTransAmount());
			trans.setTxnNotes(requestBean.getTxnNotes());

			PaymentTxnManager txnManager = new PaymentTxnManager();
			txnManager.persist(trans);

			PaymentCredit credits = new PaymentCredit();
			credits.setMerchantId(requestBean.getSellerId());
			credits.setCreditAmount(requestBean.getCreditAmount());
			credits.setDebitAmount(requestBean.getDebitAmount());
			credits.setSellerId(seller.getCitrusSellerId());
			credits.setTxnId(trans.getTxnId());
			credits.setCustomerId(requestBean.getCustomerId());

			PaymentCreditManager creditsManager = new PaymentCreditManager();
			creditsManager.persist(credits);

			responseBean.setCreditId(credits.getCreditId());
			responseBean.setTransactionId(trans.getTxnId());
			responseBean.setMessage("Success");
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@GET
	@Path("/trans/day/{citrusSellerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean transDays(@PathParam("citrusSellerId") int citrusSellerId) throws Exception {
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getDayTxns(citrusSellerId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@GET
	@Path("/trans/month/{citrusSellerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean transMonths(@PathParam("citrusSellerId") int citrusSellerId) throws Exception {
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getMonthTxns(citrusSellerId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@GET
	@Path("trans/gen/{merchantId}")
	@Produces(MediaType.APPLICATION_JSON)
	public GeneralSellerTxnHistoryBean getGenSellerTxns(@PathParam("merchantId") int merchantId) throws Exception {
		GeneralSellerTxnHistoryBean historyBean = new GeneralSellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getGenSellerTxns(merchantId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@GET
	@Path("/trans/gen/{merchantId}/{firstTxnId}")
	@Produces(MediaType.APPLICATION_JSON)
	public GeneralSellerTxnHistoryBean getGenSellerTxnsPagination(@PathParam("merchantId") int merchantId,
			@PathParam("firstTxnId") int firstTxnId) throws Exception {
		GeneralSellerTxnHistoryBean historyBean = new GeneralSellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getGenSellerTxnsPagination(merchantId, firstTxnId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@GET
	@Path("/trans/gen/day/{merchantId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean genDayTxns(@PathParam("merchantId") int merchantId) throws Exception {
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getGenDayWiseTxn(merchantId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@GET
	@Path("/trans/gen/month/{merchantId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean genMonthTxns(@PathParam("merchantId") int merchantId) throws Exception {
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getGenMonthWiseTxns(merchantId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@GET
	@Path("/date/{citrusSellerId}/{txnDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean getDateTxns(@PathParam("citrusSellerId") int citrusSellerId,
			@PathParam("txnDate") String txnDate) throws Exception {
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getTxnsByDate(citrusSellerId, txnDate);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@GET
	@Path("/date/{citrusSellerId}/{txnDate}/{firstTxnId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean getDateTxnsPagination(@PathParam("citrusSellerId") int citrusSellerId,
			@PathParam("txnDate") String txnDate, @PathParam("firstTxnId") int firstTxnId) throws Exception {
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getDateWiseTxnsPagination(citrusSellerId, txnDate, firstTxnId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@GET
	@Path("/date/gen/{merchantId}/{txnDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean getGeneralDateTxn(@PathParam("merchantId") int merchantId,
			@PathParam("txnDate") String txnDate) throws Exception {
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getGeneralDateTxns(merchantId, txnDate);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@GET
	@Path("/date/gen/{merchantId}/{txnDate}/{firstTxnId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerTxnHistoryBean getGeneralDateTxnPagintion(@PathParam("merchantId") int merchantId,
			@PathParam("txnDate") String txnDate, int firstTxnId) throws Exception {
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			historyBean = manager.getGeneralDateWiseTxnsPagination(merchantId, txnDate, firstTxnId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return historyBean;
	}

	@POST
	@Path("/trans/mail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TxnMailResponseBean txnMail(TxnMailRequestBean requestBean) throws Exception {
		TxnMailResponseBean responseBean = new TxnMailResponseBean();
		try {
			PaymentTxnManager manager = new PaymentTxnManager();
			responseBean = manager.sendMail(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@POST
	@Path("/credit/reminder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CreditReminderResponseBean creditReminder(CreditReminderRequestBean requestBean) throws Exception{
		CreditReminderResponseBean responseBean = new CreditReminderResponseBean();
		try{
			PaymentCreditManager manager = new PaymentCreditManager();
			responseBean = manager.sendCreditReminder(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@GET
	@Path("/getSubMerchants/{sellerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public MerchantRequestListBean requestSeller(@PathParam("sellerId") int sellerId) throws Exception{
		MerchantRequestListBean listBean = new MerchantRequestListBean();
		try{
			EngageSellerManager manager = new EngageSellerManager();
			listBean = manager.sellerRequest(sellerId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return listBean;
	}
}
