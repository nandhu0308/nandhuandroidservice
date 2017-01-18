package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.payment.PaymentService.ReportMailResponseBean;
import com.limitless.services.payment.PaymentService.dao.MailManager;

@Path("/mail")
public class MailResource {
	
	final static Logger logger = Logger.getLogger(MailResource.class);
	
	@Path("/report")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ReportMailResponseBean getReports() throws Exception{
		ReportMailResponseBean responseBean = new ReportMailResponseBean();
		try{
			MailManager manager = new MailManager();
			responseBean = manager.sendReport();
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
}
