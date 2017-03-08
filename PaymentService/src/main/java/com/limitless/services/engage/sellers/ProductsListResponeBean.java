package com.limitless.services.engage.sellers;

import java.util.List;

public class ProductsListResponeBean {
	private int categoryId;
	private String categoryName;
	private int subCategoryId;
	private String subCategoryName;
	private List<ProductBean> productsList;
	private String message;
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
	public List<ProductBean> getProductsList() {
		return productsList;
	}
	public void setProductsList(List<ProductBean> productsList) {
		this.productsList = productsList;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
