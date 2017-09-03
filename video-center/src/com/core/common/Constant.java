package com.core.common;

public class Constant {
	
	/**短信平台信息相关**/
	public static final  String YUNTONGXIN_APPKEY = "LTAILlvM20i5x4kA";
	public static final  String YUNTONGXIN_SECRET = "ZDMrY1I06N4xi6v3aQCg1bOCLiEVPS";
	public static final  String YUNTONGXIN_SIGNNAME = "快看";
	public static final  String YUNTONGXIN_PRODUCT = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
	public static final  String YUNTONGXIN_DOMAIN = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
	
	
	//发送验证码的间隔时间
	public static final Integer AUTH_CODE_SECOND = 60;
	//发送验证码的间隔时间
	public static final Integer AUTH_CODE_VALIDITY_SECOND = 900;
	
	//分页的条数
	public static final Integer PAGESIZE = 20;
	
	
/*	public static final String VIDEO_PATH = "F:\\videos\\";
	// 缩略图保存路径
	public static final String THUMBNAIL_PATH = "F:\\videos\\thumbnail\\";
	*/
	
	// 视频上传路径
	public static final String VIDEO_PATH = "/usr/local/video/mp4/";
	// 缩略图保存路径
	public static final String THUMBNAIL_PATH = "/usr/local/video/static/thumbnail/";

	// 文件不存在返回的默认图标
	public static final String DEFAULT_FILE_ICON = "/usr/videodata/thumbnail/402881bb5e093204015e093204e20000.jpg";

	// 默认的金币数量
	public static final int DEFAULT_GOLD_COUNT = 100;
	//上传一个视频得到10个金币
	public static final int UPLOAD_VIDEO_PROPORTION_GOLD = 10;
	//观看一个视频减去10个金币
	public static final int VIEW_VIDEO_PROPORTION_GOLD = -10;

	// 未删除
	public static final int UN_DELETE = 0;
	// 删除
	public static final int IS_DELETED = 1;

	// 接口返回消息
	public static final String INFO_NOT_FULL = "信息不完整";
	// 用户名密码错误
	public static final String USERNAME_OR_PASSWORD_ERROR = "手机号或验证码错误";
	// 用户已存在
	public static final String USER_PHONE_USED = "手机号已经被注册";
	// 用户注册成功
	public static final String USER_REG_SUCCESS = "注册成功";
	// 用户注册失败
	public static final String USER_REG_FAILED = "注册成功";

	// 参数不能为空
	public static final String DATA_NOT_EMPTY = "参数不能为空";
	// 操作成功
	public static final String HANDLE_SUCCESS = "操作成功";
	// 操作失败
	public static final String HANDLE_ERROR = "操作失败";
	
	
	//操作成功状态码
	public static final int SUCCESS_CODE = 2000;
	//未登录状态码
	public static final int ERROR_CODE_USER_UN_LOGIN = 1111;
	//手机号或验证码错误
	public static final int ERROR_CODE_PHONE_CODE = 2222;
	//金币不足
	public static final int NO_GOLD = 8888;
}
