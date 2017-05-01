package com.limitless.services.engage.entertainment.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="viewers_track", catalog="llcdb")
public class ViewersTrack {
	@Id
	@GeneratedValue
	@Column(name="VT_ID")
	private Integer vtId;
	@Column(name="VIDEO_ID")
	private Integer videoId;
	@Column(name="CUSTOMER_ID")
	private Integer customerId;
	@Column(name="VIEW_DATE")
	private String viewDate;
	@Column(name="VT_CREATED_TIME", insertable=false, updatable=false)
	private Date vtCreatedTime;
	@Column(name="IS_WATCHING")
	private Integer isWatching;
	
	public Integer getVtId() {
		return vtId;
	}
	public void setVtId(Integer vtId) {
		this.vtId = vtId;
	}
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getViewDate() {
		return viewDate;
	}
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}
	public Date getVtCreatedTime() {
		return vtCreatedTime;
	}
	public void setVtCreatedTime(Date vtCreatedTime) {
		this.vtCreatedTime = vtCreatedTime;
	}
	public Integer getIsWatching() {
		return isWatching;
	}
	public void setIsWatching(Integer isWatching) {
		this.isWatching = isWatching;
	}
}
