package com.limitless.services.engage.upi;

public class UpiTxnHistoryBean {
	private int orderId;
	private int customerId;
	private String customerName;
	private int sellerId;
	private String sellerName;
	private double orderAmount;
	private String txnNotes;
	private String iciciTxnNo;
	private String iciciTxnTime;
	private String orderStatus;
	private String paymentType;
	private String orderTime;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
}
