package com.limitless.services.engage.restaurants;

import java.util.List;

public class NewRestaurantCategoryRequestBean {
	private int sellerId;
	private int restaurantId;
	private String categoryName;
	private List<NewRestaurantSubcategoryRequestBean> subcategoryRequestList;
	
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<NewRestaurantSubcategoryRequestBean> getSubcategoryRequestList() {
		return subcategoryRequestList;
	}
	public void setSubcategoryRequestList(List<NewRestaurantSubcategoryRequestBean> subcategoryRequestList) {
		this.subcategoryRequestList = subcategoryRequestList;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
}
