package com.limitless.services.engage;

public class SellerUpdateResponseBean {
	private int sellerId;
	private int citrusSellerId;
	private String message;
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
