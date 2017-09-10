package com.limitless.services.engage.entertainment.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "broadcaster_channel")
public class Channel {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer channelId;
	@Column(name = "APPLICATION_ID")
	private Integer applicationId;
	@Column(name = "CHANNEL_NAME")
	private String channelName;
	@Column(name = "CHANNEL_DESCRIPTION")
	private String channelDescription;
	@Column(name = "IMAGE_FILE_NAME")
	private String channelThumbnail;
	@Column(name = "HA_CHANNEL_IMAGE")
	private String haChannelThumbnail;
	@Column(name = "BROADCASTER_ID")
	private int broadcasterId;
	@Column(name = "CREATED_ON", insertable = false, updatable = false)
	private Date createdTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name = "UPDATED_ON")
	private Date updatedTime;
	@Column(name = "CATEGORY_ID")
	private int broadcasterChannelCategoryId;
	@Column(name = "HA_RANK")
	private Integer rank;
	@Column(name = "IS_ACTIVE")
	private boolean isActive;
	@Column(name = "IS_HD")
	private boolean isHd;
	@Column(name = "LANG_ID")
	private Integer languageId;
	@Column(name = "deprecated")
	private boolean deprecated;

	private List<ChannelVideo> videos;

	@Access(AccessType.PROPERTY)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ElementCollection(targetClass = ChannelVideo.class)
	@JoinColumn(name = "broadcaster_channel_id")
	@BatchSize(size = 1)
	@Where(clause = " is_primary=1 ")
	public List<ChannelVideo> getVideos() {
		return videos;
	}

	public void setVideos(List<ChannelVideo> videos) {
		this.videos = videos;
	}

	public boolean isDeprecated() {
		return deprecated;
	}

	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
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

	public int getBroadcasterChannelCategoryId() {
		return broadcasterChannelCategoryId;
	}

	public void setBroadcasterChannelCategoryId(int broadcasterChannelCategoryId) {
		this.broadcasterChannelCategoryId = broadcasterChannelCategoryId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getBroadcasterId() {
		return broadcasterId;
	}

	public void setBroadcasterId(int broadcasterId) {
		this.broadcasterId = broadcasterId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getChannelDescription() {
		return channelDescription;
	}

	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}

	public String getChannelThumbnail() {
		return channelThumbnail;
	}

	public void setChannelThumbnail(String channelThumbnail) {
		this.channelThumbnail = channelThumbnail;
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public boolean isHd() {
		return isHd;
	}

	public void setHd(boolean isHd) {
		this.isHd = isHd;
	}

	public int getLanguageId() {
		return languageId == null ? 1 : languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public String getHaChannelThumbnail() {
		return haChannelThumbnail;
	}

	public void setHaChannelThumbnail(String haChannelThumbnail) {
		this.haChannelThumbnail = haChannelThumbnail;
	}

}
