package com.limitless.services.engage.entertainment.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "broadcaster_videos", catalog = "llcdb")
public class ChannelVideo {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	

	@Column(name = "BROADCASTER_CHANNEL_ID")
	private Integer channelId;
	
	@Column(name = "VIDEO_NAME")
	private String videoName;
	
	@Column(name = "VIDEO_DESCRIPTION")
	private String videoDescription;
	@Column(name = "VIDEO_THUMBNAIL")
	private String videoThumbnail;
	@Column(name = "URL" , insertable=false , updatable=false)
	private String videoUrl;
	@Column(name = "URL")
	private String url;
	@Column(name = "VIDEO_TYPE")
	private String videoType;
	@Column(name = "P160")
	private boolean p160;
	@Column(name = "P360")
	private boolean p360;
	@Column(name = "P720")
	private boolean p720;
	@Column(name = "P1080")
	private boolean p1080;
	@Column(name = "P_UHD")
	private boolean pUhd;
	@Column(name = "VIDEO_CREATED_TIME", insertable = false, updatable = false)
	private Date videoCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name = "VIDEO_UPDATED_TIME")
	private Date videoUpdatedTime;
	@Column(name = "IS_YOUTUBE", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isYoutube;
	@Column(name = "RANK")
	private Integer rank;
	@Column(name = "IS_ACTIVE")
	private boolean isActive;
	@Column(name = "IS_LIVE", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean islive;
	@Column(name = "duration")
	private int duration;
	@Column(name = "live_ads", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean liveAds;	
	
	
	
	public boolean isLiveAds() {
		return liveAds;
	}

	public void setLiveAds(boolean liveAds) {
		this.liveAds = liveAds;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public boolean isP160() {
		return p160;
	}

	public void setP160(boolean p160) {
		this.p160 = p160;
	}

	public boolean isP360() {
		return p360;
	}

	public void setP360(boolean p360) {
		this.p360 = p360;
	}

	public boolean isP720() {
		return p720;
	}

	public void setP720(boolean p720) {
		this.p720 = p720;
	}

	public boolean isP1080() {
		return p1080;
	}

	public void setP1080(boolean p1080) {
		this.p1080 = p1080;
	}

	public boolean ispUhd() {
		return pUhd;
	}

	public void setpUhd(boolean pUhd) {
		this.pUhd = pUhd;
	}

}
