package com.limitless.services.engage.entertainment;

import java.util.List;

import com.limitless.services.socialentity.SocialEntityResponseBean;

public class ChannelResponseBean {
	private String message;
	private int broadcasterId;
	private int channelId;
	private int categoryId;
	private int languageId;
	private String channelName;
	private String channelDescription;
	private String channelThumbnail;
	private boolean isHd;
	private SocialEntityResponseBean socialEntity = new SocialEntityResponseBean();

	
	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public SocialEntityResponseBean getSocialEntity() {
		return socialEntity;
	}

	public void setSocialEntity(SocialEntityResponseBean socialEntity) {
		this.socialEntity = socialEntity;
	}

	public int getBroadcasterId() {
		return broadcasterId;
	}

	public void setBroadcasterId(int broadcasterId) {
		this.broadcasterId = broadcasterId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	public boolean isHd() {
		return isHd;
	}

	public void setHd(boolean isHd) {
		this.isHd = isHd;
	}
	
}
