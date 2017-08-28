package com.core.vo;

public class UserVo {
	private String id;
	private String name;
	private String phone;
	private Integer goldCount;
	private String token;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getGoldCount() {
		return goldCount;
	}
	public void setGoldCount(Integer goldCount) {
		this.goldCount = goldCount;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
