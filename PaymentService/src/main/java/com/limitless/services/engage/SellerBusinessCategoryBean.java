package com.limitless.services.engage;

import java.util.List;

public class SellerBusinessCategoryBean {
	private String sellerBusinessCategory;
	private List<SellerMinBean> sellerList;
	public String getSellerBusinessCategory() {
		return sellerBusinessCategory;
	}
	public void setSellerBusinessCategory(String sellerBusinessCategory) {
		this.sellerBusinessCategory = sellerBusinessCategory;
	}
	public List<SellerMinBean> getSellerList() {
		return sellerList;
	}
	public void setSellerList(List<SellerMinBean> sellerList) {
		this.sellerList = sellerList;
	}
}
