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
@Table(name = "video_brand_promotion")
public class VideoBrandPromotion {
	@Id
	@GeneratedValue
	@Column(name = "VBP_ID")
	private Integer vbpId;
	@Column(name = "SELLER_ID")
	private Integer sellerId;
	@Column(name = "PROMOTION_TYPE")
	private String promotionType;
	@Column(name = "END_DATE")
	private String ednDate;
	@Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isActive;
	@Column(name = "VBP_CREATED_TIME", insertable = false, updatable = false)
	private Date vbpCreatedTime;
	@Column(name = "VBP_UPDATED_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	private Date vbpUpdatedTime;
	@Column(name = "AD_URL")
	private String adUrl = "";

	public String getAdUrl() {
		return adUrl == null ? "" : adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public Integer getVbpId() {
		return vbpId;
	}

	public void setVbpId(Integer vbpId) {
		this.vbpId = vbpId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getEdnDate() {
		return ednDate;
	}

	public void setEdnDate(String ednDate) {
		this.ednDate = ednDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getVbpCreatedTime() {
		return vbpCreatedTime;
	}

	public void setVbpCreatedTime(Date vbpCreatedTime) {
		this.vbpCreatedTime = vbpCreatedTime;
	}

	public Date getVbpUpdatedTime() {
		return vbpUpdatedTime;
	}

	public void setVbpUpdatedTime(Date vbpUpdatedTime) {
		this.vbpUpdatedTime = vbpUpdatedTime;
	}
}
