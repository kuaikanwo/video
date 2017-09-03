package com.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.core.common.Constant;

public class FileUtil {

	private static final int BUFSIZE = 1024 * 8;
	private static final Logger logger = Logger.getLogger(FileUtil.class);

	public static String getSuffix(String fileName) {
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index, fileName.length());
	}

	/**
	 * @param file
	 * @param path
	 * @return
	 */
	public static String write(MultipartFile file, String path) {
		String rawFileName = file.getOriginalFilename();
		String suffix = getSuffix(rawFileName);
		String fileName = UUIDGenerator.generate() + suffix;
		File filepath = new File(path);
		if (!filepath.exists()) {
			filepath.mkdirs();
		}
		try {
			file.transferTo(new File(path + fileName));
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return fileName;
	}

	/**
	 * @param file
	 * @param path
	 * @return
	 */
	public static String write(MultipartFile file, String path,
			String fileName, String originalFilename) {
		File filepath = new File(path);
		if (!filepath.exists()) {
			filepath.mkdirs();
		}
		try {
			String suffix = getSuffix(originalFilename);
			file.transferTo(new File(path + "/" + fileName + suffix));
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
	public static void read(HttpServletResponse response, String filePath) {
		File file = new File(filePath);
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			if (!file.exists()) {
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
				if (in != null && out != null) {
					out.flush();
					in.close();

				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 合并文件
	 * @param outFile
	 * @param res
	 */
	@SuppressWarnings("resource")
	public static void mergeFiles(String outFile, String res) {
		if(StringUtils.isEmpty(outFile))
			return;
		
		File file = new File(outFile);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File filePath = new File(res);

		File[] files = filePath.listFiles();
		FileChannel outChannel = null;
		try {
			outChannel = new FileOutputStream(outFile).getChannel();
			File fileItem = null;
			for(int i=1; i<=files.length; i++) {
				logger.info(filePath+File.separator+String.valueOf(i)+".mp4********************%%%%%%%%%%%%%%%%%%%%%%%%%%%########################");
				fileItem = new File(filePath + File.separator + String.valueOf(i) + ".mp4");
				FileChannel fc = new FileInputStream(fileItem).getChannel();
				ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
				while (fc.read(bb) != -1) {
					bb.flip();
					outChannel.write(bb);
					bb.clear();
				}
				fc.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (outChannel != null) {
					outChannel.close();
				}
			} catch (IOException ignore) {
			}
		}
	}
	
	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
	public static void main(String[] args) {
		mergeFiles("C:\\Users\\mengtao\\Desktop\\tmp\\test.mp4",
				"C:\\Users\\mengtao\\Desktop\\tmp\\test");
	}
}
