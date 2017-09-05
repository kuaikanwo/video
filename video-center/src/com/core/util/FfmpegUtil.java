package com.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.core.common.Constant;

public class FfmpegUtil {
	private static final Logger logger = Logger.getLogger(FfmpegUtil.class);
	public static String generate(String videoPath) {
		/*int index = videoPath.lastIndexOf(".");
		String videoName = videoPath.substring(0, index);
		String imageRealPath = Constant.THUMBNAIL_PATH + videoName + ".jpg";
		try {
			Runtime.getRuntime().exec(
					"cmd /c start E://ffmpeg//ffmpeg.bat "
							+ Constant.VIDEO_PATH + videoPath + " "
							+ imageRealPath);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		return videoName + ".jpg";*/
		return transfer(videoPath);
	}

	/**
	 * linux下的缩略图生成
	 * @param inFile
	 * @return
	 */
	public static String transfer(String inFile) {
		int index = inFile.lastIndexOf(".");
		String videoName = inFile.substring(0, index);
		String outFile = "/usr/local/video/static/thumbnail/" + videoName + ".jpg";
		String command = "ffmpeg -i " + Constant.VIDEO_PATH+inFile
				+ " -y -f image2 -ss 00:00:1 -t 00:00:01 -s 195x260 "
				+ outFile;
		
		try {
			logger.info(outFile+"缩略图路径**************************************");
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				System.out.println(line);
		} catch (Throwable t) {
			logger.info(t.getMessage());
			return null;
		}
		return videoName + ".jpg";
	}

	/**
	 * 压缩视频
	 * @param fileName
	 * @param tarFileName
	 */
	public static void compress(String fileName, String tarFileName){
		try {
			Runtime.getRuntime().exec(
					"cmd /c start E://ffmpeg//compress.bat "
							+ fileName + " "
							+ tarFileName);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}
	
	
	/**
	 * linux下压缩视频
	 * @param inFile
	 * @return
	 */
	public static void  compressByLinux(String fileName, String tarFileName) {
		String command = "ffmpeg -i " + fileName
				+ " -vcodec libx264 -r 10 -b 32k "
				+ tarFileName;
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				System.out.println(line);
		} catch (Throwable t) {
			logger.info(t.getMessage());
		}
	}
	
	public static void main(String[] args) {
		//compress("C:\\Users\\WP\\Desktop\\tmp\\de2edd30bc97d946144fc6d01b4ddb57.mp4", "F:\\videos\\a1.mp4");
		
		File file = new File("C:\\Users\\mengtao\\Desktop\\tmp\\59aa316fd66a9_wpd.mp4");
		System.out.println(file.length()/1024/1024);
		
	}
}
