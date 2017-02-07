package com.limitless.services.engage.order;

public class OrderProductsBean {
	private int productId;
	private int quantity;
	private int productPricesMapperId;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getProductPricesMapperId() {
		return productPricesMapperId;
	}
	public void setProductPricesMapperId(int productPricesMapperId) {
		this.productPricesMapperId = productPricesMapperId;
	}
}
