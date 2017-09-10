package com.limitless.services.engage.order.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cart")
public class Cart {
	@Id
	@GeneratedValue
	@Column(name="CART_ID")
	private Integer cartId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="IS_DELETED")
	private int isDeleted;
	@Column(name="IS_CHECKED_OUT")
	private Integer isCheckedOut;
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
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
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Integer getIsCheckedOut() {
		return isCheckedOut;
	}
	public void setIsCheckedOut(Integer isCheckedOut) {
		this.isCheckedOut = isCheckedOut;
	}
}
