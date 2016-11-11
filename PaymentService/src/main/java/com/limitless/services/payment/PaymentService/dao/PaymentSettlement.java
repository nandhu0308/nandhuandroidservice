package com.limitless.services.payment.PaymentService.dao;

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
@Table(name="payment_settle", catalog = "llcdb")
public class PaymentSettlement implements java.io.Serializable {
	@Id
	@GeneratedValue
	private int psId;
	@Column(name="TXN_ID")
	private int txnId;
	@Column(name="SETTLEMENT_ID")
	private int settlementId;
	@Column(name="RELEASEFUND_REF_ID")
	private int releasefundRefId;
	@Column(name="SETTLEMENT_AMOUNT")
	private double settlementAmount;
	@Column(name="ERROR_ID_SETTLE")
	private String errorIdSettle;
	@Column(name="ERROR_DESCRIPTION_SETTLE")
	private String errorDescriptionSettle;
	@Column(name="ERROR_ID_RELEASE")
	private String errorIdRelease;
	@Column(name="ERROR_DESCRIPTION_RELEASE")
	private String errorDescriptionRelease;
	@Column(name="SETTLEMENT_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	private Date settlementTime;
	public int getPsId() {
		return psId;
	}
	public void setPsId(int psId) {
		this.psId = psId;
	}
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}
	public int getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(int settlementId) {
		this.settlementId = settlementId;
	}
	public int getReleasefundRefId() {
		return releasefundRefId;
	}
	public void setReleasefundRefId(int releasefundRefId) {
		this.releasefundRefId = releasefundRefId;
	}
	public Date getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}
	public String getErrorIdSettle() {
		return errorIdSettle;
	}
	public void setErrorIdSettle(String errorIdSettle) {
		this.errorIdSettle = errorIdSettle;
	}
	public String getErrorDescriptionSettle() {
		return errorDescriptionSettle;
	}
	public void setErrorDescriptionSettle(String errorDescriptionSettle) {
		this.errorDescriptionSettle = errorDescriptionSettle;
	}
	public String getErrorIdRelease() {
		return errorIdRelease;
	}
	public void setErrorIdRelease(String errorIdRelease) {
		this.errorIdRelease = errorIdRelease;
	}
	public String getErrorDescriptionRelease() {
		return errorDescriptionRelease;
	}
	public void setErrorDescriptionRelease(String errorDescriptionRelease) {
		this.errorDescriptionRelease = errorDescriptionRelease;
	}
	public double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
}
