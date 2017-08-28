package com.core.service;


/**
 * @author cuimengtao
 *
 */
public interface SystemServiceI {
	String sendAuthCode(String phone);
	String getCodeByPhone(String phone);
}
