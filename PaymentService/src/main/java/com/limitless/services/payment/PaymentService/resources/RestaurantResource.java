package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.limitless.services.engage.restaurants.RestaurantBean;
import com.limitless.services.engage.restaurants.RestaurantErrorResponseBean;
import com.limitless.services.engage.restaurants.RestaurantOrderRequestBean;
import com.limitless.services.engage.restaurants.RestaurantOrderResponseBean;
import com.limitless.services.engage.restaurants.dao.RestaurantManager;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("restaurant")
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
	public Response newOrder(RestaurantOrderRequestBean requestBean){
		RestaurantManager manager = new RestaurantManager();
		RestaurantOrderResponseBean responseBean = manager.createOrder(requestBean);
		if(responseBean!=null){
			return Response.status(200).entity(responseBean).build();
		}
		RestaurantErrorResponseBean errorBean = new RestaurantErrorResponseBean();
		errorBean.setRestaurantId(requestBean.getRestaurantId());
		errorBean.setMessage("Not Found");
		return Response.status(Status.NOT_FOUND).entity(errorBean).build();
	}
	
}
