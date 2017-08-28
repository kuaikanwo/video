package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;

import com.core.common.Constant;
import com.core.mapper.UserMapper;
import com.core.model.User;
import com.core.service.UserServiceI;
import com.core.util.ListUtils;
import com.core.util.RedisUtil;
import com.core.util.UUIDGenerator;

@Component
public class UserServiceImp implements UserServiceI{
    @Autowired
	private UserMapper userMapper;
	@Override
	public User findUserById(int id) {
		User user = userMapper.selectUserById(id);
		 return user; 
	}
	
	@Override
	public List<User> checkUserExits(String phone) {
		return userMapper.getUserByPhone(phone);
	}

	@Override
	public User addUser(User user) {
		user.setId(UUIDGenerator.generate());
		user.setGoldCount(Constant.DEFAULT_GOLD_COUNT);
		userMapper.addUser(user);
		return user;
	}

	@Override
	public User checkUser(String phone, String code) {
		Jedis jedis = null;
		try {
			jedis = RedisUtil.getJedis();
			String dbCode = jedis.get(phone+"db");
			if(StringUtils.isEmpty(dbCode)){
				return null;
			}else{
				if(code.equals(dbCode)){
					List<User> dbUsers = checkUserExits(phone);
					if(ListUtils.isNullOrEmpty(dbUsers)){
						User user = new User();
						user.setPhone(phone);
						user.setName(phone.substring(6, 11));
						user.setGoldCount(Constant.DEFAULT_GOLD_COUNT);
						return addUser(user);
					}else{
						return dbUsers.get(0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			RedisUtil.returnResource(jedis);
		}
		return null;
	}

	@Override
	public void updateGoldCount(Integer count, String id) {
		userMapper.updateGoldCount(count, id);
	}

	@Override
	public Integer getGoldCount(String id) {
		return userMapper.getGoldCount(id);
	}
}
