package com.limitless.services.engage.sellers.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seller_version")
public class SellerVersion {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "VERSION")
	private String version;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
