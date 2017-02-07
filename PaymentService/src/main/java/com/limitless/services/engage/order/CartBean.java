package com.limitless.services.engage.order;

import java.util.List;

public class CartBean {
	private int cartId;
	private int customerId;
	private String customerName;
	private String customerMobileNumber;
	private int sellerId;
	private String sellerShopName;
	private String sellerMobileNumber;
	private List<CartItemsListBean> cartItemsList;
	private String message;
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerShopName() {
		return sellerShopName;
	}
	public void setSellerShopName(String sellerShopName) {
		this.sellerShopName = sellerShopName;
	}
	public String getSellerMobileNumber() {
		return sellerMobileNumber;
	}
	public void setSellerMobileNumber(String sellerMobileNumber) {
		this.sellerMobileNumber = sellerMobileNumber;
	}
	public List<CartItemsListBean> getCartItemsList() {
		return cartItemsList;
	}
	public void setCartItemsList(List<CartItemsListBean> cartItemsList) {
		this.cartItemsList = cartItemsList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
