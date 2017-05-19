package com.limitless.services.engage.entertainment;

import java.util.List;

public class BroadcasterAlbumCategoryResponseBean {
	private String categoryName;
	private List<AlbumBean> albumList;

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
