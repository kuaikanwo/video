package com.core.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.mapper.VideoCollectMapper;
import com.core.model.Video;
import com.core.model.VideoCollect;
import com.core.service.VideoCollectServiceI;
import com.core.service.VideoServiceI;
import com.core.util.UUIDGenerator;

@Component
public class VideoCollectServiceImp implements VideoCollectServiceI{
    @Autowired
	private VideoCollectMapper videoCollectMapper;
    @Autowired
	private VideoServiceI videoService;

	@Override
	public void add(VideoCollect vc) {
		Video video = videoService.getById(vc.getVideoId());
		
		vc.setId(UUIDGenerator.generate());
		vc.setVideoCrtByUserId(video.getCrtUserId());
		vc.setVideoCrtByUserName(video.getCrtUserName());
		vc.setVideoCrtTime(video.getCrtTime());
		vc.setVideoFileName(video.getFileName());
		vc.setVideoId(video.getId());
		vc.setVideoThumbnailPath(video.getThumbnailPath());
		vc.setVideoTitle(video.getTitle());
		
		videoCollectMapper.insert(vc);
	}

	@Override
	public void delete(String id) {
		videoCollectMapper.delete(id);
	}
}
