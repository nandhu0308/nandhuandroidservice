package com.limitless.services.engage.restaurants;

import java.util.List;

public class RestaurantOrderBean {
	private int customerId;
	private int restaurantId;
	private List<RestaurantOrderListBean> orderList;
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
	public List<RestaurantOrderListBean> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<RestaurantOrderListBean> orderList) {
		this.orderList = orderList;
	}
}
