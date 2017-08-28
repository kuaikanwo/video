package com.core.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;

import com.core.common.Constant;
import com.core.service.SystemServiceI;
import com.core.util.RedisUtil;
import com.core.util.SMSUtil;

@Component
public class SystemServiceImp implements SystemServiceI{

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
}
