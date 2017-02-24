package com.limitless.services.engage.restaurants;

public class RestaurantOrderResponseBean {
	private int restaurantOrderId;
	private float totalAmount;
	private int sellerId;
	private int citrusSellerId;
	private String sellerName;
	private String sellerEmail;
	private String sellerMobileName;
	public int getRestaurantOrderId() {
		return restaurantOrderId;
	}
	public void setRestaurantOrderId(int restaurantOrderId) {
		this.restaurantOrderId = restaurantOrderId;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	public String getSellerMobileName() {
		return sellerMobileName;
	}
	public void setSellerMobileName(String sellerMobileName) {
		this.sellerMobileName = sellerMobileName;
	}
}
