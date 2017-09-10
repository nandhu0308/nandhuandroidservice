package com.limitless.services.engage.order.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="orders")
public class Orders {
	@Id
	@GeneratedValue
	@Column(name="ORDER_ID")
	private Integer orderId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="TOTAL_AMOUNT")
	private double totalAmount;
	@Column(name="ORDER_STATUS")
	private String orderStatus;
	@Column(name="DELIVERY_ADDRESS")
	private Integer deliveryAddress;
	@Column(name="ORDER_CREATED_TIME", insertable = false, updatable = false)
	private Date orderCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name="ORDER_UPDATED_TIME")
	private Date orderUpdatedTime;
	@Column(name="PAYMENT_MODE")
	private String paymentMode;
	@Column(name="DELIVERY_FEE")
	private float deliveryFee;
	@Column(name="PACKAGING_FEE")
	private float packagingFee;
	
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
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getOrderCreatedTime() {
		return orderCreatedTime;
	}
	public void setOrderCreatedTime(Date orderCreatedTime) {
		this.orderCreatedTime = orderCreatedTime;
	}
	public Date getOrderUpdatedTime() {
		return orderUpdatedTime;
	}
	public void setOrderUpdatedTime(Date orderUpdatedTime) {
		this.orderUpdatedTime = orderUpdatedTime;
	}
	public Integer getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(Integer deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getPaymentMode() {
		if(paymentMode == null)
			return "";
		else
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
