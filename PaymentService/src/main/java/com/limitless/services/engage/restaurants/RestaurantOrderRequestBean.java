package com.limitless.services.engage.restaurants;

import java.util.List;

public class RestaurantOrderRequestBean {
	private int customerId;
	private int restaurantId;
	private String orderStyle;
	private int deliveryAddressId;
	private String paymentMode;
	private List<RestaurantOrderItemsBean> itemsList;
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getOrderStyle() {
		return orderStyle;
	}
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	public int getDeliveryAddressId() {
		return deliveryAddressId;
	}
	public void setDeliveryAddressId(int deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}
	public List<RestaurantOrderItemsBean> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<RestaurantOrderItemsBean> itemsList) {
		this.itemsList = itemsList;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
}
