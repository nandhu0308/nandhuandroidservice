package com.limitless.services.engage.order.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cart", catalog="llcdb")
public class Cart {
	

	@Id
	@GeneratedValue
	@Column(name="cart_id")
	private Integer cartId;
	
	@Column(name="customer_id")
	private Integer customerId;
	
	@Column(name="seller_id")
	private Integer sellerId;
	
	
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) 
	{
		this.cartId = cartId;
	}
	
	public int getCustomerId()
	{
		return customerId;
	}
	public void setCustomerId(int customerId)
	{
		this.customerId = customerId;
	}
	
	public int getSellerId(){
		return sellerId;
	}
	public void setSellerId(int sellerId){
		this.sellerId = sellerId;
	}
	
	
	

}
