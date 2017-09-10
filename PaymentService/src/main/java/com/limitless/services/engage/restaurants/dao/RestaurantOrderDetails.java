package com.limitless.services.engage.restaurants.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant_order_details")
public class RestaurantOrderDetails {
	@Id
	@GeneratedValue
	@Column(name="ROD_ID")
	private Integer rodId;
	@Column(name="ORDER_ID")
	private Integer orderId;
	@Column(name="ITEM_ID")
	private Integer itemId;
	@Column(name="ITEM_PRICE")
	private float itemPrice;
	@Column(name="ITEM_QUANTITY")
	private Integer quantity;
	@Column(name="TOTAL_PRICE")
	private float totalPrice;
	
	public Integer getRodId() {
		return rodId;
	}
	public void setRodId(Integer rodId) {
		this.rodId = rodId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public float getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
