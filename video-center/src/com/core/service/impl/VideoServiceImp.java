package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.dto.VideoDto;
import com.core.mapper.VideoMapper;
import com.core.model.Video;
import com.core.service.VideoServiceI;
import com.core.util.DateUtils;
import com.core.util.UUIDGenerator;

@Component
public class VideoServiceImp implements VideoServiceI{
    @Autowired
	private VideoMapper videoMapper;

	@Override
	public void add(Video video) {
		video.setId(UUIDGenerator.generate());
		video.setCrtTime(DateUtils.getDate());
		videoMapper.add(video);
	}

	@Override
	public List<Video> queryAllVideo(VideoDto video) {
		return videoMapper.queryAllVideo(video);
	}

	@Override
	public void increasePlayCount(String id) {
		videoMapper.increasePlayCount(id);
	}

	@Override
	public Video getById(String id) {
		return videoMapper.getById(id);
	}

	@Override
	public void delete(String id) {
		videoMapper.delete(id);
	}

	@Override
	public List<Video> getHot() {
		return videoMapper.getHot();
	}

	@Override
	public List<String> getTitles(String title) {
		return videoMapper.getTitles(title);
	}
}
