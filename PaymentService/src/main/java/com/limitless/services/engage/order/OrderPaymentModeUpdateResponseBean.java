package com.limitless.services.engage.order;

public class OrderPaymentModeUpdateResponseBean {
	private int orderId;
	private String paymentMode;
	private String prevPaymentMode;
	public String getPrevPaymentMode() {
		return prevPaymentMode;
	}
	public void setPrevPaymentMode(String prevPaymentMode) {
		this.prevPaymentMode = prevPaymentMode;
	}
	private String message;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
