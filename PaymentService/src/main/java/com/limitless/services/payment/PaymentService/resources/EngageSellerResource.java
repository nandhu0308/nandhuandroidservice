package com.limitless.services.payment.PaymentService.resources;

import java.util.ArrayList;
import java.util.List;

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

import com.limitless.services.engage.CoordinatesResponseBean;
import com.limitless.services.engage.EngageSellerBean;
import com.limitless.services.engage.EngageSellerResponseBean;
import com.limitless.services.engage.SellerDeviceIdRespBean;
import com.limitless.services.engage.SellerLoginRequestBean;
import com.limitless.services.engage.SellerLoginResponseBean;
import com.limitless.services.engage.SellerPasswdRequestBean;
import com.limitless.services.engage.SellerPasswdResponseBean;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.dao.EngageSellerManager;
import com.limitless.services.payment.PaymentService.PaymentTxnBean;

@Path("/merchant")
public class EngageSellerResource {
	
	final static Logger logger = Logger.getLogger(EngageSellerResource.class);
	
	@POST
    @Path("/seller")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EngageSellerResponseBean addTxn(EngageSellerBean bean) throws Exception{
		
		EngageSellerResponseBean sellerResp = new EngageSellerResponseBean();
		
		try {
			EngageSeller seller = new EngageSeller();
			seller.setCitrusSellerId(bean.getCitrusSellerId());
			seller.setSellerName(bean.getSellerName());
			seller.setSellerEmail99(bean.getEmailId());
			seller.setSellerMobileNumber(bean.getMobileNumber());
			seller.setSellerPasswd99(bean.getPasswd());
			seller.setSellerCountryCode(bean.getCountryCode());
			seller.setSellerCity(bean.getCity());
			seller.setSellerAddress(bean.getAddress());
			seller.setSellerDeviceId(bean.getSellerDeviceId());
			seller.setSellerCountry(bean.getCountry());
			seller.setSellerType(bean.getSellerType());
			
			seller.setSellerKycDocType(bean.getKycDocType());
			seller.setSellerKycDocValue(bean.getKycDocValue());
			
			seller.setSellerLocationLatitude(bean.getLatitude());
			seller.setSellerLocationLongitude(bean.getLongitude());
			
			seller.setSellerSplitPercent(bean.getSplitPerent());
			
			EngageSellerManager manager = new EngageSellerManager();
			
			if( !(manager.checkDuplicateEmail(bean.getEmailId())) && !(manager.checkDuplicateMobile(bean.getMobileNumber())) ){
				manager.persist(seller);
				sellerResp.setSellerId(seller.getSellerId());
				sellerResp.setStatus(1);
				sellerResp.setMessage("Success");
			} else {
				sellerResp.setStatus(-1);
				sellerResp.setMessage("Failure - Duplicate Email / Mobile Number");
			}
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return sellerResp;
	}
	
	@GET
	@Path("/seller/{id}/deviceid")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerDeviceIdRespBean getSellerDeviceId(@PathParam("id") int id) throws Exception {
		EngageSellerManager manager = new EngageSellerManager();
		EngageSeller engageSeller = manager.findById(id);
		
		SellerDeviceIdRespBean bean = new SellerDeviceIdRespBean();
		bean.setSellerDeviceId(engageSeller.getSellerDeviceId());
		
		return bean;
	}
	
	@GET
	@Path("/getVersion")
	public Response getVersion() {
		String output = "1.0.0";
		logger.info("Reached getVersion..");
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SellerLoginResponseBean loginSeller(SellerLoginRequestBean reqBean) throws Exception{
		SellerLoginResponseBean respBean = new SellerLoginResponseBean();
		try{
			EngageSellerManager manager = new EngageSellerManager();
			respBean = manager.sellerLogin(reqBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return respBean;
	}
	
	@PUT
	@Path("/cpwd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SellerPasswdResponseBean changePasswd(SellerPasswdRequestBean reqBean)throws Exception{
		SellerPasswdResponseBean respBean = new SellerPasswdResponseBean();
		try{
			EngageSellerManager manager = new EngageSellerManager();
			respBean = manager.changeSellerPasswd(reqBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return respBean;
	}
	
	@GET
	@Path("/coordinates")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CoordinatesResponseBean> sellerCoordibates()throws Exception{
		List<CoordinatesResponseBean> coords = new ArrayList<CoordinatesResponseBean>();
		try{
			EngageSellerManager manager = new EngageSellerManager();
			coords = manager.sellerCoordinates();
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return coords;
	}
	
}
