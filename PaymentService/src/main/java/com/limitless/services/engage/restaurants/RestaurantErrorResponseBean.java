package com.limitless.services.engage.restaurants;

public class RestaurantErrorResponseBean {
	private int restaurantId;
	private String message;
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
