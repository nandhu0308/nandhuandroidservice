package com.limitless.services.payment.PaymentService;

public class CreditReminderRequestBean {
	private int sellerId;
	private int customerId;
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
}
