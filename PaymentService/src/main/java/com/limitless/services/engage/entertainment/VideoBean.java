package com.limitless.services.engage.entertainment;

import java.util.List;

public class VideoBean {
	private String message;
	private int videoId;
	private int albumId;
	private String albumName;
	private String videoName;
	private String videoDescription;
	private String videoThumbnail;
	private String videoUrl;
	private boolean isYoutube;
	private boolean isLive;
	

	private String videoCreated;
	private int vtId;
	private long liveViewCount;
	private long totalViewCount;
	private AdRollBean preRoll;
	List<AdRollBean> midRolls;

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

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public String getVideoThumbnail() {
		return videoThumbnail;
	}

	public void setVideoThumbnail(String videoThumbnail) {
		this.videoThumbnail = videoThumbnail;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoCreated() {
		return videoCreated;
	}

	public void setVideoCreated(String videoCreated) {
		this.videoCreated = videoCreated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isYoutube() {
		return isYoutube;
	}

	public void setYoutube(boolean isYoutube) {
		this.isYoutube = isYoutube;
	}

	public long getLiveViewCount() {
		return liveViewCount;
	}

	public void setLiveViewCount(long liveViewCount) {
		this.liveViewCount = liveViewCount;
	}

	public long getTotalViewCount() {
		return totalViewCount;
	}

	public void setTotalViewCount(long totalViewCount) {
		this.totalViewCount = totalViewCount;
	}

	public int getVtId() {
		return vtId;
	}

	public void setVtId(int vtId) {
		this.vtId = vtId;
	}

	public AdRollBean getPreRoll() {
		return preRoll;
	}

	public void setPreRoll(AdRollBean preRoll) {
		this.preRoll = preRoll;
	}

	public List<AdRollBean> getMidRolls() {
		return midRolls;
	}

	public void setMidRolls(List<AdRollBean> midRolls) {
		this.midRolls = midRolls;
	}
	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	
}
