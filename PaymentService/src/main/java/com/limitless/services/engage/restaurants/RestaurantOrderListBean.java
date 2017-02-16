package com.limitless.services.engage.restaurants;

import com.limitless.services.engage.AddressListBean;

public class RestaurantOrderListBean {
	private int orderId;
	private int restaurantId;
	private String restaurantName;
	private String restaurantCity;
	private String restaurantMobileNumber;
	private int customerId;
	private String customerName;
	private String customerMobileNumber;
	private float orderTotalAmount;
	private String orderStyle;
	private AddressListBean deliveryAddress;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantCity() {
		return restaurantCity;
	}
	public void setRestaurantCity(String restaurantCity) {
		this.restaurantCity = restaurantCity;
	}
	public String getRestaurantMobileNumber() {
		return restaurantMobileNumber;
	}
	public void setRestaurantMobileNumber(String restaurantMobileNumber) {
		this.restaurantMobileNumber = restaurantMobileNumber;
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
	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	public float getOrderTotalAmount() {
		return orderTotalAmount;
	}
	public void setOrderTotalAmount(float orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}
	public String getOrderStyle() {
		return orderStyle;
	}
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	public AddressListBean getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(AddressListBean deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
}