package com.limitless.services.engage.order;

import java.util.List;

public class CartListBean {
	private int customerId;
	private String customerName;
	private String message;
	private List<CartBean> cartBean;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<CartBean> getCartBean() {
		return cartBean;
	}
	public void setCartBean(List<CartBean> cartBean) {
		this.cartBean = cartBean;
	}
}
