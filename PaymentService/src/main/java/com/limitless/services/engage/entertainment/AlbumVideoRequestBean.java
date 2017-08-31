package com.limitless.services.engage.entertainment;

public class AlbumVideoRequestBean {
	int albumId;
	int videoIndex;
	private int customerId;
	private boolean isLoggedIn;
	int videoId;
	

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	
	public int getVideoIndex() {
		return videoIndex;
	}

	public void setVideoIndex(int videoIndex) {
		this.videoIndex = videoIndex;
	}


	

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

}
