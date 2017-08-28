package com.core.mapper;

import java.util.List;

import com.core.model.User;
/**
 * Mapperӳ����
 * @author linbingwen
 * @time 2015.5.15
 */
public interface UserMapper {
	public User selectUserById(int userId);
	
	public List<User> getUserByPhone(String phone);
	
	public void addUser(User user);
	
	void updateGoldCount(Integer count, String id);
	
	public Integer getGoldCount(String id);
}
