package com.limitless.services.payment.PaymentService;

import java.util.Date;

public class TxnHistoryBean {
	
	private int txnId;
	private int customerId;
	private String customerName;
	private int sellerId;
	private String sellerName;
	private double txtAmount;
	private double creditAmount;
	private int citrusMpTxnId;
	private int splitId;
	private String txtStatus;
	private String txnTime;
	private PaymentsSettlementResponseBean settlement;
	
	public TxnHistoryBean(){
		
	}

	public double getCreditAmount() {
		return creditAmount;
	}


	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}


	public int getTxnId() {
		return txnId;
	}

	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public double getTxtAmount() {
		return txtAmount;
	}

	public void setTxtAmount(double txtAmount) {
		this.txtAmount = txtAmount;
	}

	public int getCitrusMpTxnId() {
		return citrusMpTxnId;
	}

	public void setCitrusMpTxnId(int citrusMpTxnId) {
		this.citrusMpTxnId = citrusMpTxnId;
	}

	public int getSplitId() {
		return splitId;
	}

	public void setSplitId(int splitId) {
		this.splitId = splitId;
	}

	public String getTxtStatus() {
		return txtStatus;
	}

	public void setTxtStatus(String txtStatus) {
		this.txtStatus = txtStatus;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public PaymentsSettlementResponseBean getSettlement() {
		return settlement;
	}

	public void setSettlement(PaymentsSettlementResponseBean settlement) {
		this.settlement = settlement;
	}

}
