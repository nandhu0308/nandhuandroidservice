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

import com.limitless.services.engage.AliasCheckResponseBean;
import com.limitless.services.engage.AmbassadorResponseBean;
import com.limitless.services.engage.CoordinatesResponseBean;
import com.limitless.services.engage.CustomerNotifyListBean;
import com.limitless.services.engage.CustomerNotifyRequestBean;
import com.limitless.services.engage.CustomerNotifyResponseBean;
import com.limitless.services.engage.CustomerNotifyUpdateResponseBean;
import com.limitless.services.engage.EngageSellerBean;
import com.limitless.services.engage.EngageSellerResponseBean;
import com.limitless.services.engage.MerchantDeviceIdRequestBean;
import com.limitless.services.engage.MerchantDeviceIdResponseBean;
import com.limitless.services.engage.MerchantLogoutRequestBean;
import com.limitless.services.engage.MerchantLogoutResponseBean;
import com.limitless.services.engage.MerchantRequestCountBean;
import com.limitless.services.engage.MerchantRequestListBean;
import com.limitless.services.engage.NewMerchantsRequestBean;
import com.limitless.services.engage.SellerAdsListBean;
import com.limitless.services.engage.SellerBusinessCategoryBean;
import com.limitless.services.engage.SellerBusinessCategoryListBean;
import com.limitless.services.engage.SellerContactsRequestBean;
import com.limitless.services.engage.SellerContactsResponseBean;
import com.limitless.services.engage.SellerDeviceIdRespBean;
import com.limitless.services.engage.SellerLoginRequestBean;
import com.limitless.services.engage.SellerLoginResponseBean;
import com.limitless.services.engage.SellerPasswdRequestBean;
import com.limitless.services.engage.SellerPasswdResponseBean;
import com.limitless.services.engage.SellerRequestBean;
import com.limitless.services.engage.SellerRestaurantListBean;
import com.limitless.services.engage.SellerRestaurantsBean;
import com.limitless.services.engage.SellerTempRequestBean;
import com.limitless.services.engage.SellerTempResponseBean;
import com.limitless.services.engage.SellerUpdateRequestBean;
import com.limitless.services.engage.SellerUpdateResponseBean;
import com.limitless.services.engage.dao.EngageCustomerManager;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.dao.EngageSellerManager;
import com.limitless.services.engage.dao.SellerPayamentsConfiguration;
import com.limitless.services.engage.dao.SellerTempManager;
import com.limitless.services.engage.entertainment.BroadcasterChannelRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.entertainment.dao.BroadcasterManager;
import com.limitless.services.engage.restaurants.dao.RestaurantManager;
import com.limitless.services.engage.sellers.ProductBean;
import com.limitless.services.engage.sellers.SubMerchantListResponseBean;
import com.limitless.services.engage.sellers.product.dao.Product;
import com.limitless.services.engage.sellers.product.dao.ProductManager;
import com.limitless.services.payment.PaymentService.PaymentTxnBean;
import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/merchant")
public class EngageSellerResource {

	final static Logger logger = Logger.getLogger(EngageSellerResource.class);

	@POST
	@Path("/seller/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EngageSellerResponseBean addTxn(EngageSellerBean bean) throws Exception {

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
			seller.setSellerShopName(bean.getSellerShopName());
			seller.setSellerRole(bean.getSellerRole());

			seller.setSellerKycDocType(bean.getKycDocType());
			seller.setSellerKycDocValue(bean.getKycDocValue());
			// seller.setKycDocImage(bean.getKycDocImage());

			seller.setSellerLocationLatitude(bean.getLatitude());
			seller.setSellerLocationLongitude(bean.getLongitude());
			seller.setIsActive(bean.getIsActive());

			seller.setSellerSplitPercent(bean.getSplitPerent());
			seller.setMobileAlias(bean.getMobileAlias());
			seller.setAmbassadorMobile(bean.getAmbassadorMobile());
			seller.setBusinessCategory(bean.getBusinessCategory());
			seller.setMapMarkerName(bean.getMapMarkerName());
			EngageSellerManager manager = new EngageSellerManager();

			if (!manager.checkDuplicateEmail(bean.getEmailId())
					&& !manager.checkDuplicateMobile(bean.getMobileNumber())) {
				manager.persist(seller);
				sellerResp.setSellerId(seller.getSellerId());
				System.out.println("Seller Id: " + seller.getSellerId());
				sellerResp.setStatus(1);
				sellerResp.setMessage("Success");
				
				SellerPayamentsConfiguration payConfig = new SellerPayamentsConfiguration();
				payConfig.setSellerId(seller.getSellerId());
				payConfig.setCitrusSellerId(bean.getCitrusSellerId());
				payConfig.setSplitPercent(bean.getSplitPerent());
				if(bean.isPodAvailable()){
					payConfig.setPayOnDelivery(1);
				}
				else if(!bean.isPodAvailable()){
					payConfig.setPayOnDelivery(0);
				}
				payConfig.setDevliveryMInAmt(bean.getDeliveryMinAmount());
				payConfig.setDeliveryRadius(bean.getDeliveryRadius());
				payConfig.setDeliveryFee(bean.getDeliveryFee());
				if(bean.isConvenienceFee()){
					payConfig.setConvenienceFee(1);
				}
				else if(!bean.isConvenienceFee()){
					payConfig.setConvenienceFee(0);
				}
				manager.sellerPaymentConfigAdd(payConfig);

				if (bean.getIsActive() == 0) {
					SellerTempRequestBean tempRequestBean = new SellerTempRequestBean();
					if (bean.getCitrusSellerId() == 0) {
						tempRequestBean.setSellerId(seller.getSellerId());
						System.out.println("Seller Id: " + seller.getSellerId());
						tempRequestBean.setSellerBankAccountNumber(bean.getSellerBankAccountNumber());
						tempRequestBean.setSellerIfsc(bean.getSellerIfsc());
						tempRequestBean.setSellerBankProof(bean.getSellerBankProof());
						tempRequestBean.setSellerKycImage(bean.getKycDocImage());
						tempRequestBean.setStatus("NOTYET");
					} else if (bean.getCitrusSellerId() > 0) {
						tempRequestBean.setSellerId(seller.getSellerId());
						tempRequestBean.setStatus("ONBOARDED");
					}
					SellerTempManager tempMananger = new SellerTempManager();
					SellerTempResponseBean tempResponseBean = tempMananger.addTempDetails(tempRequestBean);
				}

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
	@Path("/seller/{id}/deviceidbg")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerDeviceIdRespBean getSellerDeviceId(@PathParam("id") int id) throws Exception {
		EngageSellerManager manager = new EngageSellerManager();
		EngageSeller engageSeller = manager.findById(id);

		SellerDeviceIdRespBean bean = new SellerDeviceIdRespBean();
		if (engageSeller == null) {
			bean.setSellerDeviceId("NA");
		} else {
			bean.setSellerDeviceId(engageSeller.getSellerDeviceId());
		}

		return bean;
	}

	@GET
	@Path("/getVersion")
	public Response getVersion() {
		logger.info("Reached Seller getVersion..");
		String version = "";

		try {
			EngageSellerManager manager = new EngageSellerManager();
			version = manager.getSellerVersion();
		} catch (Exception e) {
			logger.error("API Error", e);
		}

		return Response.status(200).entity(version).build();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SellerLoginResponseBean loginSeller(SellerLoginRequestBean reqBean) throws Exception {
		SellerLoginResponseBean respBean = new SellerLoginResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			respBean = manager.sellerLogin(reqBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return respBean;
	}

	@PUT
	@Path("/cpwd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SellerPasswdResponseBean changePasswd(SellerPasswdRequestBean reqBean) throws Exception {
		SellerPasswdResponseBean respBean = new SellerPasswdResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			respBean = manager.changeSellerPasswd(reqBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return respBean;
	}

	@GET
	@Path("/coordinates")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CoordinatesResponseBean> sellerCoordibates() throws Exception {
		List<CoordinatesResponseBean> coords = new ArrayList<CoordinatesResponseBean>();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			coords = manager.sellerCoordinates();
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return coords;
	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SellerLoginResponseBean getSeller(SellerRequestBean requestBean) throws Exception {
		SellerLoginResponseBean responseBean = new SellerLoginResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			responseBean = manager.getSellerBySearchString(requestBean.getSearchString());

			if (responseBean.getMessage().equals("Success")) {
				if (responseBean.getBusinessType().equalsIgnoreCase("restaurant")
						|| responseBean.getBusinessCategory().equalsIgnoreCase("restaurant")
						|| responseBean.getBusinessCategory().equalsIgnoreCase("hotel")) {
					RestaurantManager restaurantManager = new RestaurantManager();
					List<SellerRestaurantListBean> restaurantListBeans = restaurantManager
							.getSellerRestaurants(responseBean.getSellerId());
					responseBean.setRestaurants(restaurantListBeans);
				}
				else if(responseBean.getBusinessType().equalsIgnoreCase("broadcaster")){
					BroadcasterChannelRequestBean channelRequestBean = new BroadcasterChannelRequestBean();
					channelRequestBean.setBroadcasterName(responseBean.getSellerName());
					BroadcasterManager broadcasterManager = new BroadcasterManager();
					BroadcasterChannelResponseBean channelResponseBean = broadcasterManager.getBroadcasterChannel(channelRequestBean);
					responseBean.setChannelBean(channelResponseBean);
				}

				SellerContactsResponseBean contactsResponseBean = manager.addSearchMapper(requestBean.getCustomerId(),
						responseBean.getSellerId());
			}
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@GET
	@Path("/get/{customerId}/{sellerMobileNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerLoginResponseBean getSeller(@PathParam("sellerMobileNumber") String sellerMobileNumber,
			@PathParam("customerId") int customerId) throws Exception {
		SellerLoginResponseBean responseBean = new SellerLoginResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			responseBean = manager.getSellerByMobile(sellerMobileNumber);

			if (responseBean.getMessage().equals("Success")) {
				if (responseBean.getBusinessType().equals("restaurant")) {
					RestaurantManager restaurantManager = new RestaurantManager();
					List<SellerRestaurantListBean> restaurantListBeans = restaurantManager
							.getSellerRestaurants(responseBean.getSellerId());
					responseBean.setRestaurants(restaurantListBeans);
				} 
				SellerContactsResponseBean contactsResponseBean = manager.addSearchMapper(customerId,
						responseBean.getSellerId());
			}
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@GET
	@Path("/get/{sellerMobileNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerLoginResponseBean getSeller2(@PathParam("sellerMobileNumber") String sellerMobileNumber)
			throws Exception {
		SellerLoginResponseBean responseBean = new SellerLoginResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			responseBean = manager.getSellerByMobile(sellerMobileNumber);

			if (responseBean.getMessage().equals("Success")) {
				if (responseBean.getBusinessType().equals("restaurant")) {
					RestaurantManager restaurantManager = new RestaurantManager();
					List<SellerRestaurantListBean> restaurantListBeans = restaurantManager
							.getSellerRestaurants(responseBean.getSellerId());
					responseBean.setRestaurants(restaurantListBeans);
				}
				/*
				 * else if(responseBean.getBusinessType().equals("eCommerce")){
				 * ProductManager productManager = new ProductManager();
				 * List<ProductBean> productList =
				 * productManager.getAllProducts(responseBean.getSellerId());
				 * responseBean.setProducts(productList); }
				 */
			}
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@GET
	@Path("/ambassador/{ambassadorMobileNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public AmbassadorResponseBean checkAmbassadorCount(
			@PathParam("ambassadorMobileNumber") String ambassadorMobileNumber) throws Exception {
		AmbassadorResponseBean responseBean = new AmbassadorResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			responseBean = manager.ambassadorCount(ambassadorMobileNumber);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@GET
	@Path("/aliascheck/{aliasNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public AliasCheckResponseBean checkAlias(@PathParam("aliasNumber") String aliasNumber) throws Exception {
		AliasCheckResponseBean responseBean = new AliasCheckResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			responseBean = manager.getAliasNumber(aliasNumber);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@GET
	@Path("/requestcount")
	@Produces(MediaType.APPLICATION_JSON)
	public MerchantRequestCountBean getCounts() throws Exception {
		MerchantRequestCountBean countBean = new MerchantRequestCountBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			countBean = manager.getRequestCount();
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return countBean;
	}

	@GET
	@Path("/requestlist")
	@Produces(MediaType.APPLICATION_JSON)
	public NewMerchantsRequestBean requestNewMerchantsList() throws Exception {
		NewMerchantsRequestBean requestBean = new NewMerchantsRequestBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			requestBean = manager.getRequestList();
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return requestBean;
	}

	@GET
	@Path("/requestseller/{sellerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public MerchantRequestListBean requestSeller(@PathParam("sellerId") int sellerId) throws Exception {
		MerchantRequestListBean listBean = new MerchantRequestListBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			listBean = manager.sellerRequest(sellerId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return listBean;
	}

	@PUT
	@Path("/activate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SellerUpdateResponseBean activteSeller(SellerUpdateRequestBean requestBean) throws Exception {
		SellerUpdateResponseBean responseBean = new SellerUpdateResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			responseBean = manager.sellerActivate(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@POST
	@Path("/sync")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SellerContactsResponseBean addContacts(SellerContactsRequestBean requestBean) throws Exception {
		SellerContactsResponseBean responseBean = new SellerContactsResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			responseBean = manager.addSellerContacts(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@POST
	@Path("/post/circle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postCircle(CustomerNotifyRequestBean requestBean) throws Exception {
		EngageSellerManager manager = new EngageSellerManager();
		CustomerNotifyResponseBean responseBean = manager.postToCircle(requestBean);
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postAll(CustomerNotifyRequestBean requestBean) throws Exception {
		EngageSellerManager manager = new EngageSellerManager();
		CustomerNotifyResponseBean responseBean = manager.postToAll(requestBean);
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@PUT
	@Path("/post/update/{notifyId}/{statusCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postUpdate(@PathParam("notifyId") int notifyId, @PathParam("statusCode") int statusCode) {
		EngageSellerManager manager = new EngageSellerManager();
		CustomerNotifyUpdateResponseBean responseBean = manager.updateNotifyRequest(notifyId, statusCode);
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@GET
	@Path("/post/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postList() {
		EngageSellerManager manager = new EngageSellerManager();
		CustomerNotifyListBean listBean = manager.getAllNotifyRequest();
		if (listBean != null) {
			return Response.status(Status.OK).entity(listBean).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/post/get/{sellerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerNotifyListBean getAllPosts(@PathParam("sellerId") int sellerId) throws Exception {
		CustomerNotifyListBean notifyListBean = new CustomerNotifyListBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			notifyListBean = manager.getSellerPostList(sellerId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return notifyListBean;
	}

	@PUT
	@Path("/deviceid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MerchantDeviceIdResponseBean updateDeviceId(MerchantDeviceIdRequestBean requestBean) throws Exception {
		MerchantDeviceIdResponseBean responseBean = new MerchantDeviceIdResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			responseBean = manager.sellerDeviceIdUpadte(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MerchantLogoutResponseBean sellerLogout(MerchantLogoutRequestBean requestBean) throws Exception {
		MerchantLogoutResponseBean responseBean = new MerchantLogoutResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			responseBean = manager.logoutSeller(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@GET
	@Path("/getsubmerchants/{sellerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SubMerchantListResponseBean getSubMerchants(@PathParam("sellerId") int sellerId) throws Exception {
		SubMerchantListResponseBean listBean = new SubMerchantListResponseBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			listBean = manager.getSubMerchants(sellerId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return listBean;
	}

	@GET
	@Path("/restaurants/{sellerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerRestaurantsBean getRestaurants(@PathParam("sellerId") int sellerId) throws Exception {
		SellerRestaurantsBean restaurantsBean = new SellerRestaurantsBean();
		try {
			EngageSellerManager manager = new EngageSellerManager();
			restaurantsBean = manager.getSellerRestaurants(sellerId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return restaurantsBean;
	}
	
	@GET
	@Path("/ads")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerAdsListBean getSellerAds() throws Exception{
		SellerAdsListBean listBean = new SellerAdsListBean();
		try{
			EngageSellerManager manager = new EngageSellerManager();
			listBean = manager.getSellerAds();
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return listBean;
	}
	
	@GET
	@Path("/list/business/category")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerBusinessCategoryListBean getBusinessCategoryList() throws Exception{
		SellerBusinessCategoryListBean listBean = new SellerBusinessCategoryListBean();
		try{
			EngageSellerManager manager = new EngageSellerManager();
			listBean = manager.getSellerBusinessCategoryList();
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return listBean;
	}
	
	@GET
	@Path("/get/business/category/{categoryName}")
	@Produces(MediaType.APPLICATION_JSON)
	public SellerBusinessCategoryBean getBusinessCategorySellerList(@PathParam("categoryName") String categoryName) throws Exception{
		SellerBusinessCategoryBean categoryBean = new SellerBusinessCategoryBean();
		try{
			EngageSellerManager manager = new EngageSellerManager();
			categoryBean = manager.getBusinessCategorySellerList(categoryName);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return categoryBean;
	}

}
