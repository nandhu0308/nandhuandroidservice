package com.limitless.services.payment.PaymentService;

import java.util.List;

public class SellerTxnHistoryBean {
	private String message;
	private List<TxnHistoryBean> hitoryBean;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<TxnHistoryBean> getHitoryBean() {
		return hitoryBean;
	}
	public void setHitoryBean(List<TxnHistoryBean> hitoryBean) {
		this.hitoryBean = hitoryBean;
	}
	
}
