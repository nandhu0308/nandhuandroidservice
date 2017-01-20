package com.limitless.services.engage.order.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cart_details", catalog="llcdb")
public class CartDetails {

	@Id
	@GeneratedValue
	@Column(name="cart_details_id")
	private Integer cartDetailsId;
	
	@Column(name="product_id")
	private Integer productId;
	
	@Column(name="product_quantity_id")
	private Integer productQuantityId;
	
	
	public int getCartDetailsId(){
		return cartDetailsId;
	}
	public void setCartDetailsId(int cartDetailsId){
	
	this.cartDetailsId = cartDetailsId;
	}
	
    public int getProductId(){
    	return productId;
    }
    public void setProductId(Integer productId){
    	this.productId = productId;
    }
    	
   public Integer getProductQuantityId(){
	   return productQuantityId;
   }
   public void setProductQuantityId(int productQuantityId){
	   this.productQuantityId = productQuantityId;
	
	
}


	
}
