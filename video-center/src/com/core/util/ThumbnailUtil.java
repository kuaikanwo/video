package com.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.core.common.Constant;

public class ThumbnailUtil {
	private static final Logger logger = Logger.getLogger(ThumbnailUtil.class);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return videoName + ".jpg";*/
		return transfer(videoPath);
	}

	// 视频缩略图截取
	// inFile 输入文件(包括完整路径)
	// outFile 输出文件(可包括完整路径)
	public static String transfer(String inFile) {
		int index = inFile.lastIndexOf(".");
		String videoName = inFile.substring(0, index);
		String outFile = Constant.THUMBNAIL_PATH + videoName + ".jpg";
		String command = "ffmpeg -i " + Constant.VIDEO_PATH+inFile
				+ " -y -f image2 -ss 00:00:1 -t 00:00:01 -s 176x144 "
				+ outFile;
		
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
			t.printStackTrace();
			return null;
		}
		return videoName + ".jpg";
	}

	public static void main(String[] args) {
		System.out.println(generate("402881bb5e277e32015e277e328c0000.mp4"));
	}
}
