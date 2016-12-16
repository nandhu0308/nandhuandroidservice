package com.limitless.services.payment.PaymentService;

public class TxnDayWiseBean {
	private String date;
	private double totalAmount;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
