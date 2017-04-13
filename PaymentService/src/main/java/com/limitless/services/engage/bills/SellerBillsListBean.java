package com.limitless.services.engage.bills;

import java.util.List;

public class SellerBillsListBean {
	private String message;
	private int sellerId;
	private String sellerName;
	private String sellerShopName;
	private String sellerMobile;
	private List<BillBean> billList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerShopName() {
		return sellerShopName;
	}
	public void setSellerShopName(String sellerShopName) {
		this.sellerShopName = sellerShopName;
	}
	public String getSellerMobile() {
		return sellerMobile;
	}
	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
	}
	public List<BillBean> getBillList() {
		return billList;
	}
	public void setBillList(List<BillBean> billList) {
		this.billList = billList;
	}
}
