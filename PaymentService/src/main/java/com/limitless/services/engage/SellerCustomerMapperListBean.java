package com.limitless.services.engage;

import java.util.List;

public class SellerCustomerMapperListBean {
	private String message;
	private List<SellerCustomerMapperBean> mapperList;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<SellerCustomerMapperBean> getMapperList() {
		return mapperList;
	}
	public void setMapperList(List<SellerCustomerMapperBean> mapperList) {
		this.mapperList = mapperList;
	}
}
