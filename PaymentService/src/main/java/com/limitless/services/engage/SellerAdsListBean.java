package com.limitless.services.engage;

import java.util.List;

public class SellerAdsListBean {
	private String message;
	private List<SellerAdBean> adBean;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<SellerAdBean> getAdBean() {
		return adBean;
	}
	public void setAdBean(List<SellerAdBean> adBean) {
		this.adBean = adBean;
	}
}
