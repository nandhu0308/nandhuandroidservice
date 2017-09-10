package com.limitless.services.engage.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="promo_codes")
public class SellerPromoCode {
	@Id
	@GeneratedValue
	@Column(name="PROMO_CODE_ID")
	private Integer promoCodeId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="PROMO_CODE")
	private String promoCode;
	@Column(name="RATE")
	private float rate;
	@Column(name="RATE_TYPE")
	private String rateType;
	@Column(name="EXPIRY_DATE")
	private String expiryDate;
	@Column(name="IS_ACTIVE", nullable=false, columnDefinition="TINYINT(1)")
	private boolean isActive;
	@Column(name="PC_CREATED_DATE", insertable=false, updatable=false)
	private Date pcCreatedTime;
	@Column(name="PC_UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	private Date pcUpdatedTime;
	
	public Integer getPromoCodeId() {
		return promoCodeId;
	}
	public void setPromoCodeId(Integer promoCodeId) {
		this.promoCodeId = promoCodeId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getPcCreatedTime() {
		return pcCreatedTime;
	}
	public void setPcCreatedTime(Date pcCreatedTime) {
		this.pcCreatedTime = pcCreatedTime;
	}
	public Date getPcUpdatedTime() {
		return pcUpdatedTime;
	}
	public void setPcUpdatedTime(Date pcUpdatedTime) {
		this.pcUpdatedTime = pcUpdatedTime;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
}
