package com.limitless.services.engage.restaurants;

public class RestaurantOrderResponseBean {
	private int restaurantOrderId;
	private float totalAmount;
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
}
