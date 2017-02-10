package com.limitless.services.engage.restaurants;

import java.util.List;

public class RestaurantOrderDetailsResponseBean {
	private int orderId;
	private List<RestaurantOrderItemsListBean> itemsList;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public List<RestaurantOrderItemsListBean> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<RestaurantOrderItemsListBean> itemsList) {
		this.itemsList = itemsList;
	}
}
