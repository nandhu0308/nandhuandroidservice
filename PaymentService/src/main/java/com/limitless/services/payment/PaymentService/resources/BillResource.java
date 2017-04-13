package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.engage.bills.BillPaymentStatusUpdateRequestBean;
import com.limitless.services.engage.bills.BillPaymentStatusUpdateResponseBean;
import com.limitless.services.engage.bills.BillRequestBean;
import com.limitless.services.engage.bills.BillResponseBean;
import com.limitless.services.engage.bills.CustomerBillsListBean;
import com.limitless.services.engage.bills.SellerBillsListBean;
import com.limitless.services.engage.bills.dao.BillsManager;

@Path("/bill")
public class BillResource {
	final static Logger logger = Logger.getLogger(BillResource.class);
	
	@Path("/get/customer/{customerId}/status={statusCode}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerBillsListBean getCustomerBill(@PathParam("customerId") int customerId,
			@PathParam("statusCode") String statusCode) throws Exception{
		CustomerBillsListBean listBean = new CustomerBillsListBean();
		try{
			BillsManager manager = new BillsManager();
			listBean = manager.getCustomerBills(customerId, statusCode);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return listBean;
	}
	
	@Path("/get/seller/{sellerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SellerBillsListBean getSellerBills(@PathParam("sellerId") int sellerId) throws Exception{
		SellerBillsListBean listBean = new SellerBillsListBean();
		try{
			BillsManager manager = new BillsManager();
			listBean = manager.getSellerBills(sellerId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return listBean;
	}
	
	@Path("/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillResponseBean newBill(BillRequestBean requestBean) throws Exception{
		BillResponseBean responseBean = new BillResponseBean();
		try{
			BillsManager manager = new BillsManager();
			responseBean = manager.newBill(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/status")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BillPaymentStatusUpdateResponseBean updateBillStatus(BillPaymentStatusUpdateRequestBean requestBean) throws Exception{
		BillPaymentStatusUpdateResponseBean responseBean = new BillPaymentStatusUpdateResponseBean();
		try{
			BillsManager manager = new BillsManager();
			responseBean = manager.updateBillStatus(requestBean.getBillId(), requestBean.getBillStatusCode());
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/mail/{billId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void sendMail(@PathParam("billId") int billId) throws Exception{
		try{
			BillsManager manager = new BillsManager();
			manager.sendBillMail(billId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
	}
	
}
