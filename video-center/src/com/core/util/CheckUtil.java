package com.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class CheckUtil {
	/**
	 * 判断是否登录
	 * @return
	 */
	public static boolean isUnLogin(HttpServletRequest request){
		if(StringUtils.isEmpty(ResourceUtil.getCurrentUserToken(request)) ||
				StringUtils.isEmpty(ResourceUtil.getCurrentUserId(request))	)
			return true;
		else
			return false;
		
	}
}
