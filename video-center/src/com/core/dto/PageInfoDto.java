package com.core.dto;

import com.core.common.Constant;

public class PageInfoDto {
	private Integer index;
	private Integer pageSize;
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getPageSize() {
		return (pageSize-1)*Constant.PAGESIZE;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
