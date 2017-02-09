package com.limitless.services.engage.restaurants;

import java.util.List;

public class RestaurantBean {
	private int restaurantId;
	private String restaurantName;
	private String restaurantCity;
	private int restaurantOrderStyle;
	private List<RestaurantItemListBean> itemsList;
	private List<RestaurantCategoryListBean> categorysList;
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
	public int getRestaurantOrderStyle() {
		return restaurantOrderStyle;
	}
	public void setRestaurantOrderStyle(int restaurantOrderStyle) {
		this.restaurantOrderStyle = restaurantOrderStyle;
	}
	public List<RestaurantItemListBean> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<RestaurantItemListBean> itemsList) {
		this.itemsList = itemsList;
	}
	public List<RestaurantCategoryListBean> getCategorysList() {
		return categorysList;
	}
	public void setCategorysList(List<RestaurantCategoryListBean> categorysList) {
		this.categorysList = categorysList;
	}
}
