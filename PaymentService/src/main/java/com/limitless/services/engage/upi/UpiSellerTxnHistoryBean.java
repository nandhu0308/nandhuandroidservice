package com.limitless.services.engage.upi;

import java.util.List;

public class UpiSellerTxnHistoryBean {
	private String message;
	private List<UpiTxnHistoryBean> txnList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<UpiTxnHistoryBean> getTxnList() {
		return txnList;
	}
	public void setTxnList(List<UpiTxnHistoryBean> txnList) {
		this.txnList = txnList;
	}
}
