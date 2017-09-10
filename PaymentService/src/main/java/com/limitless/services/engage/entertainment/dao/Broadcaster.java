package com.limitless.services.engage.entertainment.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.limitless.services.engage.dao.EngageSeller;

@Entity
@Table(name = "broadcasters")
@SqlResultSetMapping(name = "broadcasters", entities = @EntityResult(entityClass = Broadcaster.class))
public class Broadcaster {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer broadcasterId;
	@Column(name = "BROADCASTER_NAME")
	private String broadcasterName;
	@Column(name = "BROADCASTER_CHANNEL_NAME")
	private String broadcasterChannelName;
	@Column(name = "BROADCASTER_EMAIL")
	private String broadcasterEmail;
	@Column(name = "BROADCASTER_DESCRIPTION")
	private String broadcasterDescription;
	@Column(name = "BROADCASTER_WEBSITE")
	private String broadcasterWebsite;
	@Column(name = "BROADCASTER_IMAGE")
	private String broadcasterImage;
	@Column(name = "BROADCASTER_TAGS")
	private String broadcasterTags;
	@Column(name = "BROADCASTER_TOTAL_VIDEOS")
	private Integer broadcasterTotalVideos;

	@Column(name = "BROADCASTER_LOC_LATITUDE")
	private double broadcasterLocationLatitude;
	@Column(name = "BROADCASTER_LOC_LONGITUDE")
	private double broadcasterLocationLongitude;
	@Column(name = "BROADCASTER_KYC_DOC_TYPE")
	private String broadcasterKycDocType;
	@Column(name = "BROADCASTER_KYC_DOC_VALUE")
	private String broadcasterKycDocValue;

	@Column(name = "BROADCASTER_CREATED_TIME", insertable = false, updatable = false)
	private Date broadcasterCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name = "BROADCASTER_UPDATED_TIME")
	private Date broadcasterUPdatedTime;

	@Column(name = "SELLER_ID")
	private Integer sellerId;

	@Column(name = "CATEGORY_ID")
	private Integer categoryId;

	@Column(name = "RANK")
	private Integer rank;

	@Column(name = "IS_ACTIVE")
	private boolean isActive;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getBroadcasterId() {
		return broadcasterId;
	}

	public void setBroadcasterId(Integer broadcasterId) {
		this.broadcasterId = broadcasterId;
	}

	public String getBroadcasterName() {
		return broadcasterName;
	}

	public void setBroadcasterName(String broadcasterName) {
		this.broadcasterName = broadcasterName;
	}

	public String getBroadcasterEmail() {
		return broadcasterEmail;
	}

	public void setBroadcasterEmail(String broadcasterEmail) {
		this.broadcasterEmail = broadcasterEmail;
	}

	public String getBroadcasterDescription() {
		return broadcasterDescription;
	}

	public void setBroadcasterDescription(String broadcasterDescription) {
		this.broadcasterDescription = broadcasterDescription;
	}

	public String getBroadcasterWebsite() {
		return broadcasterWebsite;
	}

	public void setBroadcasterWebsite(String broadcasterWebsite) {
		this.broadcasterWebsite = broadcasterWebsite;
	}

	public String getBroadcasterImage() {
		return broadcasterImage;
	}

	public void setBroadcasterImage(String broadcasterImage) {
		this.broadcasterImage = broadcasterImage;
	}

	public String getBroadcasterTags() {
		return broadcasterTags;
	}

	public void setBroadcasterTags(String broadcasterTags) {
		this.broadcasterTags = broadcasterTags;
	}

	public Integer getBroadcasterTotalVideos() {
		return broadcasterTotalVideos;
	}

	public void setBroadcasterTotalVideos(Integer broadcasterTotalVideos) {
		this.broadcasterTotalVideos = broadcasterTotalVideos;
	}

	public Date getBroadcasterCreatedTime() {
		return broadcasterCreatedTime;
	}

	public void setBroadcasterCreatedTime(Date broadcasterCreatedTime) {
		this.broadcasterCreatedTime = broadcasterCreatedTime;
	}

	public Date getBroadcasterUPdatedTime() {
		return broadcasterUPdatedTime;
	}

	public void setBroadcasterUPdatedTime(Date broadcasterUPdatedTime) {
		this.broadcasterUPdatedTime = broadcasterUPdatedTime;
	}

	public String getBroadcasterChannelName() {
		return broadcasterChannelName;
	}

	public void setBroadcasterChannelName(String broadcasterChannelName) {
		this.broadcasterChannelName = broadcasterChannelName;
	}

	public double getBroadcasterLocationLatitude() {
		return broadcasterLocationLatitude;
	}

	public void setBroadcasterLocationLatitude(double broadcasterLocationLatitude) {
		this.broadcasterLocationLatitude = broadcasterLocationLatitude;
	}

	public double getBroadcasterLocationLongitude() {
		return broadcasterLocationLongitude;
	}

	public void setBroadcasterLocationLongitude(double broadcasterLocationLongitude) {
		this.broadcasterLocationLongitude = broadcasterLocationLongitude;
	}

	public String getBroadcasterKycDocType() {
		return broadcasterKycDocType;
	}

	public void setBroadcasterKycDocType(String broadcasterKycDocType) {
		this.broadcasterKycDocType = broadcasterKycDocType;
	}

	public String getBroadcasterKycDocValue() {
		return broadcasterKycDocValue;
	}

	public void setBroadcasterKycDocValue(String broadcasterKycDocValue) {
		this.broadcasterKycDocValue = broadcasterKycDocValue;
	}
	
	
}
