package com.core.service;

import java.util.List;

import com.core.model.User;
/**
 * @author cuimengtao
 *
 */
public interface UserServiceI {
	
	public User findUserById(int id);
	public List<User> checkUserExits(String phone);
	public User addUser(User user);
	public User checkUser(String phone, String code);
	public void updateGoldCount(Integer count, String id);
	public Integer getGoldCount(String id);
}
