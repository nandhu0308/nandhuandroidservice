package com.limitless.services.engage.sellers;

public class ProductSubcategoryRequestBean {
	private int categoryId;
	private int subcategoryId;
	private String subcategoryName;
	private String subcategoryDescription;
	private String subcategoryImageUrl;
	
	public String getSubcategoryName() {
		return subcategoryName;
	}
	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	public String getSubcategoryDescription() {
		return subcategoryDescription;
	}
	public void setSubcategoryDescription(String subcategoryDescription) {
		this.subcategoryDescription = subcategoryDescription;
	}
	public String getSubcategoryImageUrl() {
		return subcategoryImageUrl;
	}
	public void setSubcategoryImageUrl(String subcategoryImageUrl) {
		this.subcategoryImageUrl = subcategoryImageUrl;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getSubcategoryId() {
		return subcategoryId;
	}
	public void setSubcategoryId(int subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
}
