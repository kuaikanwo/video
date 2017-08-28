package com.core.mapper;

import java.util.List;

import com.core.dto.VideoCollectDto;
import com.core.model.VideoCollect;


public interface VideoCollectMapper {
    int insert(VideoCollect record);
    List<VideoCollect> getMyCollect(VideoCollectDto vc);
    void delete(String id);
}