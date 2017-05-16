package com.limitless.services.engage;

import java.util.List;

import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.sellers.ProductBean;

public class SellerLoginResponseBean {
	
	private String sellerName;
	private int sellerId;
	private int citrusSellerId;
	private String mobileNumber;
	private String message;
	private int status;
	private String sellerAddress;
	private String sellerCity;
	private String sellerType;
	private String businessType;
	private String sellerRole;
	private String brandingUrl;
	private String sessionKey;
	private int sessionId;
	private String businessCategory;
	private int ecomPayment;
	private String mapMarkerName;
	private List<ProductBean> products;
	private List<SellerRestaurantListBean> restaurants;
	private String aboutSeller;
	private boolean podAvailable;
	private boolean convenienceFee;
	private float deliveryMinAmount;
	private float deliveryFee;
	private int deliveryRadius;
	private float packagingFee;
	private String paymentAlert;
	private boolean cobranding;
	private BroadcasterChannelResponseBean channelBean;
	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSellerAddress() {
		return sellerAddress;
	}
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}
	public String getSellerCity() {
		return sellerCity;
	}
	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getSellerRole() {
		return sellerRole;
	}
	public void setSellerRole(String sellerRole) {
		this.sellerRole = sellerRole;
	}
	public String getBrandingUrl() {
		return brandingUrl;
	}
	public void setBrandingUrl(String brandingUrl) {
		this.brandingUrl = brandingUrl;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public List<ProductBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductBean> products) {
		this.products = products;
	}
	public List<SellerRestaurantListBean> getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(List<SellerRestaurantListBean> restaurants) {
		this.restaurants = restaurants;
	}
	public String getBusinessCategory() {
		return businessCategory == null || businessCategory.equalsIgnoreCase("") ? "Shop" : businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getMapMarkerName() {
		return mapMarkerName == null || mapMarkerName.equalsIgnoreCase("") ? "default" : mapMarkerName;
	}

	public void setMapMarkerName(String mapMarkerName) {
		this.mapMarkerName = mapMarkerName;
	}
	
	public String getAboutSeller() {
		return aboutSeller;
	}
	
	public void setAboutSeller(String aboutSeller) {
		this.aboutSeller = aboutSeller;
	}
	public boolean isPodAvailable() {
		return podAvailable;
	}
	public void setPodAvailable(boolean podAvailable) {
		this.podAvailable = podAvailable;
	}
	public boolean isConvenienceFee() {
		return convenienceFee;
	}
	public void setConvenienceFee(boolean convenienceFee) {
		this.convenienceFee = convenienceFee;
	}
	public float getDeliveryMinAmount() {
		return deliveryMinAmount;
	}
	public void setDeliveryMinAmount(float deliveryMinAmount) {
		this.deliveryMinAmount = deliveryMinAmount;
	}
	public float getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(float deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public int getDeliveryRadius() {
		return deliveryRadius;
	}
	public void setDeliveryRadius(int deliveryRadius) {
		this.deliveryRadius = deliveryRadius;
	}
	public boolean isCobranding() {
		return cobranding;
	}
	public void setCobranding(boolean cobranding) {
		this.cobranding = cobranding;
	}
	public BroadcasterChannelResponseBean getChannelBean() {
		return channelBean;
	}
	public void setChannelBean(BroadcasterChannelResponseBean channelBean) {
		this.channelBean = channelBean;
	}
	public String getPaymentAlert() {
		return paymentAlert;
	}
	public void setPaymentAlert(String paymentAlert) {
		this.paymentAlert = paymentAlert;
	}
	public int getEcomPayment() {
		return ecomPayment;
	}
	public void setEcomPayment(int ecomPayment) {
		this.ecomPayment = ecomPayment;
	}
	public float getPackagingFee() {
		return packagingFee;
	}
	public void setPackagingFee(float packagingFee) {
		this.packagingFee = packagingFee;
	}
}
