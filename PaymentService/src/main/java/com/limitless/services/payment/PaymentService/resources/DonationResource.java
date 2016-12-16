package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.engage.DonationResponseBean;
import com.limitless.services.engage.dao.DonationManager;

@Path("/donation")
public class DonationResource {
	final static Logger logger = Logger.getLogger(PaymentResource.class);
	
	@Path("/get/{merchantId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public DonationResponseBean getDonations(@PathParam("merchantId") int merchantId) throws Exception{
		DonationResponseBean responseBean = new DonationResponseBean();
		try{
			DonationManager manager = new DonationManager();
			responseBean = manager.getDonations(merchantId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
}
