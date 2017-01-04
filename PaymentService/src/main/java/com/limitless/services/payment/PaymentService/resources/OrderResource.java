package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.engage.order.OrderRequestBean;
import com.limitless.services.engage.order.OrderResponseBean;
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
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
}
