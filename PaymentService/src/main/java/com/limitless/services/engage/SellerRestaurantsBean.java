package com.limitless.services.engage;

import java.util.List;

public class SellerRestaurantsBean {
	private int sellerId;
	private String sellerName;
	private String sellerEmailId;
	private String sellerMobileNumber;
	private String sellerCity;
	private int citrusSellerId;
	private List<SellerRestaurantListBean> restaurantList;
	private String message;
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
	public String getSellerEmailId() {
		return sellerEmailId;
	}
	public void setSellerEmailId(String sellerEmailId) {
		this.sellerEmailId = sellerEmailId;
	}
	public String getSellerMobileNumber() {
		return sellerMobileNumber;
	}
	public void setSellerMobileNumber(String sellerMobileNumber) {
		this.sellerMobileNumber = sellerMobileNumber;
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
	public List<SellerRestaurantListBean> getRestaurantList() {
		return restaurantList;
	}
	public void setRestaurantList(List<SellerRestaurantListBean> restaurantList) {
		this.restaurantList = restaurantList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
