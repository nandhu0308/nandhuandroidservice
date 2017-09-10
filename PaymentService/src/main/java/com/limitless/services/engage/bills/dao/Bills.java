package com.limitless.services.engage.bills.dao;

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
@Table(name="bills")
public class Bills {
	@Id
	@GeneratedValue
	@Column(name="BILL_ID")
	private Integer billId;
	@Column(name="CLIENT_INVOICE_ID")
	private String clientInvoiceId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="BILL_DATE")
	private String billDate;
	@Column(name="BILL_DUE_DATE")
	private String billDueDate;
	@Column(name="BILL_PAID_DATE")
	private String billPaidDate;
	@Column(name="BILL_AMOUNT")
	private float billAmount;
	@Column(name="BILL_ADJUSTMENT")
	private float billAdjustment;
	@Column(name="BILL_STATUS")
	private String billStatus;
	@Column(name="BILL_CREATED_DATE", insertable=false, updatable=false)
	private Date billCreatedDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name="BILL_UPDATED_DATE")
	private Date billUpdatedTime;
	
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
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
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getBillDueDate() {
		return billDueDate;
	}
	public void setBillDueDate(String billDueDate) {
		this.billDueDate = billDueDate;
	}
	public String getBillPaidDate() {
		return billPaidDate;
	}
	public void setBillPaidDate(String billPaidDate) {
		this.billPaidDate = billPaidDate;
	}
	public float getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}
	public float getBillAdjustment() {
		return billAdjustment;
	}
	public void setBillAdjustment(float billAdjustment) {
		this.billAdjustment = billAdjustment;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public Date getBillCreatedDate() {
		return billCreatedDate;
	}
	public void setBillCreatedDate(Date billCreatedDate) {
		this.billCreatedDate = billCreatedDate;
	}
	public Date getBillUpdatedTime() {
		return billUpdatedTime;
	}
	public void setBillUpdatedTime(Date billUpdatedTime) {
		this.billUpdatedTime = billUpdatedTime;
	}
	public String getClientInvoiceId() {
		return clientInvoiceId;
	}
	public void setClientInvoiceId(String clientInvoiceId) {
		this.clientInvoiceId = clientInvoiceId;
	}
}
