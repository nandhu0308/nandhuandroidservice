package com.limitless.services.payment.PaymentService;

public class SplitResponseBean {
	
	private int splitId;
	private String message = "Failure";
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSplitId() {
		return splitId;
	}

	public void setSplitId(int splitId) {
		this.splitId = splitId;
	}

	
}
