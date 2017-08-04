package com.limitless.services.engage.entertainment;

public class AlbumVideoRequestBean {
	int albumId;
	int videoIndex;

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


	private int customerId;
	private boolean isLoggedIn;

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
