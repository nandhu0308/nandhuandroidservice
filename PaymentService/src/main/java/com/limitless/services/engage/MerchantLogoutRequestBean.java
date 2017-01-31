package com.limitless.services.engage;

public class MerchantLogoutRequestBean {
	private int sellerId;
	private int sessionId;
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
}
