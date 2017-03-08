package com.limitless.services.engage.sellers;

import java.util.List;

public class ProductCSCListBean {
	private int sellerId;
	private String sellerName;
	private String sellerCity;
	private int citrusSellerId;
	private List<ProductsCategoryListBean> categoryList;
	//private List<ProductBean> productsList;
	private String message;
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerCity() {
		return sellerCity;
	}
	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public List<ProductsCategoryListBean> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<ProductsCategoryListBean> categoryList) {
		this.categoryList = categoryList;
	}
//	public List<ProductBean> getProductsList() {
//		return productsList;
//	}
//	public void setProductsList(List<ProductBean> productsList) {
//		this.productsList = productsList;
//	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
