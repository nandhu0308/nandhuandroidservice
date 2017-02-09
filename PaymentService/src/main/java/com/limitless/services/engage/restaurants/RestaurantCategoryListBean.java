package com.limitless.services.engage.restaurants;

import java.util.List;

public class RestaurantCategoryListBean {
	private int restaurantId;
	private int categoryId;
	private String categoryName;
	private List<RestaurantSubcategoryListBean> subcategorysList;
	private List<RestaurantItemListBean> itemsList;
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<RestaurantSubcategoryListBean> getSubcategorysList() {
		return subcategorysList;
	}
	public void setSubcategorysList(List<RestaurantSubcategoryListBean> subcategorysList) {
		this.subcategorysList = subcategorysList;
	}
	public List<RestaurantItemListBean> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<RestaurantItemListBean> itemsList) {
		this.itemsList = itemsList;
	}
}
