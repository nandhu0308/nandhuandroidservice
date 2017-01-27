package com.limitless.services.engage.sellers;

public class ProductModelsBean {
	private int productId;
	private String sizeText;
	private int sizeNumber;
	private String color;
	private float productPrice;
	private double discountRate;
	private float discountedPrice;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getSizeText() {
		return sizeText;
	}
	public void setSizeText(String sizeText) {
		this.sizeText = sizeText;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
	public float getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(float discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public int getSizeNumber() {
		return sizeNumber;
	}
	public void setSizeNumber(int sizeNumber) {
		this.sizeNumber = sizeNumber;
	}
}
