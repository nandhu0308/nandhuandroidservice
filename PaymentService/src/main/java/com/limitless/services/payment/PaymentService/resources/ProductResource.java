package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.engage.sellers.NewProductsRequestBean;
import com.limitless.services.engage.sellers.NewProductsResponseBean;
import com.limitless.services.engage.sellers.ProductBean;
import com.limitless.services.engage.sellers.ProductResponseBean;
import com.limitless.services.engage.sellers.product.dao.ProductManager;

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
}
