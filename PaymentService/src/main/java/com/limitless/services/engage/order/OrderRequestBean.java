package com.limitless.services.engage.order;

import java.util.List;

public class OrderRequestBean {
	private int customerId;
	private int sellerId;
	private List<OrderProductsBean> orderList;
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
	public List<OrderProductsBean> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderProductsBean> orderList) {
		this.orderList = orderList;
	}
}
