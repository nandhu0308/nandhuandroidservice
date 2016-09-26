package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.limitless.services.engage.EngageCustomerBean;
import com.limitless.services.engage.EngageCustomerResponseBean;
import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageCustomerManager;

@Path("/")
public class EngageCustomerResource {
	
	@POST
    @Path("/customer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EngageCustomerResponseBean addTxn(EngageCustomerBean bean){
		
		EngageCustomerResponseBean customerResp = new EngageCustomerResponseBean();
		
		EngageCustomer customer = new EngageCustomer();
		customer.setCustomerName(bean.getCustomerName());
		customer.setCustomerEmail99(bean.getEmailId());
		customer.setCustomerMobileNumber(bean.getMobileNumber());
		customer.setCustomerEmail99(bean.getEmailId());
		customer.setCustomerPasswd99(bean.getPasswd());
		customer.setCustomerCountryCode(bean.getCountryCode());
		customer.setCustomerCity(bean.getCity());
		customer.setCustomerCountry(bean.getCountry());
		
		EngageCustomerManager manager = new EngageCustomerManager();
		manager.persist(customer);
		
		customerResp.setCustomerId(customer.getCustomerId());
		customerResp.setMessage("Success");
		return customerResp;
	}
	
	@GET
	@Path("/getVersion")
	public Response getVersion() {
		String output = "1.0.0";
		return Response.status(200).entity(output).build();
	}

}
