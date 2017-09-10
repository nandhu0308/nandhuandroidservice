package com.limitless.services.engage.order.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cart_details")
public class CartDetails {
	@Id
	@GeneratedValue
	@Column(name="CART_DETAILS_ID")
	private Integer cartDetailsId;
	@Column(name="CART_ID")
	private Integer cartId;
	@Column(name="PRODUCT_ID")
	private Integer productId;
	@Column(name="QUANTITY")
	private Integer quantity;
	@Column(name="IS_REMOVED")
	private int isRemoved;
	
	public Integer getCartDetailsId() {
		return cartDetailsId;
	}
	public void setCartDetailsId(Integer cartDetailsId) {
		this.cartDetailsId = cartDetailsId;
	}
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
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
	public int getIsRemoved() {
		return isRemoved;
	}
	public void setIsRemoved(int isRemoved) {
		this.isRemoved = isRemoved;
	}
}
