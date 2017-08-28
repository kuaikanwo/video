package com.core.model;

import java.util.Date;

public class VideoCollect extends BaseModel{

    private String videoId;

    private String videoTitle;

    private String videoThumbnailPath;

    private Date videoCrtTime;

    private String videoFileName;

    private String videoCrtByUserId;

    private String videoCrtByUserName;

    private String collectUsrId;


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle == null ? null : videoTitle.trim();
    }

    public String getVideoThumbnailPath() {
        return videoThumbnailPath;
    }

    public void setVideoThumbnailPath(String videoThumbnailPath) {
        this.videoThumbnailPath = videoThumbnailPath == null ? null : videoThumbnailPath.trim();
    }

    public Date getVideoCrtTime() {
        return videoCrtTime;
    }

    public void setVideoCrtTime(Date videoCrtTime) {
        this.videoCrtTime = videoCrtTime;
    }

    public String getVideoFileName() {
        return videoFileName;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName == null ? null : videoFileName.trim();
    }

    public String getVideoCrtByUserId() {
        return videoCrtByUserId;
    }

    public void setVideoCrtByUserId(String videoCrtByUserId) {
        this.videoCrtByUserId = videoCrtByUserId == null ? null : videoCrtByUserId.trim();
    }

    public String getVideoCrtByUserName() {
        return videoCrtByUserName;
    }

    public void setVideoCrtByUserName(String videoCrtByUserName) {
        this.videoCrtByUserName = videoCrtByUserName == null ? null : videoCrtByUserName.trim();
    }

    public String getCollectUsrId() {
        return collectUsrId;
    }

    public void setCollectUsrId(String collectUsrId) {
        this.collectUsrId = collectUsrId == null ? null : collectUsrId.trim();
    }

}