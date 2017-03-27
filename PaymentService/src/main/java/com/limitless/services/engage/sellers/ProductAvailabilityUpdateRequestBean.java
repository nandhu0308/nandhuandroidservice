package com.limitless.services.engage.sellers;

import java.util.List;

public class ProductAvailabilityUpdateRequestBean {
	private int sellerId;
	private List<Integer> productIds;
	
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public List<Integer> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}
	
}
