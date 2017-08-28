package com.core.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.common.AjaxJson;
import com.core.common.Constant;
import com.core.dto.VideoDto;
import com.core.model.Video;
import com.core.model.VideoCollect;
import com.core.model.ViewHistory;
import com.core.service.UserServiceI;
import com.core.service.VideoCollectServiceI;
import com.core.service.VideoServiceI;
import com.core.service.ViewHistoryServiceI;
import com.core.util.CheckUtil;
import com.core.util.FileUtil;
import com.core.util.ResourceUtil;
import com.core.util.ThumbnailUtil;

@Scope("prototype")
@Controller
@RequestMapping("/videoController")
public class VideoController {
	
	private static final Logger logger = Logger.getLogger(VideoController.class);
	
	@Autowired
	private UserServiceI UserService;
	@Autowired
	private VideoServiceI videoService;
	@Autowired
	private VideoCollectServiceI videoCollectService;
	@Autowired
	private ViewHistoryServiceI viewHistoryService;
	
	/**
	 * 删除我上传的视频
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "delVideo", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson delVideo(HttpServletRequest request, String id) throws Exception {
		AjaxJson aj = new AjaxJson();
		if (StringUtils.isEmpty(id)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			if (CheckUtil.isUnLogin(request)) {
				aj.setMsg(Constant.USER_UN_LOGIN);
				aj.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
			} else {
				videoService.delete(id);
				aj.setMsg(Constant.HANDLE_SUCCESS);
			}
		}
		return aj;
	}

	/**
	 * 
	 * 查询我的上传
	 * 
	 * @param request
	 * @param title
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "queryMyupload")
	@ResponseBody
	public AjaxJson queryMyupload(HttpServletRequest request, String title, Integer pageNo) throws Exception {
		AjaxJson aj = new AjaxJson();
		if (CheckUtil.isUnLogin(request)) {
			aj.setMsg(Constant.USER_UN_LOGIN);
			aj.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
		} else {
			aj.setStatus(Constant.SUCCESS_CODE);
			if (StringUtils.isEmpty(title))
				title = "";
			String newTitle = new String(title.getBytes("iso8859-1"), "UTF-8");
			aj.setObj(queryVideo(ResourceUtil.getCurrentUserId(request), newTitle, pageNo));
		}

		return aj;
	}

	/**
	 * 分页查询所有视频
	 * 
	 * @param request
	 * @param title
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "queryAll")
	@ResponseBody
	public AjaxJson queryAll(HttpServletRequest request, String title, Integer pageNo) throws Exception {
		AjaxJson aj = new AjaxJson();
		String newTitle = new String(title.getBytes("iso8859-1"), "UTF-8");
		aj.setObj(queryVideo(null, newTitle, pageNo));
		return aj;
	}

	@RequestMapping(params = "downloadVideo")
	public void download(String videoId, String userId, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson aj = new AjaxJson();
		
		//验证是否登录
		if (StringUtils.isEmpty(videoId) || StringUtils.isEmpty(userId)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			Video video = videoService.getById(videoId);
			
			//校验金币数量是否足够，如果足够就减10
			
			if (goldCount == 0) {
				aj.setStatus(Constant.NO_GOLD);
			} else {
				ViewHistory vhis = viewHistoryService.getByUserId(userId);
				//如果之前没有看过
				if(vhis == null) {
					//判断观看者是不是视频创建者
					if (!video.getCrtUserId().equals(userId) ) {
						
						
						Integer goldCount = UserService.getGoldCount(userId);
						if (goldCount == 0) {
							aj.setStatus(Constant.NO_GOLD);
							return aj;
						} else {
							UserService.updateGoldCount(Constant.VIEW_VIDEO_PROPORTION_GOLD,
									ResourceUtil.getCurrentUserId(request));
						}
						
						
					}
					
					//保存一条观看记录到数据库
					ViewHistory nhis = new ViewHistory();
					
					nhis.setVideoId(videoId);
					nhis.setUserId(userId);
					viewHistoryService.add(nhis);
				}
				
				//减去金币数量之后提供视频
				try {
		            // path是指欲下载的文件的路径。
		        	String path = Constant.VIDEO_PATH+video.getFileName();
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
				
				//播放次数加1
				videoService.increasePlayCount(videoId);
			}
		}
    }

	/**
	 * 上传视频
	 * 
	 * @param request
	 * @param file
	 * @param title
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "upload", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
		AjaxJson aj = new AjaxJson();
		if (CheckUtil.isUnLogin(request)) {
			aj.setMsg(Constant.USER_UN_LOGIN);
			aj.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
		} else {
			String title = request.getParameter("title");
			if (file.isEmpty() || StringUtils.isEmpty(title)) {
				aj.setMsg(Constant.HANDLE_ERROR);
			} else {
				String fileName = FileUtil.write(file, Constant.VIDEO_PATH);
				String crtUserId = ResourceUtil.getCurrentUserId(request);
				Video video = new Video();
				video.setFileName(fileName);
				video.setTitle(title);
				video.setCrtUserId(crtUserId);
				video.setCrtUserName(ResourceUtil.getCurrentUserName(request));
				video.setThumbnailPath(ThumbnailUtil.generate(fileName));
				videoService.add(video);

				// 上传者金币加10
				UserService.updateGoldCount(Constant.UPLOAD_VIDEO_PROPORTION_GOLD, crtUserId);

				aj.setMsg(Constant.HANDLE_SUCCESS);
			}
		}
		return aj;
	}

	/**
	 * 播放次数加1
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "playing", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson playing(HttpServletRequest request, String id) throws Exception {
		AjaxJson aj = new AjaxJson();
		if (StringUtils.isEmpty(id) || CheckUtil.isUnLogin(request)) {
			aj.setMsg(Constant.USER_UN_LOGIN);
			aj.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
		} else {
			videoService.increasePlayCount(id);
			Video video = videoService.getById(id);
			// 观看者金币减10
			String curUserId = ResourceUtil.getCurrentUserId(request);
			Integer goldCount = UserService.getGoldCount(curUserId);
			if (goldCount == 0) {
				aj.setStatus(Constant.NO_GOLD);
			} else {
				if (!video.getCrtUserId().equals(curUserId))
					UserService.updateGoldCount(Constant.VIEW_VIDEO_PROPORTION_GOLD,
							ResourceUtil.getCurrentUserId(request));
			}
		}
		return aj;
	}

	/**
	 * 收藏视频
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "collect", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson collect(HttpServletRequest request, String id) throws Exception {
		AjaxJson aj = new AjaxJson();
		if (StringUtils.isEmpty(id)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			if (CheckUtil.isUnLogin(request)) {
				aj.setMsg(Constant.USER_UN_LOGIN);
				aj.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
			} else {
				VideoCollect vc = new VideoCollect();
				vc.setCollectUsrId(ResourceUtil.getCurrentUserId(request));
				vc.setVideoId(id);
				videoCollectService.add(vc);
				aj.setMsg(Constant.HANDLE_SUCCESS);
			}
		}
		return aj;
	}

	/**
	 * 取消收藏
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "delCollect", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson delCollect(HttpServletRequest request, String collectId) throws Exception {
		AjaxJson aj = new AjaxJson();
		if (StringUtils.isEmpty(collectId)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			if (CheckUtil.isUnLogin(request)) {
				aj.setMsg(Constant.USER_UN_LOGIN);
				aj.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
			} else {
				videoCollectService.delete(collectId);
				aj.setMsg(Constant.HANDLE_SUCCESS);
			}
		}
		return aj;
	}

	public List<Video> queryVideo(String userId, String title, Integer pageNo) {
		VideoDto vd = new VideoDto();
		vd.setCrtUserId(userId);
		vd.setTitle(title);
		vd.setIndex(pageNo);
		vd.setPageSize(Constant.PAGESIZE);
		vd.setIsDelete(Constant.UN_DELETE);
		return videoService.queryAllVideo(vd);
	}

	/**
	 * 得到热门的前10条
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "getHot")
	@ResponseBody
	public AjaxJson getHot(HttpServletRequest request) throws Exception {
		AjaxJson aj = new AjaxJson();
		aj.setObj(videoService.getHot());
		return aj;
	}

	/**
	 * 模糊查询视频标题
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "getTitles")
	@ResponseBody
	public AjaxJson getTitles(String title, HttpServletRequest request) throws Exception {
		AjaxJson aj = new AjaxJson();
		if (StringUtils.isEmpty(title)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			String newTitle = new String(title.getBytes("iso8859-1"), "UTF-8");
			aj.setObj(videoService.getTitles(newTitle));
		}
		return aj;
	}
}
