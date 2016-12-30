package com.limitless.services.payment.PaymentService.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="master_txn")
public class MasterTxn {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	@Column(name="CITRUS_TXN_ID")
	private int citrusTxnId;
	@Column(name="ICICI_UORDER_ID")
	private int iciciUorderId;
	@Column(name="SELLER_ID")
	private int sellerId;
	@Column(name="CUSTOMER_ID")
	private int customerId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TXN_CREATED_TIME", insertable = false, updatable = false)
	private Date txnCreatedTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCitrusTxnId() {
		return citrusTxnId;
	}
	public void setCitrusTxnId(int citrusTxnId) {
		this.citrusTxnId = citrusTxnId;
	}
	public int getIciciUorderId() {
		return iciciUorderId;
	}
	public void setIciciUorderId(int iciciUorderId) {
		this.iciciUorderId = iciciUorderId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getTxnCreatedTime() {
		return txnCreatedTime;
	}
	public void setTxnCreatedTime(Date txnCreatedTime) {
		this.txnCreatedTime = txnCreatedTime;
	}
}
