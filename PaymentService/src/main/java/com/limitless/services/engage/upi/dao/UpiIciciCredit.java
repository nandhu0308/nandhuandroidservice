package com.limitless.services.engage.upi.dao;

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
@Table(name="upi_icici_credit", catalog="llcdb")
public class UpiIciciCredit {
	@Id
	@GeneratedValue
	@Column(name="CREDIT_ID")
	private Integer creditId;
	@Column(name="ICICI_ORDER_ID")
	private Integer iciciOrderId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="CREDIT_AMOUNT")
	private double creditAmount;
	@Column(name="CREDIT_TEMP")
	private double creditTemp;
	@Column(name="DEBIT_AMOUNT")
	private double debitAmount;
	@Column(name="DEBIT_TEMP")
	private double debitTemp;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREDIT_CREATED_TIME", insertable = false, updatable = false)
	private Date creditCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREDIT_UPDATED_TIME")
	@Version
	private Date creditUpdatedTime;
	
	public Integer getCreditId() {
		return creditId;
	}
	public void setCreditId(Integer creditId) {
		this.creditId = creditId;
	}
	public Integer getIciciOrderId() {
		return iciciOrderId;
	}
	public void setIciciOrderId(Integer iciciOrderId) {
		this.iciciOrderId = iciciOrderId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public double getCreditTemp() {
		return creditTemp;
	}
	public void setCreditTemp(double creditTemp) {
		this.creditTemp = creditTemp;
	}
	public double getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}
	public double getDebitTemp() {
		return debitTemp;
	}
	public void setDebitTemp(double debitTemp) {
		this.debitTemp = debitTemp;
	}
	public Date getCreditCreatedTime() {
		return creditCreatedTime;
	}
	public void setCreditCreatedTime(Date creditCreatedTime) {
		this.creditCreatedTime = creditCreatedTime;
	}
	public Date getCreditUpdatedTime() {
		return creditUpdatedTime;
	}
	public void setCreditUpdatedTime(Date creditUpdatedTime) {
		this.creditUpdatedTime = creditUpdatedTime;
	}
}
