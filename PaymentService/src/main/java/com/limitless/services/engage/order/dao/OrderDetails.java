package com.limitless.services.engage.order.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order_details", catalog="llcdb")
public class OrderDetails {
	@Id
	@GeneratedValue
	@Column(name="OD_ID")
	private Integer orderDetailsId;
	@Column(name="ORDER_ID")
	private Integer orderId;
	@Column(name="PRODUCT_ID")
	private Integer productId;
	@Column(name="QUANTITY")
	private Integer quantity;
	@Column(name="UNIT_PRICE")
	private double unitPrice;
	@Column(name="PPM_ID")
	private Integer productPricesMapperId;
	
	public Integer getOrderDetailsId() {
		return orderDetailsId;
	}
	public void setOrderDetailsId(Integer orderDetailsId) {
		this.orderDetailsId = orderDetailsId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getProductPricesMapperId() {
		return productPricesMapperId;
	}
	public void setProductPricesMapperId(Integer productPricesMapperId) {
		this.productPricesMapperId = productPricesMapperId;
	}
}
