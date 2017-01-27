package com.limitless.services.engage.sellers.product.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_prices_mapper")
public class ProductPricesMapper {
	@Id
	@GeneratedValue
	@Column(name="PPM_ID")
	private Integer ppmId;
	@Column(name="PRODUCT_ID")
	private Integer productId;
	@Column(name="PRODUCT_SIZE_NUMBER")
	private Integer productSizeNumber;
	@Column(name="PRODUCT_SIZE_TEXT")
	private String productSizeText;
	@Column(name="PRODUCT_COLOR")
	private String productColor;
	@Column(name="PRODUCT_PRICE")
	private float productPrice;
	@Column(name="DISCOUNT_RATE")
	private double discountRate;
	@Column(name="IS_DEFAULT")
	private Integer isDefault;
	
	public Integer getPpmId() {
		return ppmId;
	}
	public void setPpmId(Integer ppmId) {
		this.ppmId = ppmId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductColor() {
		return productColor;
	}
	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getProductSizeNumber() {
		return productSizeNumber;
	}
	public void setProductSizeNumber(Integer productSizeNumber) {
		this.productSizeNumber = productSizeNumber;
	}
	public String getProductSizeText() {
		return productSizeText;
	}
	public void setProductSizeText(String productSizeText) {
		this.productSizeText = productSizeText;
	}
}
