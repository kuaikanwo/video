package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;

import com.core.common.Constant;
import com.core.mapper.SystemMapper;
import com.core.mapper.TokenMapper;
import com.core.model.PlayGross;
import com.core.service.SystemServiceI;
import com.core.util.DateUtils;
import com.core.util.RedisUtil;
import com.core.util.SMSUtil;
import com.core.util.UUIDGenerator;

@Component
public class SystemServiceImp implements SystemServiceI{

	@Autowired
	private TokenMapper tokenMapper;
	@Autowired
	private SystemMapper systemMapper;
	
	@Override
	public String sendAuthCode(String phone) {
		Jedis jedis = null;
		try {
			jedis = RedisUtil.getJedis();
			if(StringUtils.isEmpty(jedis.get(phone))){
				String code = SMSUtil.createRandomVcode();
				//SMSUtil.sendAuthCode(phone, code);
				jedis.setex(phone, Constant.AUTH_CODE_SECOND, code);
				jedis.setex(phone+"db", Constant.AUTH_CODE_VALIDITY_SECOND, code);
				return code;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			RedisUtil.returnResource(jedis);
		}
		return null;
	}

	@Override
	public String getCodeByPhone(String phone) {
		Jedis jedis = null;
		try {
			jedis = RedisUtil.getJedis();
			return jedis.get(phone);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			RedisUtil.returnResource(jedis);
		}
		return null;
	}

	@Override
	public void addPlayGross() {
		systemMapper.addPlayGross(UUIDGenerator.generate(), DateUtils.gettimestamp());
	}

	@Override
	public List<PlayGross> getPlayGross() {
		return systemMapper.getPlayGross();
	}
}
