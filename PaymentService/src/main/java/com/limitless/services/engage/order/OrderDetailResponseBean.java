package com.limitless.services.engage.order;

import java.util.List;

public class OrderDetailResponseBean {
	private int orderId;
	private List<OrderProductsListBean> productsBean;
	private String message;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<OrderProductsListBean> getProductsBean() {
		return productsBean;
	}
	public void setProductsBean(List<OrderProductsListBean> productsBean) {
		this.productsBean = productsBean;
	}
}
