package com.core.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlayGross {
	private String id;
	private Integer playGross;
	@JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
	private Date crtDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPlayGross() {
		return playGross;
	}
	public void setPlayGross(Integer playGross) {
		this.playGross = playGross;
	}
	public Date getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
}
