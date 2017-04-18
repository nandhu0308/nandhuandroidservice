package com.limitless.services.engage;

import java.util.List;

public class SellerBrandPromotionListBean {
	private String message;
	private List<SellerBrandPromotionBean> promotionList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<SellerBrandPromotionBean> getPromotionList() {
		return promotionList;
	}
	public void setPromotionList(List<SellerBrandPromotionBean> promotionList) {
		this.promotionList = promotionList;
	}
}
