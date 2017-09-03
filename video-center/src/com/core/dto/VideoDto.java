package com.core.dto;

public class VideoDto extends PageInfoDto{
	private String title;
	private Integer isDelete;
	private String crtUserId;
	private String srotBy;
	public String getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(String crtUserId) {
		this.crtUserId = crtUserId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSrotBy() {
		return srotBy;
	}

	public void setSrotBy(String srotBy) {
		this.srotBy = srotBy;
	}
}
