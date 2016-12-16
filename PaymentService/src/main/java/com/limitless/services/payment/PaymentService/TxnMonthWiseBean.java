package com.limitless.services.payment.PaymentService;

public class TxnMonthWiseBean {
	private String month;
	private double totalAmount;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
