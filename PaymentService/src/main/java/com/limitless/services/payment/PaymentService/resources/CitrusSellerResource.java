package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.engage.sellers.CitrusSellerResponseBean;
import com.limitless.services.engage.sellers.dao.CitrusSellerManager;

@Path("/citrus")
public class CitrusSellerResource {
	final static Logger logger = Logger.getLogger(CitrusSellerResource.class);
	
	@GET
	@Path("/get/all")
	@Produces(MediaType.APPLICATION_JSON)
	public CitrusSellerResponseBean getAllCitrusSellers() throws Exception{
		CitrusSellerResponseBean responseBean = new CitrusSellerResponseBean();
		try{
			CitrusSellerManager manager = new CitrusSellerManager();
			responseBean = manager.getCitrusSellers();
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
}
