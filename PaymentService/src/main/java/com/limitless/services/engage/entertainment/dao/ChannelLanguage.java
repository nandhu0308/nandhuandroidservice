package com.limitless.services.engage.entertainment.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "language_info", catalog = "llcdb")
public class ChannelLanguage {
	@Id
	@GeneratedValue	
	private int id;
	private String lang_name;
	private String lang_code;
	private String description;
	private boolean is_active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLang_name() {
		return lang_name;
	}

	public void setLang_name(String lang_name) {
		this.lang_name = lang_name;
	}

	public String getLang_code() {
		return lang_code;
	}

	public void setLang_code(String lang_code) {
		this.lang_code = lang_code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

}
