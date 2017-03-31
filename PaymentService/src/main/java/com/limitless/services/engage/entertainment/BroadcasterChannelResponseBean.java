package com.limitless.services.engage.entertainment;

import java.util.List;

public class BroadcasterChannelResponseBean {
	private String message;
	private int broadcasterId;
	private String broadcasterName;
	private String channelName;
	private String broadcasterDescription;
	private int totalVideos;
	private List<AlbumBean> albumList;
	
	public int getBroadcasterId() {
		return broadcasterId;
	}
	public void setBroadcasterId(int broadcasterId) {
		this.broadcasterId = broadcasterId;
	}
	public String getBroadcasterName() {
		return broadcasterName;
	}
	public void setBroadcasterName(String broadcasterName) {
		this.broadcasterName = broadcasterName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getBroadcasterDescription() {
		return broadcasterDescription;
	}
	public void setBroadcasterDescription(String broadcasterDescription) {
		this.broadcasterDescription = broadcasterDescription;
	}
	public int getTotalVideos() {
		return totalVideos;
	}
	public void setTotalVideos(int totalVideos) {
		this.totalVideos = totalVideos;
	}
	public List<AlbumBean> getAlbumList() {
		return albumList;
	}
	public void setAlbumList(List<AlbumBean> albumList) {
		this.albumList = albumList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
