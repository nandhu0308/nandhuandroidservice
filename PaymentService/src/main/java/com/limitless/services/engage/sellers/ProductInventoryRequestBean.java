package com.limitless.services.engage.sellers;

public class ProductInventoryRequestBean {
	private int productId;
	private int productStock;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductStock() {
		return productStock;
	}
	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}
}
