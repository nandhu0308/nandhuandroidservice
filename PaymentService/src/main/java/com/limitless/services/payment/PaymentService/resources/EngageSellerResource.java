package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.limitless.services.engage.EngageSellerBean;
import com.limitless.services.engage.EngageSellerResponseBean;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.dao.EngageSellerManager;

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
			seller.setSellerId(bean.getSellerId());
			seller.setSellerName(bean.getSellerName());
			seller.setSellerEmail99(bean.getEmailId());
			seller.setSellerMobileNumber(bean.getMobileNumber());
			seller.setSellerPasswd99(bean.getPasswd());
			seller.setSellerCountryCode(bean.getCountryCode());
			seller.setSellerCity(bean.getCity());
			seller.setSellerAddress(bean.getAddress());
			seller.setSellerCountry(bean.getCountry());
			
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
	@Path("/getVersion")
	public Response getVersion() {
		String output = "1.0.0";
		logger.info("Reached getVersion..");
		return Response.status(200).entity(output).build();
	}
	
}
