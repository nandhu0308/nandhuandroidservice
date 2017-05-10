package com.limitless.services.engage;

public class PromoCodeResponseBean {
	private String message;
	private int promoCodeId;
	private int sellerId;
	private String promoCode;
	private float rate;
	private String rateType;
	private String expiryDate;
	private boolean isActive;
	
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
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
