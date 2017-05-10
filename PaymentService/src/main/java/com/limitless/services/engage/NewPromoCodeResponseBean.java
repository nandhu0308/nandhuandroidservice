package com.limitless.services.engage;

public class NewPromoCodeResponseBean {
	private String message;
	private int promoCodeId;
	private int sellerId;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getPromoCodeId() {
		return promoCodeId;
	}
	public void setPromoCodeId(int promoCodeId) {
		this.promoCodeId = promoCodeId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
}
