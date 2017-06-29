package com.limitless.services.engage.entertainment.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="broadcaster_videos", catalog="llcdb")
public class BroadcasterVideo {
	@Id
	@GeneratedValue
	@Column(name="VIDEOS_ID")
	private Integer videosId;
	@Column(name="ALBUM_ID")
	private Integer albumId;
	@Column(name="VIDEO_NAME")
	private String videoName;
	@Column(name="VIDEO_DESCRIPTION")
	private String videoDescription;
	@Column(name="VIDEO_THUMBNAIL")
	private String videoThumbnail;
	@Column(name="VIDEO_URL")
	private String videoUrl;
	@Column(name="VIDEO_CREATED_TIME", insertable=false, updatable=false)
	private Date videoCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name="VIDEO_UPDATED_TIME")
	private Date videoUpdatedTime;
	@Column(name="IS_YOUTUBE", nullable=false, columnDefinition="TINYINT(1)")
	private boolean isYoutube;
	@Column(name = "RANK")
	private Integer rank;
	@Column(name = "isActive")
	private boolean isActive;
	@Column(name="ISLIVE", nullable=false, columnDefinition="TINYINT(1)")
	private boolean islive;
	
	
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
	public Integer getVideosId() {
		return videosId;
	}
	public void setVideosId(Integer videosId) {
		this.videosId = videosId;
	}
	public Integer getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Date getVideoCreatedTime() {
		return videoCreatedTime;
	}
	public void setVideoCreatedTime(Date videoCreatedTime) {
		this.videoCreatedTime = videoCreatedTime;
	}
	public Date getVideoUpdatedTime() {
		return videoUpdatedTime;
	}
	public void setVideoUpdatedTime(Date videoUpdatedTime) {
		this.videoUpdatedTime = videoUpdatedTime;
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
	public boolean isYoutube() {
		return isYoutube;
	}
	public void setYoutube(boolean isYoutube) {
		this.isYoutube = isYoutube;
	}
	
	public boolean isLive() {
		return islive;
	}
	public void setLive(boolean isLive) {
		this.islive = isLive;
	}
	
}
