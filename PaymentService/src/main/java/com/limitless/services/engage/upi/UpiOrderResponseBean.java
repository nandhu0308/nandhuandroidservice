package com.limitless.services.engage.upi;

public class UpiOrderResponseBean {
	
	private int orderId = 0;
	private String message = "Failure";
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
