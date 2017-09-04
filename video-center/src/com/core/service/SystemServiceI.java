package com.core.service;

import java.util.List;

import com.core.model.PlayGross;


/**
 * @author cuimengtao
 *
 */
public interface SystemServiceI {
	String sendAuthCode(String phone);
	String getCodeByPhone(String phone);
	void addPlayGross();
	List<PlayGross> getPlayGross();
	Integer getCurrentPlayGross();
}
