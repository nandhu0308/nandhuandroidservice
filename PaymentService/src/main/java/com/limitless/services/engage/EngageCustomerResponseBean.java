package com.limitless.services.engage;

public class EngageCustomerResponseBean {
	
	private int customerId;
	private String message = "Failure";
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
}
