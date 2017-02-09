package com.limitless.services.engage.restaurants;

import java.util.List;

public class RestaurantSubcategoryListBean {
	private int categoryId;
	private int subCategoryId;
	private String subCategoryName;
	private List<RestaurantItemListBean> itemsList;
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(int subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public List<RestaurantItemListBean> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<RestaurantItemListBean> itemsList) {
		this.itemsList = itemsList;
	}
}
