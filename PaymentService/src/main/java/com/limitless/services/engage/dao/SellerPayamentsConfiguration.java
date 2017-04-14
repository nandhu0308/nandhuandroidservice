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
@Table(name="seller_payment_configs", catalog="llcdb")
public class SellerPayamentsConfiguration {
	@Id
	@GeneratedValue
	@Column(name="SPC_ID")
	private Integer spcId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="CITRUS_SELLER_ID")
	private Integer citrusSellerId;
	@Column(name="PAY_ON_DELIVERY")
	private Integer payOnDelivery;
	@Column(name="DELIVERY_MIN_AMT")
	private float devliveryMInAmt;
	@Column(name="DELIVERY_FEE")
	private Float deliveryFee;
	@Column(name="DELIVERY_RADIUS")
	private Integer deliveryRadius;
	@Column(name="SPLIT_PERCENT")
	private Float splitPercent;
	@Column(name="CONVENIENCE_FEE")
	private Integer convenienceFee;
	@Column(name="SPC_CREATED_TIME", insertable = false, updatable = false)
	private Date spcCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name="SPC_UPDATED_TIME")
	private Date spcUpdatedTime;
	@Column(name="CONVENIENCE_FEE_AMOUNT")
	private float convenienceFeeAmount;
	@Column(name="CONVENIENCE_FEE_RATE")
	private float convenienceFeeRate;
	@Column(name="PAYMENT_ALERT")
	private String paymentAlert;
	
	public Integer getSpcId() {
		return spcId;
	}
	public void setSpcId(Integer spcId) {
		this.spcId = spcId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getCitrusSellerId() {
		return citrusSellerId;
	}
	public void setCitrusSellerId(Integer citrusSellerId) {
		this.citrusSellerId = citrusSellerId;
	}
	public Integer getPayOnDelivery() {
		return payOnDelivery;
	}
	public void setPayOnDelivery(Integer payOnDelivery) {
		this.payOnDelivery = payOnDelivery;
	}
	public float getDevliveryMInAmt() {
		return devliveryMInAmt;
	}
	public void setDevliveryMInAmt(float devliveryMInAmt) {
		this.devliveryMInAmt = devliveryMInAmt;
	}
	public Float getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Float deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public Integer getDeliveryRadius() {
		return deliveryRadius;
	}
	public void setDeliveryRadius(Integer deliveryRadius) {
		this.deliveryRadius = deliveryRadius;
	}
	public Float getSplitPercent() {
		return splitPercent;
	}
	public void setSplitPercent(Float splitPercent) {
		this.splitPercent = splitPercent;
	}
	public Integer getConvenienceFee() {
		return convenienceFee;
	}
	public void setConvenienceFee(Integer convenienceFee) {
		this.convenienceFee = convenienceFee;
	}
	public Date getSpcCreatedTime() {
		return spcCreatedTime;
	}
	public void setSpcCreatedTime(Date spcCreatedTime) {
		this.spcCreatedTime = spcCreatedTime;
	}
	public Date getSpcUpdatedTime() {
		return spcUpdatedTime;
	}
	public void setSpcUpdatedTime(Date spcUpdatedTime) {
		this.spcUpdatedTime = spcUpdatedTime;
	}
	public float getConvenienceFeeAmount() {
		return convenienceFeeAmount;
	}
	public void setConvenienceFeeAmount(float convenienceFeeAmount) {
		this.convenienceFeeAmount = convenienceFeeAmount;
	}
	public float getConvenienceFeeRate() {
		return convenienceFeeRate;
	}
	public void setConvenienceFeeRate(float convenienceFeeRate) {
		this.convenienceFeeRate = convenienceFeeRate;
	}
	public String getPaymentAlert() {
		return paymentAlert;
	}
	public void setPaymentAlert(String paymentAlert) {
		this.paymentAlert = paymentAlert;
	}
}
