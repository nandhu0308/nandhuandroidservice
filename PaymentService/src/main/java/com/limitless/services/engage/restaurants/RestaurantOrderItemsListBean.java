package com.limitless.services.engage.restaurants;

public class RestaurantOrderItemsListBean {
	private int itemId;
	private String itemName;
	private int itemQuantity;
	private float itemPrice;
	private float itemTotalPrice;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public float getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}
	public float getItemTotalPrice() {
		return itemTotalPrice;
	}
	public void setItemTotalPrice(float itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}
}
