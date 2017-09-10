package com.limitless.services.engage.entertainment.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video_ads")
public class VideoAds {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	@Column(name = "VIDEO_ID")
	private Integer videoId;

	@Column(name = "p_roll_url")
	private String preRollUrl;
	@Column(name = "p_code")
	private String preRollCode;

	@Column(name = "m1_roll_url")
	private String midRollUrl_1;
	@Column(name = "m1_code")
	private String midRollUrl_1_code;

	@Column(name = "m2_roll_url")
	private String midRollUrl_2;
	@Column(name = "m2_code")
	private String midRollUrl_2_code;

	@Column(name = "m3_roll_url")
	private String midRollUrl_3;
	@Column(name = "m3_code")
	private String midRollUrl_3_code;

	@Column(name = "m4_roll_url")
	private String midRollUrl_4;
	@Column(name = "m4_code")
	private String midRollUrl_4_code;

	@Column(name = "m5_roll_url")
	private String midRollUrl_5;
	@Column(name = "m5_code")
	private String midRollUrl_5_code;

	@Column(name = "m6_roll_url")
	private String midRollUrl_6;
	@Column(name = "m6_code")
	private String midRollUrl_6_code;

	@Column(name = "m7_roll_url")
	private String midRollUrl_7;
	@Column(name = "m7_code")
	private String midRollUrl_7_code;

	@Column(name = "m8_roll_url")
	private String midRollUrl_8;
	@Column(name = "m8_code")
	private String midRollUrl_8_code;

	@Column(name = "m9_roll_url")
	private String midRollUrl_9;
	@Column(name = "m9_code")
	private String midRollUrl_9_code;

	@Column(name = "m10_roll_url")
	private String midRollUrl_10;
	@Column(name = "m10_code")
	private String midRollUrl_10_code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public String getPreRollUrl() {
		return preRollUrl;
	}

	public void setPreRollUrl(String preRollUrl) {
		this.preRollUrl = preRollUrl;
	}

	public String getPreRollCode() {
		return preRollCode;
	}

	public void setPreRollCode(String preRollCode) {
		this.preRollCode = preRollCode;
	}

	public String getMidRollUrl_1() {
		return midRollUrl_1;
	}

	public void setMidRollUrl_1(String midRollUrl_1) {
		this.midRollUrl_1 = midRollUrl_1;
	}

	public String getMidRollUrl_1_code() {
		return midRollUrl_1_code;
	}

	public void setMidRollUrl_1_code(String midRollUrl_1_code) {
		this.midRollUrl_1_code = midRollUrl_1_code;
	}

	public String getMidRollUrl_2() {
		return midRollUrl_2;
	}

	public void setMidRollUrl_2(String midRollUrl_2) {
		this.midRollUrl_2 = midRollUrl_2;
	}

	public String getMidRollUrl_2_code() {
		return midRollUrl_2_code;
	}

	public void setMidRollUrl_2_code(String midRollUrl_2_code) {
		this.midRollUrl_2_code = midRollUrl_2_code;
	}

	public String getMidRollUrl_3() {
		return midRollUrl_3;
	}

	public void setMidRollUrl_3(String midRollUrl_3) {
		this.midRollUrl_3 = midRollUrl_3;
	}

	public String getMidRollUrl_3_code() {
		return midRollUrl_3_code;
	}

	public void setMidRollUrl_3_code(String midRollUrl_3_code) {
		this.midRollUrl_3_code = midRollUrl_3_code;
	}

	public String getMidRollUrl_4() {
		return midRollUrl_4;
	}

	public void setMidRollUrl_4(String midRollUrl_4) {
		this.midRollUrl_4 = midRollUrl_4;
	}

	public String getMidRollUrl_4_code() {
		return midRollUrl_4_code;
	}

	public void setMidRollUrl_4_code(String midRollUrl_4_code) {
		this.midRollUrl_4_code = midRollUrl_4_code;
	}

	public String getMidRollUrl_5() {
		return midRollUrl_5;
	}

	public void setMidRollUrl_5(String midRollUrl_5) {
		this.midRollUrl_5 = midRollUrl_5;
	}

	public String getMidRollUrl_5_code() {
		return midRollUrl_5_code;
	}

	public void setMidRollUrl_5_code(String midRollUrl_5_code) {
		this.midRollUrl_5_code = midRollUrl_5_code;
	}

	public String getMidRollUrl_6() {
		return midRollUrl_6;
	}

	public void setMidRollUrl_6(String midRollUrl_6) {
		this.midRollUrl_6 = midRollUrl_6;
	}

	public String getMidRollUrl_6_code() {
		return midRollUrl_6_code;
	}

	public void setMidRollUrl_6_code(String midRollUrl_6_code) {
		this.midRollUrl_6_code = midRollUrl_6_code;
	}

	public String getMidRollUrl_7() {
		return midRollUrl_7;
	}

	public void setMidRollUrl_7(String midRollUrl_7) {
		this.midRollUrl_7 = midRollUrl_7;
	}

	public String getMidRollUrl_7_code() {
		return midRollUrl_7_code;
	}

	public void setMidRollUrl_7_code(String midRollUrl_7_code) {
		this.midRollUrl_7_code = midRollUrl_7_code;
	}

	public String getMidRollUrl_8() {
		return midRollUrl_8;
	}

	public void setMidRollUrl_8(String midRollUrl_8) {
		this.midRollUrl_8 = midRollUrl_8;
	}

	public String getMidRollUrl_8_code() {
		return midRollUrl_8_code;
	}

	public void setMidRollUrl_8_code(String midRollUrl_8_code) {
		this.midRollUrl_8_code = midRollUrl_8_code;
	}

	public String getMidRollUrl_9() {
		return midRollUrl_9;
	}

	public void setMidRollUrl_9(String midRollUrl_9) {
		this.midRollUrl_9 = midRollUrl_9;
	}

	public String getMidRollUrl_9_code() {
		return midRollUrl_9_code;
	}

	public void setMidRollUrl_9_code(String midRollUrl_9_code) {
		this.midRollUrl_9_code = midRollUrl_9_code;
	}

	public String getMidRollUrl_10() {
		return midRollUrl_10;
	}

	public void setMidRollUrl_10(String midRollUrl_10) {
		this.midRollUrl_10 = midRollUrl_10;
	}

	public String getMidRollUrl_10_code() {
		return midRollUrl_10_code;
	}

	public void setMidRollUrl_10_code(String midRollUrl_10_code) {
		this.midRollUrl_10_code = midRollUrl_10_code;
	}

}
