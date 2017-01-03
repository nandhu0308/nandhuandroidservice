package com.limitless.services.engage.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="seller_fee_rate", catalog="llcdb")
public class SellerFeeRates {
	@Id
	@GeneratedValue
	@Column(name="RATE_ID")
	private Integer rateId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="UPTO_#K")
	private double upto3k;
	@Column(name="GT_3K_LT_10K")
	private double gt3klt10k;
	@Column(name="GT_10K_LT_1LAC")
	private double gt10klt1lac;
	@Column(name="GT_1LAC_LT_2LAC")
	private double gt1laclt2lac;
	@Column(name="GT_2LAC")
	private double gt2lac;
	
	public Integer getRateId() {
		return rateId;
	}
	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
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
