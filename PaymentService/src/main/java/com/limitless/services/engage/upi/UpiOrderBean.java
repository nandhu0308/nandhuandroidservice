package com.limitless.services.engage.upi;


public class UpiOrderBean {
	
	private int orderId;
	private int customerId;
	
	private float orderAmount;
	
	public static enum OrderStatus {
		PAYMENT_INITIATED, PAYMENT_SUCCESSFUL, PAYMENT_FAILED;
	}
	private OrderStatus orderStatus;
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public float getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(float orderAmount) {
		this.orderAmount = orderAmount;
	}
	
}
