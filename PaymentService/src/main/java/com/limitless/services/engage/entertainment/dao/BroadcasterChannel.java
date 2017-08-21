package com.limitless.services.engage.entertainment.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name = "broadcaster_channel", catalog = "llcdb")
public class BroadcasterChannel {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer channelId;
	@Column(name = "APPLICATION_ID")
	private Integer applicationId;
	@Column(name = "CHANNEL_NAME")
	private String channelName;
	@Column(name = "CHANNEL_DESCRIPTION")
	private String channelDescription;
	@Column(name = "IMAGE_FILE_NAME")
	private String channelThumbnail;
	
	@Column(name = "BROADCASTER_ID")
	private int broadcasterId;
	@Column(name = "CREATED_ON", insertable = false, updatable = false)
	private Date createdTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name = "UPDATED_ON")
	private Date updatedTime;
	@Column(name = "CATEGORY_ID")
	private int broadcasterChannelCategoryId;
	@Column(name = "RANK")
	private Integer rank;
	@Column(name = "IS_ACTIVE")
	private boolean isActive;

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getBroadcasterChannelCategoryId() {
		return broadcasterChannelCategoryId;
	}

	public void setBroadcasterChannelCategoryId(int broadcasterChannelCategoryId) {
		this.broadcasterChannelCategoryId = broadcasterChannelCategoryId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}


	public int getBroadcasterId() {
		return broadcasterId;
	}

	public void setBroadcasterId(int broadcasterId) {
		this.broadcasterId = broadcasterId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getChannelDescription() {
		return channelDescription;
	}

	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}

	public String getChannelThumbnail() {
		return channelThumbnail;
	}

	public void setChannelThumbnail(String channelThumbnail) {
		this.channelThumbnail = channelThumbnail;
	}
}
