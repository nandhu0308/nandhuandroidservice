package com.limitless.services.payment.PaymentService;

import java.util.List;

public class GeneralSellerTxnHistoryBean {
	private String message;
	private List<TxnHistoryBean> historyBean;
	private List<TxnDayWiseBean> dayHistory;
	private List<TxnMonthWiseBean> monthHistory;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<TxnDayWiseBean> getDayHistory() {
		return dayHistory;
	}
	public void setDayHistory(List<TxnDayWiseBean> dayHistory) {
		this.dayHistory = dayHistory;
	}
	public List<TxnMonthWiseBean> getMonthHistory() {
		return monthHistory;
	}
	public void setMonthHistory(List<TxnMonthWiseBean> monthHistory) {
		this.monthHistory = monthHistory;
	}
	public List<TxnHistoryBean> getHistoryBean() {
		return historyBean;
	}
	public void setHistoryBean(List<TxnHistoryBean> historyBean) {
		this.historyBean = historyBean;
	}
}
