package com.core.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.common.Constant;
import com.core.service.UserServiceI;
import com.core.util.FileUtil;

@Scope("prototype")
@Controller
@RequestMapping("/fileController")
public class FileController {
	private static final Logger logger = Logger.getLogger(FileController.class);
	
	@Autowired
	private UserServiceI UserService;
	/**
	 * 通过url请求返回图像的字节流
	 */
	@RequestMapping(params = "readVideo")
	public void readVideo(String fileName, String userId, HttpServletRequest request, HttpServletResponse response) {
		Integer goldCount = UserService.getGoldCount(userId);
		if(goldCount > 0){
			response.setHeader("Cache-Control", "max-age=31536000");
			response.setHeader("Content-Type", "video/mp4");
			FileUtil.read(response, Constant.VIDEO_PATH+fileName);
		}
	}
	
	/**
	 * 通过url请求返回图像的字节流
	 */
	@RequestMapping(params = "readThumbnail")
	public void readThumbnail(String fileName, HttpServletRequest request, HttpServletResponse response) {
		FileUtil.read(response, Constant.THUMBNAIL_PATH+fileName);
	}
	
	@RequestMapping(params = "downloadVideo")
	public void download(String fileName, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
        	String path = Constant.VIDEO_PATH+fileName;
            File file = new File(path);
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
          /*  response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));*/
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("video/mp4");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "max-age=31536000");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
        	logger.info(ex.getMessage());
        }
    }
}
