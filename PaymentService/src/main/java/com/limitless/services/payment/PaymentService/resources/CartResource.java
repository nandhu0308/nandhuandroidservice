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

import com.limitless.services.engage.order.CartBean;
import com.limitless.services.engage.order.CartItemsListBean;
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
	
	@Path("/item/remove/{itemId}")
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
	
	@Path("/delete/{cartId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public CartResponseBean removeCart(@PathParam("cartId") int cartId) throws Exception{
		CartResponseBean responseBean = new CartResponseBean();
		try{
			CartManager manager = new CartManager();
			responseBean = manager.removeCart(cartId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/add/item/{cartId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CartResponseBean addItemToCart(@PathParam("cartId") int cartId, CartItemsListBean itemBean) throws Exception{
		CartResponseBean responseBean = new CartResponseBean();
		try{
			CartManager manager = new CartManager();
			responseBean = manager.itemToCart(cartId, itemBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/get/{cartId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CartBean getCart(@PathParam("cartId") int cartId) throws Exception{
		CartBean bean = new CartBean();
		try{
			CartManager manager = new CartManager();
			bean = manager.getCart(cartId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return bean;
	}
	
}
