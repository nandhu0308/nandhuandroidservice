package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.limitless.services.payment.PaymentService.AppTokenBean;

//INSTAMOJO
public class TokenResource {
	
	@GET
	@Path("/getVersion")
	public Response getVersion() {
		String output = "1.0.0";
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/appToken")
	@Produces(MediaType.APPLICATION_JSON)
	public AppTokenBean getAppToken() {
		AppTokenBean appTokenBean = new AppTokenBean();
		
		
		return appTokenBean;
	}

}
