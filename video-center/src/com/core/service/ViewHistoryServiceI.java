package com.core.service;

import com.core.model.ViewHistory;
/**
 * @author cuimengtao
 *
 */
public interface ViewHistoryServiceI {
	
	public ViewHistory getByUserIdAndVideoId(String id, String videoId);
	public ViewHistory add(ViewHistory user);
}
