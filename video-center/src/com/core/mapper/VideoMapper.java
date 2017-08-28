package com.core.mapper;

import java.util.List;

import com.core.dto.VideoDto;
import com.core.model.Video;
/**
 * 
 * @author cuimengtao
 *
 */
public interface VideoMapper {
	public void add(Video video);
	public List<Video> queryAllVideo(VideoDto video);
	public void increasePlayCount(String id);
	public Video getById(String id);
	public void delete(String id);
	public List<Video> getHot();
	public List<String> getTitles(String title);
}
