package com.limitless.services.engage.entertainment;

import java.util.List;

public class AlbumBean {
	private String message;
	private int albumId;
	private String albumName;
	private String albumDescription;
	private int albumVideoCount;
	private String albumThumbnail;
	private List<VideoBean> videoList;
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getAlbumDescription() {
		return albumDescription;
	}
	public void setAlbumDescription(String albumDescription) {
		this.albumDescription = albumDescription;
	}
	public int getAlbumVideoCount() {
		return albumVideoCount;
	}
	public void setAlbumVideoCount(int albumVideoCount) {
		this.albumVideoCount = albumVideoCount;
	}
	public List<VideoBean> getVideoList() {
		return videoList;
	}
	public void setVideoList(List<VideoBean> videoList) {
		this.videoList = videoList;
	}
	public String getAlbumThumbnail() {
		return albumThumbnail;
	}
	public void setAlbumThumbnail(String albumThumbnail) {
		this.albumThumbnail = albumThumbnail;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
