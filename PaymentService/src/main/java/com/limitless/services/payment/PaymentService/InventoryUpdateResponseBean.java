package com.limitless.services.payment.PaymentService;

public class InventoryUpdateResponseBean {
	private int orderId;
	private String message;
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
