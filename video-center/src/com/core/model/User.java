package com.core.model;
/**
 * @author cuimengtao
 */
public class User extends BaseModel{
	private String name;
	private String phone;
	private Integer goldCount;
	
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
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String password, String phone) {
		super();
		this.phone = phone;
	}
}
