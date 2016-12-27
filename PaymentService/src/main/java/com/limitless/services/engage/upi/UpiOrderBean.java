package com.limitless.services.engage.upi;


public class UpiOrderBean {
	
	private int orderId;
	private int customerId;
	private int sellerId;
	private String txnNotes;
	private float orderAmount;
	
	private String iciciTxnNo;
	private String iciciTxnTime;
	private String orderPaymentType;
	
	private String errorCode;
	
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
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getTxnNotes() {
		return txnNotes;
	}
	public void setTxnNotes(String txnNotes) {
		this.txnNotes = txnNotes;
	}
	public String getIciciTxnNo() {
		return iciciTxnNo;
	}
	public void setIciciTxnNo(String iciciTxnNo) {
		this.iciciTxnNo = iciciTxnNo;
	}
	public String getIciciTxnTime() {
		return iciciTxnTime;
	}
	public void setIciciTxnTime(String iciciTxnTime) {
		this.iciciTxnTime = iciciTxnTime;
	}
	public String getOrderPaymentType() {
		return orderPaymentType;
	}
	public void setOrderPaymentType(String orderPaymentType) {
		this.orderPaymentType = orderPaymentType;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}