package com.limitless.services.engage.order;

import java.util.List;

public class OrderSummaryResponseBean {
	private String message;
	private List<OrdersListBean> ordersList;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<OrdersListBean> getOrdersList() {
		return ordersList;
	}
	public void setOrdersList(List<OrdersListBean> ordersList) {
		this.ordersList = ordersList;
	}
}
