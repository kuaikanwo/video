package com.core.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.core.util.FfmpegUtil;
import com.core.util.FileUtil;
import com.core.util.ResourceUtil;

@Scope("prototype")
@Controller
@RequestMapping("/videoController")
public class VideoController {

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
	public AjaxJson delVideo(HttpServletRequest request, String id)
			throws Exception {
		AjaxJson aj = new AjaxJson();
		if (StringUtils.isEmpty(id)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			if (CheckUtil.isUnLogin(request)) {
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
	public AjaxJson queryMyupload(HttpServletRequest request, String title,
			Integer pageNo) throws Exception {
		AjaxJson aj = new AjaxJson();
		if (CheckUtil.isUnLogin(request)) {
			aj.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
		} else {
			aj.setStatus(Constant.SUCCESS_CODE);
			if (StringUtils.isEmpty(title))
				title = "";
			String newTitle = new String(title.getBytes("iso8859-1"), "UTF-8");
			aj.setObj(queryVideo(ResourceUtil.getCurrentUserId(request),
					newTitle, pageNo, ""));
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
	public AjaxJson queryAll(HttpServletRequest request, String title, String sortBy,
			Integer pageNo) throws Exception {
		AjaxJson aj = new AjaxJson();
		String newTitle = new String(title.getBytes("UTF-8"), "UTF-8");
		aj.setObj(queryVideo(null, newTitle, pageNo, sortBy));
		return aj;
	}

	@RequestMapping(params = "downloadVideo")
	@ResponseBody
	public AjaxJson downloadVideo(String id, String userId, String isFree,
			HttpServletResponse response, HttpServletRequest request) {
		AjaxJson aj = new AjaxJson();

		// 验证是否登录
		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(userId)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			// 之前是否观看过
			ViewHistory vhis = viewHistoryService.getByUserIdAndVideoId(userId,
					id);
			Video video = videoService.getById(id);
			
			//之前没有观看过
			if (vhis == null) {
				
				//如果是免注册观看
				if(!"YES".equals(isFree)) {
					//如果是视频创建者，不扣金币
					if (video.getCrtUserId().equals(userId)) {
					} else {
						Integer goldCount = UserService.getGoldCount(userId);
						if (goldCount != null && goldCount >= 10) {
							UserService.updateGoldCount(
									Constant.VIEW_VIDEO_PROPORTION_GOLD, userId);
						} else {
							//返回
							aj.setStatus(Constant.NO_GOLD);
							return aj;
						}
					}
				}
				aj.setObj(video.getFileName());
				playHelper(id, userId);
				
				// 保存一条观看记录到数据库
				ViewHistory nhis = new ViewHistory();
				nhis.setVideoId(id);
				nhis.setUserId(userId);
				viewHistoryService.add(nhis);
				
				
			} else {
				aj.setObj(video.getFileName());
				playHelper(id, userId);
			}
		}
		return aj;
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
	public AjaxJson upload(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws Exception {
		AjaxJson aj = new AjaxJson();
		if (CheckUtil.isUnLogin(request)) {
			aj.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
		} else {
			String title = request.getParameter("title");
			String total = request.getParameter("total");
			String index = request.getParameter("index");
			String fileId = request.getParameter("fileid");
			String name = request.getParameter("name");
			String newTitle = new String(title.getBytes("utf-8"), "utf-8");
			if (file.isEmpty() || StringUtils.isEmpty(newTitle)) {
				aj.setMsg(Constant.HANDLE_ERROR);
			} else {

				File filePath = new File(Constant.VIDEO_PATH + fileId);
				if (!filePath.exists())
					filePath.mkdirs();
				FileUtil.write(file, filePath.getPath(), index, name);

				// 如果当前文件是此次分片中的最后一个

				String videoPath = Constant.VIDEO_PATH + fileId;
				// 检验文件是否真是存在于服务器
				File tarPath = new File(videoPath);

				if (tarPath.listFiles().length == Integer.parseInt(total)) {
					String suffix = FileUtil.getSuffix(name);
					String dbFileName = null;//要保存到数据库的文件名
					/*//只有大于10兆的文件才压缩
					if(Integer.parseInt(total) > 5) {
						dbFileName = fileId + "cp"+ suffix;
						//为了便于以后删除大文件，在文件名上做标记
						// 合并
						FileUtil.mergeFiles(videoPath + "BIG" + suffix, videoPath);
						
						// 压缩后的文件名
						FfmpegUtil.compressByLinux(videoPath + "BIG" + suffix, videoPath + "cp" + suffix);
					}else {
						//只合并不压缩
						dbFileName = fileId + suffix;
						FileUtil.mergeFiles(videoPath + suffix, videoPath);
					}*/
					
					//只合并不压缩
					dbFileName = fileId + suffix;
					FileUtil.mergeFiles(videoPath + suffix, videoPath);
					// 压缩合并完成之后删除分片文件和未压缩的文件
					FileUtil.deleteDir(new File(videoPath));
					//new File(videoPath + suffix).delete();
					
					
					// 保存数据到数据库
					String crtUserId = ResourceUtil.getCurrentUserId(request);
					Video video = new Video();
					video.setFileName(dbFileName);
					video.setTitle(newTitle);
					video.setCrtUserId(crtUserId);
					video.setCrtUserName(ResourceUtil
							.getCurrentUserName(request));
					video.setThumbnailPath(FfmpegUtil.generate(fileId + suffix));
					videoService.add(video);

					// 上传者金币加10
					UserService.updateGoldCount(
							Constant.UPLOAD_VIDEO_PROPORTION_GOLD, crtUserId);

					// 上传完毕，返回成功标识
					aj.setStatus(Constant.SUCCESS_CODE);

				}
			}
			aj.setMsg(Constant.HANDLE_SUCCESS);
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
	public AjaxJson collect(HttpServletRequest request, String id)
			throws Exception {
		AjaxJson aj = new AjaxJson();
		if (StringUtils.isEmpty(id)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			if (CheckUtil.isUnLogin(request)) {
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
	public AjaxJson delCollect(HttpServletRequest request, String collectId)
			throws Exception {
		AjaxJson aj = new AjaxJson();
		if (StringUtils.isEmpty(collectId)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			if (CheckUtil.isUnLogin(request)) {
				aj.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
			} else {
				videoCollectService.delete(collectId);
				aj.setMsg(Constant.HANDLE_SUCCESS);
			}
		}
		return aj;
	}

	public List<Video> queryVideo(String userId, String title, Integer pageNo, String srotBy) {
		VideoDto vd = new VideoDto();
		vd.setCrtUserId(userId);
		vd.setTitle(title);
		vd.setIndex(pageNo);
		vd.setPageSize(Constant.PAGESIZE);
		vd.setIsDelete(Constant.UN_DELETE);
		vd.setSrotBy(srotBy);
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
	public AjaxJson getTitles(String title, HttpServletRequest request)
			throws Exception {
		AjaxJson aj = new AjaxJson();
		if (StringUtils.isEmpty(title)) {
			aj.setMsg(Constant.DATA_NOT_EMPTY);
		} else {
			String newTitle = new String(title.getBytes("UTF-8"), "UTF-8");
			aj.setObj(videoService.getTitles(newTitle));
		}
		return aj;
	}
	
	public void playHelper(String id, String userId) {
		// 观看次数加1
		videoService.increasePlayCount(id);
	}
}
