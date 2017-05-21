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
@Table(name="broadcaster_albums", catalog="llcdb")
public class BroadcasterAlbum {
	@Id
	@GeneratedValue
	@Column(name="ALBUMS_ID")
	private Integer albumId;
	@Column(name="ALBUM_NAME")
	private String albumName;
	@Column(name="ALBUM_DESCRIPTION")
	private String albumDescription;
	@Column(name="ALBUM_THUMBNAIL")
	private String albumThumbnail;
	@Column(name="ALBUM_VIDEOS")
	private Integer albumVideos;
	@Column(name="BROADCASTER_ID")
	private int broadcasterId;
	@Column(name="ALBUM_CREATED_TIME", insertable = false, updatable = false)
	private Date albumCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name="ALBUM_UPDATED_TIME")
	private Date albumUpdatedTime;	
	@Column(name="BROADCASTER_ALBUM_CATEGORY_ID")
	private int broadcasterAlbumCategoryId;
	@Column(name = "RANK")
	private Integer rank;
	@Column(name = "isActive")
	private boolean isActive;
	
	
	
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
	public int getBroadcasterAlbumCategoryId() {
		return broadcasterAlbumCategoryId;
	}
	public void setBroadcasterAlbumCategoryId(int broadcasterAlbumCategoryId) {
		this.broadcasterAlbumCategoryId = broadcasterAlbumCategoryId;
	}
	public Integer getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public Integer getAlbumVideos() {
		return albumVideos;
	}
	public void setAlbumVideos(Integer albumVideos) {
		this.albumVideos = albumVideos;
	}
	public int getBroadcasterId() {
		return broadcasterId;
	}
	public void setBroadcasterId(int broadcasterId) {
		this.broadcasterId = broadcasterId;
	}
	public Date getAlbumCreatedTime() {
		return albumCreatedTime;
	}
	public void setAlbumCreatedTime(Date albumCreatedTime) {
		this.albumCreatedTime = albumCreatedTime;
	}
	public Date getAlbumUpdatedTime() {
		return albumUpdatedTime;
	}
	public void setAlbumUpdatedTime(Date albumUpdatedTime) {
		this.albumUpdatedTime = albumUpdatedTime;
	}
	public String getAlbumDescription() {
		return albumDescription;
	}
	public void setAlbumDescription(String albumDescription) {
		this.albumDescription = albumDescription;
	}
	public String getAlbumThumbnail() {
		return albumThumbnail;
	}
	public void setAlbumThumbnail(String albumThumbnail) {
		this.albumThumbnail = albumThumbnail;
	}
}
