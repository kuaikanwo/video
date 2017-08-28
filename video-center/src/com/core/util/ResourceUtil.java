package com.core.util;

import javax.servlet.http.HttpServletRequest;

public class ResourceUtil {
	public static String getValueFromRequestHeaders(HttpServletRequest request, String key){
		return request.getHeader(key);
	}
	
	public static String getCurrentUserToken(HttpServletRequest request){
		return request.getHeader("token");
	}
	public static String getCurrentUserId(HttpServletRequest request){
		return request.getHeader("userId");
	}
	public static String getCurrentUserName(HttpServletRequest request){
		return request.getHeader("userName");
	}
}
