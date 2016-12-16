package com.limitless.services.payment.PaymentService;

import java.util.List;

public class CustomerTxnHistoryBean {
	private String message;
	private List<TxnHistoryBean> historyBean;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<TxnHistoryBean> getHistoryBean() {
		return historyBean;
	}
	public void setHistoryBean(List<TxnHistoryBean> historyBean) {
		this.historyBean = historyBean;
	}
}
