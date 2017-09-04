package com.core.mapper;

import java.util.Date;
import java.util.List;

import com.core.model.PlayGross;

/**
 * Mapperӳ����
 * @author linbingwen
 * @time 2015.5.15
 */
public interface SystemMapper {
	
	void addPlayGross(String id, Date crtDate);
	List<PlayGross> getPlayGross();
}
