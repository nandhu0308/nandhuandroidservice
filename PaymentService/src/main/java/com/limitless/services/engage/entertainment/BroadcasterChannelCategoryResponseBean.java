package com.limitless.services.engage.entertainment;

import java.util.List;

public class BroadcasterChannelCategoryResponseBean {
	private String categoryName;
	private Integer id;
	private List<BroadcasterChannelResponseBean> channelList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<BroadcasterChannelResponseBean> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<BroadcasterChannelResponseBean> channelList) {
		this.channelList = channelList;
	}

}
