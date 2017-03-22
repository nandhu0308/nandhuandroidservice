package com.limitless.services.payment.PaymentService.resources;

import java.util.List;

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

import com.limitless.services.engage.SellerLoginResponseBean;
import com.limitless.services.engage.dao.EngageSellerManager;
import com.limitless.services.engage.order.dao.OrdersManager;
import com.limitless.services.engage.sellers.NewProductsRequestBean;
import com.limitless.services.engage.sellers.NewProductsResponseBean;
import com.limitless.services.engage.sellers.ProductBean;
import com.limitless.services.engage.sellers.ProductErrorBean;
import com.limitless.services.engage.sellers.ProductInventoryRequestBean;
import com.limitless.services.engage.sellers.ProductInventoryResponseBean;
import com.limitless.services.engage.sellers.ProductCSCListBean;
import com.limitless.services.engage.sellers.ProductCategoryRequestBean;
import com.limitless.services.engage.sellers.ProductCategoryResponseBean;
import com.limitless.services.engage.sellers.ProductResponseBean;
import com.limitless.services.engage.sellers.ProductsListRequestBean;
import com.limitless.services.engage.sellers.ProductsListResponeBean;
import com.limitless.services.engage.sellers.SellerProductBean;
import com.limitless.services.engage.sellers.product.dao.ProductManager;
import com.limitless.services.payment.PaymentService.InventoryUpdateResponseBean;

@Path("/products")
public class ProductResource {
	final static Logger logger = Logger.getLogger(ProductResource.class);
	
	@Path("/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public NewProductsResponseBean newProducts(ProductBean bean) throws Exception{
		NewProductsResponseBean responseBean = new NewProductsResponseBean();
		try{
			ProductManager manager = new ProductManager();
			responseBean = manager.addNewProducts(bean);
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
	
	@Path("/get/seller/{sellerMobileNumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SellerProductBean getSellerProducts(@PathParam("sellerMobileNumber") String sellerMobileNumber) throws Exception{
		SellerProductBean productBean = new SellerProductBean();
		try{
			EngageSellerManager sellerManager = new EngageSellerManager();
			SellerLoginResponseBean loginResponseBean = sellerManager.getSellerByMobile(sellerMobileNumber);
			
			ProductManager productManager = new ProductManager();
			List<ProductBean> productList = productManager.getAllProducts(loginResponseBean.getSellerId());
			if(productList.size()>0){
				productBean.setMessage("Success");
				productBean.setProducts(productList);
			}
			else if(productList.isEmpty()){
				productBean.setMessage("Not Found");
			}
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return productBean;
	}
	
	@Path("/get/seller/v2/{sellerMobileNumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ProductCSCListBean getSellerProductV2(@PathParam("sellerMobileNumber") String sellerMobileNumber) throws Exception{
		ProductCSCListBean listBean = new ProductCSCListBean();
		try{
			EngageSellerManager sellerManager = new EngageSellerManager();
			SellerLoginResponseBean loginResponseBean = sellerManager.getSellerByMobile(sellerMobileNumber);
			
			ProductManager productManager = new ProductManager();
			listBean = productManager.getSellerProductsV2(loginResponseBean.getSellerId());
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return listBean;
	}
	
	@Path("/remove/{productId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public ProductResponseBean removeProduct(@PathParam("productId") int productId) throws Exception{
		ProductResponseBean responseBean = new ProductResponseBean();
		try{
			ProductManager manager = new ProductManager();
			responseBean = manager.removeProductFromStore(productId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/get")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductsListResponeBean getProductsByCriteria(ProductsListRequestBean requestBean) throws Exception{
		ProductsListResponeBean responeBean = new ProductsListResponeBean();
		try{
			ProductManager manager = new ProductManager();
			responeBean = manager.getProductsList(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responeBean;
	}
	
	@Path("/category")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductCategoryResponseBean newCategory(ProductCategoryRequestBean requestBean) throws Exception{
		ProductCategoryResponseBean responseBean = new ProductCategoryResponseBean();
		try{
			ProductManager manager = new ProductManager();
			responseBean = manager.addNewCategory(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	
	
}
