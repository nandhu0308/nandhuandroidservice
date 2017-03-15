package com.limitless.services.engage;

public class SellerRestaurantListBean {
	private int restaurantId;
	private String restaurantName;
	private String restaurantCity;
	private boolean takeAwayPod;
	private boolean eatInPod;
	private boolean homeDeliveryPod;
	
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantCity() {
		return restaurantCity;
	}
	public void setRestaurantCity(String restaurantCity) {
		this.restaurantCity = restaurantCity;
	}
	public boolean getTakeAwayPod() {
		return takeAwayPod;
	}
	public void setTakeAwayPod(boolean takeAwayPod) {
		this.takeAwayPod = takeAwayPod;
	}
	public boolean getEatInPod() {
		return eatInPod;
	}
	public void setEatInPod(boolean eatInPod) {
		this.eatInPod = eatInPod;
	}
	public boolean getHomeDeliveryPod() {
		return homeDeliveryPod;
	}
	public void setHomeDeliveryPod(boolean homeDeliveryPod) {
		this.homeDeliveryPod = homeDeliveryPod;
	}
	
}
