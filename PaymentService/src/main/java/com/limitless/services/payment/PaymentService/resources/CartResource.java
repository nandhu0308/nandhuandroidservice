package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.engage.order.CartRequestBean;
import com.limitless.services.engage.order.CartResponseBean;
import com.limitless.services.engage.order.dao.CartManager;

@Path("/cart")
public class CartResource {
	final static Logger logger = Logger.getLogger(CartResource.class);
	
	@Path("/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CartResponseBean addToCart(CartRequestBean requestBean) throws Exception{
		CartResponseBean responseBean = new CartResponseBean();
		try{
			CartManager manager = new CartManager();
			responseBean = manager.addCart(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/delete/{itemId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CartResponseBean deleteItem(@PathParam("itemId") int itemId) throws Exception{
		CartResponseBean responseBean = new CartResponseBean();
		try{
			CartManager manager = new CartManager();
			responseBean = manager.deleteItemFromCart(itemId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
}
