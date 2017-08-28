package com.core.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author cuimengtao
 */
public class Video extends BaseModel {
	private String title;
	private String fileName;
	private String playCount;
	private String thumbnailPath;
	private String crtUserId;
	private String crtUserName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date crtTime;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPlayCount() {
		return playCount;
	}
	public void setPlayCount(String playCount) {
		this.playCount = playCount;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getCrtUserId() {
		return crtUserId;
	}
	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}
	public String getCrtUserName() {
		return crtUserName;
	}
	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
}
