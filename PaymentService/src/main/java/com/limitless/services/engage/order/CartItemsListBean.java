package com.limitless.services.engage.order;

public class CartItemsListBean {
	private int cartDetailId;
	private int productId;
	private int productQuantity;
	private int productPricesMapperId;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public int getProductPricesMapperId() {
		return productPricesMapperId;
	}
	public void setProductPricesMapperId(int productPricesMapperId) {
		this.productPricesMapperId = productPricesMapperId;
	}
	public int getCartDetailId() {
		return cartDetailId;
	}
	public void setCartDetailId(int cartDetailId) {
		this.cartDetailId = cartDetailId;
	}
	
}
