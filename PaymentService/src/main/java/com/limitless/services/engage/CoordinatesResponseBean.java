package com.limitless.services.engage;

import javax.persistence.Column;

public class CoordinatesResponseBean {

	private int sellerId;
	private String sellerName;
	private String shopName;
	private String sellerMobile;
	private float latitude;
	private float longitude;
	private String businessCategory;
	private String mapMarkerName;

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getSellerMobile() {
		return sellerMobile;
	}

	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
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
}
