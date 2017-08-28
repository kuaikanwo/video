package com.core.model;

public class BaseModel {
	private String id;
	private Integer isDelete;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}
