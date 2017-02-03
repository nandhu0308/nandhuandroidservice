package com.limitless.services.engage.restaurants.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="restaurant_orders",catalog="llcdb")
public class RestaurantOrder {
	@Id
	@GeneratedValue
	@Column(name="ORDER_ID")
	private Integer orderId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="RESTAURANT_ID")
	private Integer restaurantId;
	@Column(name="TOTAL_AMOUNT")
	private double totalAmount;
	@Column(name="ORDER_TYPE")
	private String orderType;
	@Column(name="ORDER_TIME", insertable = false, updatable = false)
	private Date orderTime;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
}
