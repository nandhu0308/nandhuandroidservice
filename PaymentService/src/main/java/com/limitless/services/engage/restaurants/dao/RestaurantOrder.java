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
	@Column(name="DELIVERY_FEE")
	private float deliveryFee;
	@Column(name="PACKAGING_FEE")
	private float packagingFee;
	@Column(name="ORDER_TYPE")
	private String orderType;
	@Column(name="ORDER_TIME", insertable = false, updatable = false)
	private Date orderTime;
	@Column(name="DELIVERY_ADDRESS_ID")
	private Integer deliveryAddressId;
	@Column(name="ORDER_STATUS")
	private String orderStatus;
	@Column(name="PAYMENT_MODE")
	private String paymentMode;
	
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
	public Integer getDeliveryAddressId() {
		return deliveryAddressId;
	}
	public void setDeliveryAddressId(Integer deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public float getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(float deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public float getPackagingFee() {
		return packagingFee;
	}
	public void setPackagingFee(float packagingFee) {
		this.packagingFee = packagingFee;
	}
}
