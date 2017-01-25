package com.limitless.services.engage;

import java.util.List;

import com.limitless.services.engage.sellers.ProductBean;
import com.limitless.services.engage.sellers.product.dao.Product;

public class SellerLoginResponseBean {
	
	private String sellerName;
	private int sellerId;
	private int citrusSellerId;
	private String mobileNumber;
	private String message;
	private int status;
	private String sellerAddress;
	private String sellerCity;
	private String sellerType;
	private String sellerRole;
	private List<ProductBean> products;
	private String brandingUrl;
	
	
	public List<ProductBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductBean> products) {
		this.products = products;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSellerAddress() {
		return sellerAddress;
	}
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}
	public String getSellerCity() {
		return sellerCity;
	}
	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}
	public int getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(int citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getSellerRole() {
		return sellerRole;
	}
	public void setSellerRole(String sellerRole) {
		this.sellerRole = sellerRole;
	}
	public String getBrandingUrl() {
		return brandingUrl;
	}
	public void setBrandingUrl(String brandingUrl) {
		this.brandingUrl = brandingUrl;
	}
	
}
