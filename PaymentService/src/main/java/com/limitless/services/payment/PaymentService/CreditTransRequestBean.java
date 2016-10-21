package com.limitless.services.payment.PaymentService;

public class CreditTransRequestBean {
	
	private int customerId;
	private int sellerId;
	private float transAmount;
	private float creditAmount;
	private String sellerName;
	private String status;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public float getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(float transAmount) {
		this.transAmount = transAmount;
	}
	public float getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(float creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
