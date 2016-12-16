package com.limitless.services.payment.PaymentService;

public class CreditRespBean {
	
	private int creditId;
	private String message = "Failure";
	
	public int getCreditId() {
		return creditId;
	}
	public void setCreditId(int creditId) {
		this.creditId = creditId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
