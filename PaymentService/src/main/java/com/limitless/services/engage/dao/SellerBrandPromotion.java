package com.limitless.services.engage.dao;

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
@Table(name = "seller_brand_promotion")
public class SellerBrandPromotion {
	@Id
	@GeneratedValue
	@Column(name = "SBP_ID")
	private Integer sbpId;
	@Column(name = "SELLER_ID")
	private Integer sellerId;
	@Column(name = "PROMOTION_TYPE")
	private String promotionType;
	@Column(name = "END_DATE")
	private String ednDate;
	@Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isActive;
	@Column(name = "SBP_CREATED_TIME", insertable = false, updatable = false)
	private Date sbpCreatedTime;
	@Column(name = "SBP_UPDATED_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	private Date sbpUpdatedTime;
	@Column(name = "AD_URL")
	private String adUrl = "";

	public String getAdUrl() {
		return adUrl == null ? "" : adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public Integer getSbpId() {
		return sbpId;
	}

	public void setSbpId(Integer sbpId) {
		this.sbpId = sbpId;
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

	public Date getSbpCreatedTime() {
		return sbpCreatedTime;
	}

	public void setSbpCreatedTime(Date sbpCreatedTime) {
		this.sbpCreatedTime = sbpCreatedTime;
	}

	public Date getSbpUpdatedTime() {
		return sbpUpdatedTime;
	}

	public void setSbpUpdatedTime(Date sbpUpdatedTime) {
		this.sbpUpdatedTime = sbpUpdatedTime;
	}
}
