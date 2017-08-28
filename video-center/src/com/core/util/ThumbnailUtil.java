package com.core.util;

import java.io.IOException;

import com.core.common.Constant;

public class ThumbnailUtil {
	public static String generate(String videoPath){
		int index = videoPath.lastIndexOf(".");
		String videoName = videoPath.substring(0, index);
        String imageRealPath = Constant.THUMBNAIL_PATH+videoName+".jpg";   
        try {   
            Runtime.getRuntime().exec("cmd /c start G://ffmpeg.bat " + Constant.VIDEO_PATH+videoPath + " " + imageRealPath);   
        } catch (IOException e) {   
            // TODO Auto-generated catch block   
            e.printStackTrace();   
        }
		return videoName+".jpg";
	}
	
    public static void main(String[] args){
    	System.out.println(generate("402881bb5e08db96015e08db962a0000.mp4"));
    }
}
