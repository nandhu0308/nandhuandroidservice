package com.limitless.services.engage;

import java.util.List;

public class CustomerNotifyListBean {
	private int sellerId;
	private List<CustomerNotifyBean> notifyList;

	public List<CustomerNotifyBean> getNotifyList() {
		return notifyList;
	}

	public void setNotifyList(List<CustomerNotifyBean> notifyList) {
		this.notifyList = notifyList;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
}
