package com.limitless.services.engage.order;

import java.util.List;

public class CartRequestBean {
	private int customerId;
	private int sellerId;
	private List<CartItemsListBean> itemList;
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
	public List<CartItemsListBean> getItemList() {
		return itemList;
	}
	public void setItemList(List<CartItemsListBean> itemList) {
		this.itemList = itemList;
	}
}
