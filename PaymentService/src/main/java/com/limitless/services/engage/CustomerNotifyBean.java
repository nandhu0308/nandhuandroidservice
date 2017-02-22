package com.limitless.services.engage;

import java.util.Date;

public class CustomerNotifyBean {
	private int notifyId;
	private int sellerId;
	private String sellerName;
	private String sellerMobileNumber;
	private String title;
	private String body;
	private String imageUrl;
	private String status;
	private String postType;
	private Date notifyCreatedTime;
	public int getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
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
	public String getSellerMobileNumber() {
		return sellerMobileNumber;
	}
	public void setSellerMobileNumber(String sellerMobileNumber) {
		this.sellerMobileNumber = sellerMobileNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
	public Date getNotifyCreatedTime() {
		return notifyCreatedTime;
	}
	public void setNotifyCreatedTime(Date notifyCreatedTime) {
		this.notifyCreatedTime = notifyCreatedTime;
	}
}
