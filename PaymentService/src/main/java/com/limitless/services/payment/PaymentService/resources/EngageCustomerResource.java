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

import com.limitless.services.engage.CheckEmailRequestBean;
import com.limitless.services.engage.CheckEmailResponseBean;
import com.limitless.services.engage.EngageCustomerBean;
import com.limitless.services.engage.EngageCustomerResponseBean;
import com.limitless.services.engage.LoginRequestBean;
import com.limitless.services.engage.LoginResponseBean;
import com.limitless.services.engage.PasswdRequestBean;
import com.limitless.services.engage.PasswdResponseBean;
import com.limitless.services.engage.ProfileChangeRequestBean;
import com.limitless.services.engage.ProfileChangeResponseBean;
import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageCustomerManager;

@Path("/")
public class EngageCustomerResource {
	
	final static Logger logger = Logger.getLogger(EngageCustomerResource.class);
	
	@POST
    @Path("/customer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EngageCustomerResponseBean addTxn(EngageCustomerBean bean) throws Exception{
		
		EngageCustomerResponseBean customerResp = new EngageCustomerResponseBean();
		
		try {
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
			
			if( !(manager.checkDuplicateEmail(bean.getEmailId())) && !(manager.checkDuplicateMobile(bean.getMobileNumber())) ){
				manager.persist(customer);
				customerResp.setCustomerId(customer.getCustomerId());
				customerResp.setStatus(1);
				customerResp.setMessage("Success");
			} else {
				customerResp.setStatus(-1);
				customerResp.setMessage("Failure - Duplicate Email / Mobile Number");
			}
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return customerResp;
	}
	
	@GET
	@Path("/getVersion")
	public Response getVersion() {
		String output = "1.0.0";
		logger.info("Reached getVersion..");
		return Response.status(200).entity(output).build();
	}
	
	@POST
    @Path("/customer/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LoginResponseBean addTxn(LoginRequestBean bean) throws Exception{
		LoginResponseBean loginRespBean = new LoginResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			loginRespBean = manager.validateUser(bean.getEmail(), bean.getPasswd());
		} catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return loginRespBean;
	}
	
	@PUT
	@Path("/customer/cpwd/{customerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PasswdResponseBean changePwd(@PathParam("customerId") int  customerId, PasswdRequestBean reqBean) throws Exception{
		PasswdResponseBean resBean = new PasswdResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			resBean = manager.changePassword(customerId, reqBean.getOldPasswd(), reqBean.getNewPasswd());
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return resBean;
	}
	
	@PUT
	@Path("/customer/proupdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProfileChangeResponseBean updateProfile(ProfileChangeRequestBean requestBean) throws Exception{
		ProfileChangeResponseBean responseBean = new ProfileChangeResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.updateCustomerProfile(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@POST
	@Path("/customer/check/mail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CheckEmailResponseBean checkEmail(CheckEmailRequestBean requestBean) throws Exception{
		CheckEmailResponseBean responseBean = new CheckEmailResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			boolean emailExists = manager.checkDuplicateEmail(requestBean.getEmailId());
			boolean mobileExists = manager.checkDuplicateMobile(requestBean.getMobileNumber());
			if(emailExists){
				if(mobileExists){
					responseBean.setEmailId(requestBean.getEmailId());
					responseBean.setMobileNumber(requestBean.getMobileNumber());
					responseBean.setMessage("Email/Mobile Exist");
				}
				else{
					responseBean.setEmailId(requestBean.getEmailId());
					responseBean.setMobileNumber(requestBean.getMobileNumber());
					responseBean.setMessage("Email Exist");
				}
			}
			else if(mobileExists){
				responseBean.setEmailId(requestBean.getEmailId());
				responseBean.setMobileNumber(requestBean.getMobileNumber());
				responseBean.setMessage("Mobile Exist");
			}
			else{
				responseBean.setEmailId(requestBean.getEmailId());
				responseBean.setMobileNumber(requestBean.getMobileNumber());
				responseBean.setMessage("Email/Mobile Not Exist");
			}
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

}
