package com.limitless.services.engage.order;

import com.limitless.services.engage.AddressListBean;

public class OrdersListBean {
	private int orderId;
	private int customerId;
	private String customerName;
	private String customerMobileNumber;
	private int sellerId;
	private String sellerName;
	private int citrusSellerId;
	private String sellerMobileNumber;
	private double totalAmount;
	private float orderDeliveryFee;
	private String time;
	private String orderStatus;
	private String paymentMode;
	private AddressListBean addressBean;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public AddressListBean getAddressBean() {
		return addressBean;
	}
	public void setAddressBean(AddressListBean addressBean) {
		this.addressBean = addressBean;
	}
	public String getSellerMobileNumber() {
		return sellerMobileNumber;
	}
	public void setSellerMobileNumber(String sellerMobileNumber) {
		this.sellerMobileNumber = sellerMobileNumber;
	}
	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public float getOrderDeliveryFee() {
		return orderDeliveryFee;
	}
	public void setOrderDeliveryFee(float orderDeliveryFee) {
		this.orderDeliveryFee = orderDeliveryFee;
	}
}
