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
@Table(name="seller_deviceid_mapper", catalog="llcdb")
public class SellerDeviceIdMapper {
	@Id
	@GeneratedValue
	@Column(name="SDM_ID")
	private Integer sdmId;
	@Column(name="SELLER_ID")
	private Integer sellerId;
	@Column(name="SELLER_DEVICEID")
	private String sellerDeviceId;
	@Column(name="DEVICE_ACTIVE")
	private Integer deviceActive;
	@Column(name="SDM_CREATED_TIME", insertable = false, updatable = false)
	private Date sdmCreatedTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Version
	@Column(name="SDM_UPDATED_TIME")
	private Date sdmUpdatedTime;
	
	public Integer getSdmId() {
		return sdmId;
	}
	public void setSdmId(Integer sdmId) {
		this.sdmId = sdmId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerDeviceId() {
		return sellerDeviceId;
	}
	public void setSellerDeviceId(String sellerDeviceId) {
		this.sellerDeviceId = sellerDeviceId;
	}
	public Date getSdmCreatedTime() {
		return sdmCreatedTime;
	}
	public void setSdmCreatedTime(Date sdmCreatedTime) {
		this.sdmCreatedTime = sdmCreatedTime;
	}
	public Date getSdmUpdatedTime() {
		return sdmUpdatedTime;
	}
	public void setSdmUpdatedTime(Date sdmUpdatedTime) {
		this.sdmUpdatedTime = sdmUpdatedTime;
	}
	public Integer getDeviceActive() {
		return deviceActive;
	}
	public void setDeviceActive(Integer deviceActive) {
		this.deviceActive = deviceActive;
	}
}
