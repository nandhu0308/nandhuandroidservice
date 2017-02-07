package com.limitless.services.engage.sellers.product.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product", catalog="llcdb")
public class Product {
	
	@Id
	@GeneratedValue
	@Column(name="PRODUCT_ID")
	private Integer productId;
	@Column(name="PRODUCT_NAME")
	private String productName;
	@Column(name="PRODUCT_PRICE")
	private float productPrice;	
	@Column(name="PRODUCT_IMAGE")
	private String product_image;
	@Column(name="PRODUCT_DESCRIPTION")
	private String productDescription;
	@Column(name="DISCOUNT_RATE")
	private double discountRate;
	@Column(name="PRODUCT_IN_STOCK")
	private Integer productInStock;
	@Column(name="PARENT_PRODUCT_ID")
	private Integer parent_productid;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public Integer getParent_productid() {
		return parent_productid;
	}
	public void setParent_productid(Integer parent_productid) {
		this.parent_productid = parent_productid;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public Integer getProductInStock() {
		return productInStock;
	}
	public void setProductInStock(Integer productInStock) {
		this.productInStock = productInStock;
	}
}
