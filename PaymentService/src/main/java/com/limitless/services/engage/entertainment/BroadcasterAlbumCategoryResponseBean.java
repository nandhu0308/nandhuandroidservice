package com.limitless.services.engage.entertainment;

import java.util.List;

import com.limitless.services.socialentity.SocialEntityResponseBean;

public class BroadcasterAlbumCategoryResponseBean {
	private String categoryName;
	private List<AlbumBean> albumList;
	private SocialEntityResponseBean socialEntity;

	public SocialEntityResponseBean getSocialEntity() {
		return socialEntity;
	}

	public void setSocialEntity(SocialEntityResponseBean socialEntity) {
		this.socialEntity = socialEntity;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<AlbumBean> getAlbumList() {
		return albumList;
	}

	public void setChannelList(List<AlbumBean> albumList) {
		this.albumList = albumList;
	}

}
