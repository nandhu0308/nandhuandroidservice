package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.limitless.services.engage.order.dao.OrdersManager;
import com.limitless.services.engage.sellers.NewProductsRequestBean;
import com.limitless.services.engage.sellers.NewProductsResponseBean;
import com.limitless.services.engage.sellers.ProductBean;
import com.limitless.services.engage.sellers.ProductErrorBean;
import com.limitless.services.engage.sellers.ProductInventoryRequestBean;
import com.limitless.services.engage.sellers.ProductInventoryResponseBean;
import com.limitless.services.engage.sellers.ProductResponseBean;
import com.limitless.services.engage.sellers.product.dao.ProductManager;
import com.limitless.services.payment.PaymentService.InventoryUpdateResponseBean;

@Path("/products")
public class ProductResource {
	final static Logger logger = Logger.getLogger(ProductResource.class);
	
	@Path("/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public NewProductsResponseBean newProducts(NewProductsRequestBean requestBean) throws Exception{
		NewProductsResponseBean responseBean = new NewProductsResponseBean();
		try{
			ProductManager manager = new ProductManager();
			responseBean = manager.addNewProducts(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/edit")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductResponseBean editProduct(ProductBean bean) throws Exception{
		ProductResponseBean responseBean = new ProductResponseBean();
		try{
			ProductManager manager = new ProductManager();
			responseBean = manager.updateProduct(bean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/inventory")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductInventoryResponseBean addInventory(ProductInventoryRequestBean requestBean) throws Exception{
		ProductInventoryResponseBean responseBean = new ProductInventoryResponseBean();
		try{
			ProductManager manager = new ProductManager();
			responseBean = manager.addProductInventory(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/inventory/update/{orderId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public InventoryUpdateResponseBean updateInventory(@PathParam("orderId") int orderId) throws Exception{
		InventoryUpdateResponseBean responseBean = new InventoryUpdateResponseBean();
		try{
			OrdersManager manager = new OrdersManager();
			responseBean = manager.updateInventory(orderId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/get/{productId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam("productId") int productId){
		ProductManager manager = new ProductManager();
		ProductBean bean = manager.getProductById(productId);
		if(bean!=null){
			return Response.status(200).entity(bean).build();
		}
		ProductErrorBean errorBean = new ProductErrorBean();
		errorBean.setMessage("Not Found...");
		return Response.status(Status.NOT_FOUND).entity(errorBean).build();
	}
	
}
