package com.limitless.services.engage.sellers;

import java.util.List;

public class CitrusSellerResponseBean {
	private String message;
	private List<CitrusSellersBean> sellersList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<CitrusSellersBean> getSellersList() {
		return sellersList;
	}
	public void setSellersList(List<CitrusSellersBean> sellersList) {
		this.sellersList = sellersList;
	}
}
