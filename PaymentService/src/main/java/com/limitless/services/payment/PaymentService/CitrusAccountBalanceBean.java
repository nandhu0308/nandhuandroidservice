package com.limitless.services.payment.PaymentService;

public class CitrusAccountBalanceBean {
	private String message;
	private double accountBalance;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
}
