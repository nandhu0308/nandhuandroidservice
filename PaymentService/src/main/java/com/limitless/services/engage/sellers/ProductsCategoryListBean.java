package com.limitless.services.engage.sellers;

import java.util.List;

public class ProductsCategoryListBean {
	private int categoryId;
	private String productCategorName;
	private List<ProductSubCategoryListBean> subcategoryList;
	private List<ProductBean> productsList;
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getProductCategorName() {
		return productCategorName;
	}
	public void setProductCategorName(String productCategorName) {
		this.productCategorName = productCategorName;
	}
	public List<ProductSubCategoryListBean> getSubcategoryList() {
		return subcategoryList;
	}
	public void setSubcategoryList(List<ProductSubCategoryListBean> subcategoryList) {
		this.subcategoryList = subcategoryList;
	}
	public List<ProductBean> getProductsList() {
		return productsList;
	}
	public void setProductsList(List<ProductBean> productsList) {
		this.productsList = productsList;
	}
}
