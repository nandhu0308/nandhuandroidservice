package com.limitless.services.engage.restaurants;

public class RestaurantOrderStatusUpdateRequestBean {
	private int restaurantId;
	private int orderId;
	private int orderStatus;
	private String orderStatusString;
	
	public String getOrderStatusString() {
		return orderStatusString;
	}
	public void setOrderStatusString(String orderStatusString) {
		this.orderStatusString = orderStatusString;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
}
