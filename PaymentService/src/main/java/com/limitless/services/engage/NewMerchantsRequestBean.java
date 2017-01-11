package com.limitless.services.engage;

import java.util.List;

public class NewMerchantsRequestBean {
	private String message;
	private int requestCount;
	private List<MerchantRequestListBean> requestList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}
	public List<MerchantRequestListBean> getRequestList() {
		return requestList;
	}
	public void setRequestList(List<MerchantRequestListBean> requestList) {
		this.requestList = requestList;
	}
}
