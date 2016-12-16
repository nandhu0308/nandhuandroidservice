package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.payment.PaymentService.AuthTokenBean;
import com.limitless.services.payment.PaymentService.AuthTokenResponseBean;
import com.limitless.services.payment.PaymentService.dao.CitrusAuthTokenManager;

@Path("/token")
public class AuthTokenResource {
	final static Logger logger = Logger.getLogger(AuthTokenResource.class);
	
	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public AuthTokenResponseBean updateToken() throws Exception{
		AuthTokenResponseBean responseBean = new AuthTokenResponseBean();
		try{
			CitrusAuthTokenManager manager = new CitrusAuthTokenManager();
			responseBean = manager.getAuthToken();
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public AuthTokenBean getAuhtToken() throws Exception{
		AuthTokenBean bean = new AuthTokenBean();
		try{
			CitrusAuthTokenManager manager = new CitrusAuthTokenManager();
			bean = manager.getToken();
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return bean;
	}
	
}
