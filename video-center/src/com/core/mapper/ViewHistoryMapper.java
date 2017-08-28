package com.core.mapper;

import com.core.model.ViewHistory;
/**
 * Mapperӳ����
 * @author linbingwen
 * @time 2015.5.15
 */
public interface ViewHistoryMapper {
	public ViewHistory getByUserId(String id);
	public ViewHistory add(ViewHistory vi);
}
