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

import org.apache.log4j.Logger;

import com.limitless.services.engage.restaurants.RestaurantBean;
import com.limitless.services.engage.restaurants.RestaurantErrorResponseBean;
import com.limitless.services.engage.restaurants.RestaurantOrderBean;
import com.limitless.services.engage.restaurants.RestaurantOrderDetailsResponseBean;
import com.limitless.services.engage.restaurants.RestaurantOrderRequestBean;
import com.limitless.services.engage.restaurants.RestaurantOrderResponseBean;
import com.limitless.services.engage.restaurants.RestaurantOrderStatusUpdateRequestBean;
import com.limitless.services.engage.restaurants.RestaurantOrderStatusUpdateResponseBean;
import com.limitless.services.engage.restaurants.RestaurantSellerResponseBean;
import com.limitless.services.engage.restaurants.dao.RestaurantManager;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/restaurant")
public class RestaurantResource {
	final static Logger logger = Logger.getLogger(RestaurantResource.class);
	
	@Path("/get/{restaurantId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRestaurantDetails(@PathParam("restaurantId") int restaurantId){
		RestaurantManager manager = new RestaurantManager();
		RestaurantBean bean = manager.getRestaurantDetails(restaurantId);
		if(bean!=null){
			return Response.status(200).entity(bean).build();
		}
		RestaurantErrorResponseBean errorBean = new RestaurantErrorResponseBean();
		errorBean.setRestaurantId(restaurantId);
		errorBean.setMessage("Not Found");
		return Response.status(Status.NOT_FOUND).entity(errorBean).build();
	}
	
	@Path("/order/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newOrder(RestaurantOrderRequestBean requestBean) throws Exception{
		RestaurantManager manager = new RestaurantManager();
		RestaurantOrderResponseBean responseBean = manager.createOrder(requestBean);
		if(responseBean!=null){
			manager.notificationToRestaurant(responseBean.getRestaurantOrderId());
			manager.notificationToCustomer(responseBean.getRestaurantOrderId());
			if(requestBean.getPaymentMode()!=null && requestBean.getPaymentMode().equals("POD")){
				RestaurantOrderStatusUpdateResponseBean statusUpdateResponseBean = manager.orderStatusUpdate(responseBean.getRestaurantOrderId(), 3);
				manager.sendOrderMail(responseBean.getRestaurantOrderId());
			}
			return Response.status(200).entity(responseBean).build();
		}
		//RestaurantOrderStatusUpdateResponseBean statusUpdateResponseBean = manager.orderStatusUpdate(responseBean.getRestaurantOrderId(), 2);
		RestaurantErrorResponseBean errorBean = new RestaurantErrorResponseBean();
		errorBean.setRestaurantId(requestBean.getRestaurantId());
		errorBean.setMessage("Failed");
		return Response.status(Status.NOT_FOUND).entity(errorBean).build();
	}
	
	@Path("/order/status")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response statusUpdate(RestaurantOrderStatusUpdateRequestBean requestBean){
		RestaurantManager manager = new RestaurantManager();
		RestaurantOrderStatusUpdateResponseBean responseBean = manager.orderStatusUpdate(requestBean);
		if(responseBean != null){
			return Response.status(200).entity(responseBean).build();
		}
		RestaurantErrorResponseBean errorBean = new RestaurantErrorResponseBean();
		errorBean.setRestaurantId(requestBean.getRestaurantId());
		errorBean.setMessage("Failed");
		return Response.status(Status.NOT_FOUND).entity(errorBean).build();
	}
	
	@Path("/order/summary/customer/{customerId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response gettingCustomerSummary(@PathParam("customerId") int customerId) throws Exception{
		RestaurantManager manager = new RestaurantManager();
		RestaurantOrderBean bean = manager.getCustomerOrderSummary(customerId);
		if(bean!=null){
			return Response.status(200).entity(bean).build();
		}
		RestaurantErrorResponseBean errorBean = new RestaurantErrorResponseBean();
		errorBean.setMessage("Not Found");
		return Response.status(Status.NOT_FOUND).entity(errorBean).build();
	}
	
	@Path("/order/summary/restaurant/{restaurantId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response gettingRestaurantSummary(@PathParam("restaurantId") int restaurantId) throws Exception{
		RestaurantManager manager = new RestaurantManager();
		RestaurantOrderBean bean = manager.getRestaurantOrderSummary(restaurantId);
		if(bean!=null){
			return Response.status(200).entity(bean).build();
		}
		RestaurantErrorResponseBean errorBean = new RestaurantErrorResponseBean();
		errorBean.setMessage("Not Found");
		return Response.status(Status.NOT_FOUND).entity(errorBean).build();
	}
	
	@Path("/order/get/{orderId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response gettingOrderByOrderId(@PathParam("orderId") int orderId){
		RestaurantManager manager = new RestaurantManager();
		RestaurantOrderDetailsResponseBean responseBean = manager.getOrderById(orderId);
		if(responseBean!=null){
			return Response.status(200).entity(responseBean).build();
		}
		RestaurantErrorResponseBean errorBean = new RestaurantErrorResponseBean();
		errorBean.setMessage("Not Found");
		return Response.status(Status.NOT_FOUND).entity(errorBean).build();
	}
	
	@Path("/get/seller/{restaurantId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRestaurantSeller(@PathParam("restaurantId") int restaurantId){
		RestaurantManager manager = new RestaurantManager();
		RestaurantSellerResponseBean responseBean = manager.getRestaurantSeller(restaurantId);
		if(responseBean != null){
			return Response.status(200).entity(responseBean).build();
		}
		RestaurantErrorResponseBean errorBean = new RestaurantErrorResponseBean();
		errorBean.setMessage("Not Found");
		return Response.status(Status.NOT_FOUND).entity(errorBean).build();
	}
	
	@Path("/mail/{orderId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMail(@PathParam("orderId") int orderId) throws Exception{
		RestaurantManager manager = new RestaurantManager();
		manager.sendOrderMail(orderId);
		return Response.status(200).build();
	}
	
	@Path("/notify/{orderId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendNoification(@PathParam("orderId") int orderId){
		RestaurantManager manager = new RestaurantManager();
		manager.notificationToRestaurant(orderId);
		return Response.status(200).build();
	}
	
}
