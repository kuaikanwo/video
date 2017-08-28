package com.core.mapper;

import com.core.model.ViewHistory;
/**
 * Mapperӳ����
 * @author linbingwen
 * @time 2015.5.15
 */
public interface ViewHistoryMapper {
	public ViewHistory getByUserIdAndVideoId(String id, String videoId);
	public void add(ViewHistory vi);
}
