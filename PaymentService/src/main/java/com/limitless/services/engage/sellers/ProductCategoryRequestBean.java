package com.limitless.services.engage.sellers;

import java.util.List;

public class ProductCategoryRequestBean {
	private int sellerId;
	private String categoryName;
	private String categoryImageUrl;
	private String categoryDescription;
	private boolean hasSubcategory;
	private List<ProductSubcategoryRequestBean> subcategoryList;
	
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
	public String getCategoryImageUrl() {
		return categoryImageUrl;
	}
	public void setCategoryImageUrl(String categoryImageUrl) {
		this.categoryImageUrl = categoryImageUrl;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public boolean isHasSubcategory() {
		return hasSubcategory;
	}
	public void setHasSubcategory(boolean hasSubcategory) {
		this.hasSubcategory = hasSubcategory;
	}
	public List<ProductSubcategoryRequestBean> getSubcategoryList() {
		return subcategoryList;
	}
	public void setSubcategoryList(List<ProductSubcategoryRequestBean> subcategoryList) {
		this.subcategoryList = subcategoryList;
	}
}
