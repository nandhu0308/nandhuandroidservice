package com.limitless.services.engage.restaurants;

public class RestaurantOrderStatusUpdateResponseBean {
	private int orderId;
	private int restaurantId;
	private String previousStatus;
	private String currentStatus;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getPreviousStatus() {
		return previousStatus;
	}
	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
}
