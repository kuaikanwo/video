package com.core.service;

import com.core.model.ViewHistory;
/**
 * @author cuimengtao
 *
 */
public interface ViewHistoryServiceI {
	
	public ViewHistory getByUserId(String id);
	public ViewHistory add(ViewHistory user);
}
