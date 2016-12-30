package com.limitless.services.payment.PaymentService;

public class MasterTxnResponseBean {
	private int masterId;
	private String message;
	public int getMasterId() {
		return masterId;
	}
	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
