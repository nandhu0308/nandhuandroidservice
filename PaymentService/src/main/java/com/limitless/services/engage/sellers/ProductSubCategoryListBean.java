package com.limitless.services.engage.sellers;

import java.util.List;

public class ProductSubCategoryListBean {
	private int subcategoryId;
	private String subcategoryName;
	private int categoryId;
	//private List<ProductBean> productsList;
	public int getSubcategoryId() {
		return subcategoryId;
	}
	public void setSubcategoryId(int subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
	public String getSubcategoryName() {
		return subcategoryName;
	}
	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
//	public List<ProductBean> getProductsList() {
//		return productsList;
//	}
//	public void setProductsList(List<ProductBean> productsList) {
//		this.productsList = productsList;
//	}
}
