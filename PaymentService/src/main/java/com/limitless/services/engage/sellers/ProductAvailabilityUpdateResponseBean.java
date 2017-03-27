package com.limitless.services.engage.sellers;

public class ProductAvailabilityUpdateResponseBean {
	private int sellerId;
	private String message;
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
