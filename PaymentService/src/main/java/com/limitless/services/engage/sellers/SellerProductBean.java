package com.limitless.services.engage.sellers;

import java.util.List;

public class SellerProductBean {
	private String message;
	private List<ProductBean> products;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ProductBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductBean> products) {
		this.products = products;
	}
}
