package com.limitless.services.engage;

public class SellerFeeRateRequestBean {
	private int sellerId;
	private double upto3k;
	private double gt3klt10k;
	private double gt10klt1lac;
	private double gt1laclt2lac;
	private double gt2lac;
	
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public double getUpto3k() {
		return upto3k;
	}
	public void setUpto3k(double upto3k) {
		this.upto3k = upto3k;
	}
	public double getGt3klt10k() {
		return gt3klt10k;
	}
	public void setGt3klt10k(double gt3klt10k) {
		this.gt3klt10k = gt3klt10k;
	}
	public double getGt10klt1lac() {
		return gt10klt1lac;
	}
	public void setGt10klt1lac(double gt10klt1lac) {
		this.gt10klt1lac = gt10klt1lac;
	}
	public double getGt1laclt2lac() {
		return gt1laclt2lac;
	}
	public void setGt1laclt2lac(double gt1laclt2lac) {
		this.gt1laclt2lac = gt1laclt2lac;
	}
	public double getGt2lac() {
		return gt2lac;
	}
	public void setGt2lac(double gt2lac) {
		this.gt2lac = gt2lac;
	}
}
