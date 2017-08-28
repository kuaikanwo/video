package com.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.core.common.Constant;

public class FileUtil {
	
	private static final Logger logger = Logger.getLogger(FileUtil.class);
	/**
	 * @param file
	 * @param path
	 * @return
	 */
	public static String write(MultipartFile file, String path){
		String rawFileName = file.getOriginalFilename();
		int index = rawFileName.lastIndexOf(".");
		String suffix = rawFileName.substring(index, rawFileName.length());
		String fileName = UUIDGenerator.generate() + suffix;
		File filepath = new File(path, fileName);
		if (!filepath.getParentFile().exists()) {
			filepath.getParentFile().mkdirs();
		}
		try {
			file.transferTo(new File(filepath.getPath()));
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return fileName;
	}
	
	/**
	 * @param response
	 * @param filePath
	 */
	public static void read(HttpServletResponse response, String filePath){
		File file = new File(filePath);
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			if(!file.exists()){
				in = new FileInputStream(Constant.DEFAULT_FILE_ICON);
			}
			in = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] buffer = new byte[4 * 1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if(in != null && out != null ){
					out.flush();
					in.close();
					
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
