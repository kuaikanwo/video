package com.core.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.mapper.ViewHistoryMapper;
import com.core.model.ViewHistory;
import com.core.service.ViewHistoryServiceI;
import com.core.util.DateUtils;
import com.core.util.UUIDGenerator;

@Component
public class ViewHistoryServiceImp implements ViewHistoryServiceI{
    @Autowired
	private ViewHistoryMapper viewHistoryMapper;

	@Override
	public ViewHistory getByUserIdAndVideoId(String id, String videoId) {
		return viewHistoryMapper.getByUserIdAndVideoId(id, videoId);
	}

	@Override
	public ViewHistory add(ViewHistory vhis) {
		vhis.setId(UUIDGenerator.generate());
		vhis.setCrtTime(DateUtils.getDate());
		viewHistoryMapper.add(vhis);
		return vhis;
	}

	
}
