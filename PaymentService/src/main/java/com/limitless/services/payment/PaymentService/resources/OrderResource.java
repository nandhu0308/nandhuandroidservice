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

import com.limitless.services.engage.order.OrderDetailResponseBean;
import com.limitless.services.engage.order.OrderMailResponseBean;
import com.limitless.services.engage.order.OrderPaymentModeUpdateResponseBean;
import com.limitless.services.engage.order.OrderRequestBean;
import com.limitless.services.engage.order.OrderResponseBean;
import com.limitless.services.engage.order.OrderStatusResponseBean;
import com.limitless.services.engage.order.OrderSummaryResponseBean;
import com.limitless.services.engage.order.dao.OrdersManager;

@Path("/order")
public class OrderResource {
	final static Logger logger = Logger.getLogger(OrderResource.class);
	
	@Path("/new")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public OrderResponseBean somefunc(OrderRequestBean requestBean) throws Exception{
		OrderResponseBean responseBean = new OrderResponseBean();
		try{
			OrdersManager manager = new OrdersManager();
			responseBean = manager.addOrder(requestBean);
			if(requestBean.getPaymentMode()!=null){
				if(requestBean.getPaymentMode().equals("POD")){
					OrderStatusResponseBean statusResponseBean = manager.orderStatusUpdate(responseBean.getOrderId(), 1);
				}
			}
			if(responseBean.getOrderId()!=0 || responseBean!=null){
				OrderMailResponseBean mailResponseBean = manager.sendMailOrderTxn(responseBean.getOrderId());
			}
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/status/{orderId}/{statusCode}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public OrderStatusResponseBean updateStatus(@PathParam("orderId") int orderId,
			@PathParam("statusCode") int statusCode) throws Exception{
		OrderStatusResponseBean responseBean = new OrderStatusResponseBean();
		try{
			OrdersManager manager = new OrdersManager();
			responseBean = manager.orderStatusUpdate(orderId, statusCode);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/summary/seller/{sellerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public OrderSummaryResponseBean orderSummarySeller(@PathParam("sellerId") int sellerId) throws Exception{
		OrderSummaryResponseBean responseBean = new OrderSummaryResponseBean();
		try{
			OrdersManager manager = new OrdersManager();
			responseBean = manager.sellerOrderSummary(sellerId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/summary/customer/{customerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public OrderSummaryResponseBean orderSummaryCustomer(@PathParam("customerId") int customerId) throws Exception{
		OrderSummaryResponseBean responseBean = new OrderSummaryResponseBean();
		try{
			OrdersManager manager = new OrdersManager();
			responseBean = manager.customerOrderSummary(customerId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/get/{orderId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public OrderDetailResponseBean getOrder(@PathParam("orderId") int orderId) throws Exception{
		OrderDetailResponseBean responseBean = new OrderDetailResponseBean();
		try{
			OrdersManager manager = new OrdersManager();
			responseBean = manager.getOrderById(orderId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/mail/{orderId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public OrderMailResponseBean orderMail(@PathParam("orderId") int orderId) throws Exception{
		OrderMailResponseBean responseBean = new OrderMailResponseBean();
		try{
			OrdersManager manager = new OrdersManager();
			responseBean = manager.sendMailOrderTxn(orderId);
		}
		catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/update/mode/{orderId}/{paymentMode}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public OrderPaymentModeUpdateResponseBean updateOrderPaymentMode(@PathParam("orderId") int orderId,
			@PathParam("paymentMode") String paymentMode) throws Exception{
		OrderPaymentModeUpdateResponseBean responseBean = new OrderPaymentModeUpdateResponseBean();
		try{
			OrdersManager manager = new OrdersManager();
			responseBean = manager.updatePaymentMode(orderId, paymentMode);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
}
