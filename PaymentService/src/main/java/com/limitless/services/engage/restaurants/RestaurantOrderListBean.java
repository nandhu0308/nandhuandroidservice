package com.limitless.services.engage.restaurants;

import com.limitless.services.engage.AddressListBean;

public class RestaurantOrderListBean {
	private int orderId;
	private int restaurantId;
	private String restaurantName;
	private String restaurantCity;
	private String restaurantAddress;
	private String restaurantMobileNumber;
	private String sellerName;
	private int sellerId;
	private int citrusSellerId;
	private int customerId;
	private String customerName;
	private String customerMobileNumber;
	private float orderTotalAmount;
	private float orderDeliveryFee;
	private float packagingFee;
	private String orderStyle;
	private String orderItemNames;
	private String restaurantOrderStatus;
	private String restaurantOrderTime;
	private String paymentMode;
	private AddressListBean deliveryAddress;
	private boolean podAvailable;
	private boolean convenienceFee;
	private float deliveryMinAmount;
	private float deliveryFee;
	private int deliveryRadius;
	
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
	public String getRestaurantOrderStatus() {
		return restaurantOrderStatus;
	}
	public void setRestaurantOrderStatus(String restaurantOrderStatus) {
		this.restaurantOrderStatus = restaurantOrderStatus;
	}
	public String getRestaurantOrderTime() {
		return restaurantOrderTime;
	}
	public void setRestaurantOrderTime(String restaurantOrderTime) {
		this.restaurantOrderTime = restaurantOrderTime;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public boolean isPodAvailable() {
		return podAvailable;
	}
	public void setPodAvailable(boolean podAvailable) {
		this.podAvailable = podAvailable;
	}
	public boolean isConvenienceFee() {
		return convenienceFee;
	}
	public void setConvenienceFee(boolean convenienceFee) {
		this.convenienceFee = convenienceFee;
	}
	public float getDeliveryMinAmount() {
		return deliveryMinAmount;
	}
	public void setDeliveryMinAmount(float deliveryMinAmount) {
		this.deliveryMinAmount = deliveryMinAmount;
	}
	public float getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(float deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public int getDeliveryRadius() {
		return deliveryRadius;
	}
	public void setDeliveryRadius(int deliveryRadius) {
		this.deliveryRadius = deliveryRadius;
	}
	public float getOrderDeliveryFee() {
		return orderDeliveryFee;
	}
	public void setOrderDeliveryFee(float orderDeliveryFee) {
		this.orderDeliveryFee = orderDeliveryFee;
	}
	public float getPackagingFee() {
		return packagingFee;
	}
	public void setPackagingFee(float packagingFee) {
		this.packagingFee = packagingFee;
	}
	public String getOrderItemNames() {
		return orderItemNames;
	}
	public void setOrderItemNames(String orderItemNames) {
		this.orderItemNames = orderItemNames;
	}
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
}
