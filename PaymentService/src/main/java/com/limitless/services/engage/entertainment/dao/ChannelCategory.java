package com.limitless.services.engage.entertainment.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "broadcaster_channel_category")
public class ChannelCategory {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "CATEGORY_NAME")
	private String name;
	@Column(name = "RANK")
	private Integer rank;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	
}
