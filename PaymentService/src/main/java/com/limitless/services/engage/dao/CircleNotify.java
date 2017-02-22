package com.limitless.services.engage.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="circle_notify", catalog="llcdb")
public class CircleNotify {
	@Id
	@GeneratedValue
	@Column(name="CN_ID")
	private Integer circleNotifyId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="BODY")
	private String body;
	@Column(name="TITLE")
	private String title;
	@Column(name="IMAGE_URL")
	private String imageUrl;
	@Column(name="SELLER_MOBILE")
	private String sellerMobile;
	@Column(name="STATUS")
	private String status;
	@Column(name="POST_TYPE")
	private String postType;
	@Column(name="NOTIFY_CREATEDTIME", insertable = false, updatable = false)
	private Date notifyCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="NOTIFY_UPDATEDTIME")
	@Version
	private Date notifyUpdatedTime;
	public Integer getCircleNotifyId() {
		return circleNotifyId;
	}
	public void setCircleNotifyId(Integer circleNotifyId) {
		this.circleNotifyId = circleNotifyId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellerMobile() {
		return sellerMobile;
	}
	public void setSellerMobile(String sellerMobile) {
		this.sellerMobile = sellerMobile;
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
	public Date getNotifyUpdatedTime() {
		return notifyUpdatedTime;
	}
	public void setNotifyUpdatedTime(Date notifyUpdatedTime) {
		this.notifyUpdatedTime = notifyUpdatedTime;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
