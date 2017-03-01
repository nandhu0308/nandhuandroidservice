package com.limitless.services.payment.PaymentService.resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.limitless.services.engage.AddAccountRequestBean;
import com.limitless.services.engage.AddAccountResponseBean;
import com.limitless.services.engage.CheckEmailRequestBean;
import com.limitless.services.engage.CheckEmailResponseBean;
import com.limitless.services.engage.CustomerAddressListBean;
import com.limitless.services.engage.CustomerAddressRequestBean;
import com.limitless.services.engage.CustomerAddressResponseBean;
import com.limitless.services.engage.CustomerDeviceIdRequestBean;
import com.limitless.services.engage.CustomerDeviceIdResponseBean;
import com.limitless.services.engage.CustomerLocationUpdateRequestBean;
import com.limitless.services.engage.CustomerLocationUpdateResponseBean;
import com.limitless.services.engage.CustomerLogoutRequestBean;
import com.limitless.services.engage.CustomerLogoutResponseBean;
import com.limitless.services.engage.CustomerNotifyRequestBean;
import com.limitless.services.engage.CustomerNotifyResponseBean;
import com.limitless.services.engage.EngageCustomerBean;
import com.limitless.services.engage.EngageCustomerResponseBean;
import com.limitless.services.engage.Guest2CustomerLoginRequestBean;
import com.limitless.services.engage.GuestLoginRequestBean;
import com.limitless.services.engage.GuestLoginResponseBean;
import com.limitless.services.engage.InviteRequestBean;
import com.limitless.services.engage.InviteResponseBean;
import com.limitless.services.engage.LoginRequestBean;
import com.limitless.services.engage.LoginResponseBean;
import com.limitless.services.engage.MobileResponseBean;
import com.limitless.services.engage.P2PCustomerVerificationRequestBean;
import com.limitless.services.engage.P2PCustomerVerificationResponseBean;
import com.limitless.services.engage.PasswdRequestBean;
import com.limitless.services.engage.PasswdResponseBean;
import com.limitless.services.engage.ProfileChangeRequestBean;
import com.limitless.services.engage.ProfileChangeResponseBean;
import com.limitless.services.engage.dao.CustomerAddressBookManager;
import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageCustomerManager;
import com.limitless.services.payment.PaymentService.AuthTokenBean;
import com.limitless.services.payment.PaymentService.dao.CitrusAuthTokenManager;

@Path("/")
public class EngageCustomerResource {

	final static Logger logger = Logger.getLogger(EngageCustomerResource.class);

	@POST
	@Path("/customer/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EngageCustomerResponseBean addTxn(EngageCustomerBean bean) throws Exception {

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
			customer.setCitrusSellerId(0);
			customer.setUtmSource(bean.getUtmSource());
			customer.setUtmMedium(bean.getUtmMedium());
			customer.setUtmCampign(bean.getUtmCampign());
			customer.setUtmTerm(bean.getUtmTerm());
			customer.setUtmContent(bean.getUtmContent());
			customer.setDeviceId(bean.getDeviceId());
			customer.setCustomerZip(bean.getZip());
			customer.setCustomerCountryIsoCode(bean.getCountryIsoCode());

			EngageCustomerManager manager = new EngageCustomerManager();

			if (!(manager.checkDuplicateMobile(bean.getMobileNumber()))
					&& !(manager.checkDuplicateEmail(bean.getEmailId()))) {
				manager.persist(customer);
				customerResp.setCustomerId(customer.getCustomerId());
				customerResp.setStatus(1);
				customerResp.setMessage("Success");
			} else {
				customerResp.setStatus(-1);
				customerResp.setMessage("Failure - Duplicate Mobile Number or Email");
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
		logger.info("Reached Customer getVersion..");
		String version="";
		
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			version = manager.getCustomerVersion();
		}
		catch(Exception e){			
			logger.error("API Error", e);			
		}	
	
		return Response.status(200).entity(version).build();
	}

	@POST
	@Path("/customer/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public LoginResponseBean addTxn(LoginRequestBean bean) throws Exception {
		LoginResponseBean loginRespBean = new LoginResponseBean();
		try {
			EngageCustomerManager manager = new EngageCustomerManager();
			loginRespBean = manager.validateUser(bean.getPhone(), bean.getPasswd());
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return loginRespBean;
	}

	@PUT
	@Path("/customer/cpwd/{customerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PasswdResponseBean changePwd(@PathParam("customerId") int customerId, PasswdRequestBean reqBean)
			throws Exception {
		PasswdResponseBean resBean = new PasswdResponseBean();
		try {
			EngageCustomerManager manager = new EngageCustomerManager();
			resBean = manager.changePassword(customerId, reqBean.getOldPasswd(), reqBean.getNewPasswd());
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return resBean;
	}

	@PUT
	@Path("/customer/proupdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProfileChangeResponseBean updateProfile(ProfileChangeRequestBean requestBean) throws Exception {
		ProfileChangeResponseBean responseBean = new ProfileChangeResponseBean();
		try {
			EngageCustomerManager manager = new EngageCustomerManager();
			if (requestBean.getCustomerKey().equals("email")) {
				if (manager.checkDuplicateEmail(requestBean.getCustomerValue())) {
					responseBean.setCustomerId(requestBean.getCustomerId());
					responseBean.setMessage("Email Exist");
					System.out.println("Email Exist");
				} else {
					responseBean = manager.updateCustomerProfile(requestBean);
				}
			} else if (requestBean.getCustomerKey().equals("mobile")) {
				if (manager.checkDuplicateMobile(requestBean.getCustomerValue())) {
					responseBean.setCustomerId(requestBean.getCustomerId());
					responseBean.setMessage("Mobile Exist");
					System.out.println("Mobile Exist");
				} else {
					responseBean = manager.updateCustomerProfile(requestBean);
				}
			} else if (requestBean.getCustomerKey().equals("name")) {
				responseBean = manager.updateCustomerProfile(requestBean);
			}
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@POST
	@Path("/customer/check/mail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CheckEmailResponseBean checkEmail(CheckEmailRequestBean requestBean) throws Exception {
		CheckEmailResponseBean responseBean = new CheckEmailResponseBean();
		try {
			EngageCustomerManager manager = new EngageCustomerManager();
			boolean emailExists = manager.checkDuplicateEmail(requestBean.getEmailId());
			boolean mobileExists = manager.checkDuplicateMobile(requestBean.getMobileNumber());
			if (emailExists) {
				if (mobileExists) {
					responseBean.setEmailId(requestBean.getEmailId());
					responseBean.setMobileNumber(requestBean.getMobileNumber());
					responseBean.setMessage("Email/Mobile Exist");
				} else {
					responseBean.setEmailId(requestBean.getEmailId());
					responseBean.setMobileNumber(requestBean.getMobileNumber());
					responseBean.setMessage("Email Exist");
				}
			} else if (mobileExists) {
				responseBean.setEmailId(requestBean.getEmailId());
				responseBean.setMobileNumber(requestBean.getMobileNumber());
				responseBean.setMessage("Mobile Exist");
			} else {
				responseBean.setEmailId(requestBean.getEmailId());
				responseBean.setMobileNumber(requestBean.getMobileNumber());
				responseBean.setMessage("Email/Mobile Not Exist");
			}
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@GET
	@Path("/customer/get/{phone}")
	@Produces(MediaType.APPLICATION_JSON)
	public MobileResponseBean getMobileNumber(@PathParam("phone") String customerMobile) throws Exception {
		MobileResponseBean responseBean = new MobileResponseBean();
		try {
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.getCustomerMobileNumber(customerMobile);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@POST
	@Path("/customer/invite")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public InviteResponseBean inviteCustomers(InviteRequestBean requestBean) throws Exception {
		InviteResponseBean responseBean = new InviteResponseBean();
		try {
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.sendInvite(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@GET
	@Path("/selfinvite/{customerMobileNumber}")
	public String inviteSelf(@PathParam("customerMobileNumber") String customerMobileNumber) throws Exception {
		String response = "";
		try {
			String message = "LETS GO CASHLESS! Download the LimitlessCircle app: goo.gl/ejZrmv";
			String encoded_message = URLEncoder.encode(message);
			String authkey = "129194Aa6NwGoQsVt580d9a57";
			String mobiles = customerMobileNumber;
			String senderId = "LLCINV";
			String route = "4";
			String mainUrl = "http://api.msg91.com/api/sendhttp.php?";
			StringBuilder sbPostData = new StringBuilder(mainUrl);
			sbPostData.append("authkey=" + authkey);
			sbPostData.append("&mobiles=" + mobiles);
			sbPostData.append("&message=" + encoded_message);
			sbPostData.append("&route=" + route);
			sbPostData.append("&sender=" + senderId);
			mainUrl = sbPostData.toString();
			URL msgUrl = new URL(mainUrl);
			URLConnection con = msgUrl.openConnection();
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((response = reader.readLine()) != null) {
				System.out.println(response);
			}
			reader.close();
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return response;
	}

	@PUT
	@Path("/p2p/verify")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public P2PCustomerVerificationResponseBean checkCustomer(P2PCustomerVerificationRequestBean requestBean)
			throws Exception {
		P2PCustomerVerificationResponseBean verificationResponseBean = new P2PCustomerVerificationResponseBean();
		try {
			EngageCustomerManager manager = new EngageCustomerManager();
			verificationResponseBean = manager.p2pCustomerVerification(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return verificationResponseBean;
	}

	@POST
	@Path("/customer/p2p/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AddAccountResponseBean registerP2p(AddAccountRequestBean requestBean) throws Exception {
		AddAccountResponseBean responseBean = new AddAccountResponseBean();
		try {
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.moneyTransferRegister(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@POST
	@Path("/customer/address/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerAddressResponseBean newAddress(CustomerAddressRequestBean requestBean) throws Exception{
		CustomerAddressResponseBean responseBean = new CustomerAddressResponseBean();
		try{
			CustomerAddressBookManager manager = new CustomerAddressBookManager();
			responseBean = manager.addAddress(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@GET
	@Path("/customer/address/{customerId}/list")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerAddressListBean getAddresses(@PathParam("customerId") int customerId) throws Exception{
		CustomerAddressListBean listBean = new CustomerAddressListBean();
		try{
			CustomerAddressBookManager manager = new CustomerAddressBookManager();
			listBean = manager.getCustomerAddressList(customerId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return listBean;
	}
	
	@PUT
	@Path("/customer/address/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerAddressResponseBean editAddress(CustomerAddressRequestBean requestBean) throws Exception{
		CustomerAddressResponseBean responseBean = new CustomerAddressResponseBean();
		try{
			CustomerAddressBookManager manager = new CustomerAddressBookManager();
			responseBean = manager.editCustomerAddress(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@DELETE
	@Path("/customer/address/{addressId}/remove")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerAddressResponseBean removeAddress(@PathParam("addressId") int addressId) throws Exception{
		CustomerAddressResponseBean responseBean = new CustomerAddressResponseBean();
		try{
			CustomerAddressBookManager manager = new CustomerAddressBookManager();
			responseBean = manager.deleteCustomerAddress(addressId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@PUT
	@Path("/customer/deviceid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerDeviceIdResponseBean updateCustomer(CustomerDeviceIdRequestBean requestBean) throws Exception{
		CustomerDeviceIdResponseBean responseBean = new CustomerDeviceIdResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.addDeviceIdForCustomer(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@POST
	@Path("/customer/notify/offers/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerNotifyResponseBean notifyOffersAll(CustomerNotifyRequestBean requestBean) throws Exception{
		CustomerNotifyResponseBean responseBean = new CustomerNotifyResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.notifyAllCustomer(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@POST
	@Path("/customer/notify/offers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerNotifyResponseBean notifyOfferCustomer(CustomerNotifyRequestBean requestBean) throws Exception{
		CustomerNotifyResponseBean responseBean = new CustomerNotifyResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.notifyCustomer(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@POST
	@Path("/customer/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerLogoutResponseBean customerLogout(CustomerLogoutRequestBean requestBean) throws Exception{
		CustomerLogoutResponseBean responseBean = new CustomerLogoutResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.deacticateSessionKey(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@PUT
	@Path("/customer/lcpwd/{customerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PasswdResponseBean resetPassword(@PathParam("customerId") int customerId, 
			PasswdRequestBean requestBean) throws Exception{
		PasswdResponseBean responseBean = new PasswdResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.changePassword(customerId, requestBean.getOldPasswd(), requestBean.getNewPasswd());
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@POST
	@Path("/customer/guest")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GuestLoginResponseBean guestLogin(GuestLoginRequestBean requestBean) throws Exception{
		GuestLoginResponseBean responseBean = new GuestLoginResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.loginGuestUser(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@POST
	@Path("/guest2customer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginResponseBean loginGuest2Customer(Guest2CustomerLoginRequestBean requestBean) throws Exception{
		LoginResponseBean responseBean = new LoginResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.guest2CustomerLogin(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@PUT
	@Path("/customer/update/location")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerLocationUpdateResponseBean updateLocation(CustomerLocationUpdateRequestBean requestBean) throws Exception{
		CustomerLocationUpdateResponseBean responseBean = new CustomerLocationUpdateResponseBean();
		try{
			EngageCustomerManager manager = new EngageCustomerManager();
			responseBean = manager.updateCustomerLocation(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
}
