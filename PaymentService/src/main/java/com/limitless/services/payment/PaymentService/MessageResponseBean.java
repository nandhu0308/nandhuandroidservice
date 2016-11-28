package com.limitless.services.payment.PaymentService;

public class MessageResponseBean {
	private int txnId;
	private String message;
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
