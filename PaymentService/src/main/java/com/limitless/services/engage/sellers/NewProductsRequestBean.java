package com.limitless.services.engage.sellers;

import java.util.List;

public class NewProductsRequestBean {
	private int sellerId;
	private List<ProductBean> productList;
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public List<ProductBean> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductBean> productList) {
		this.productList = productList;
	}
}
