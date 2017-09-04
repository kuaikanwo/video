package com.core.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.common.AjaxJson;
import com.core.service.SystemServiceI;
import com.core.service.UserServiceI;

@Scope("prototype")
@Controller
@RequestMapping("/sysController")
public class SystemController {
	@Autowired
	private UserServiceI userService;
	@Autowired
	private SystemServiceI systemService;
	
	/**
	 * 查询当前播放总量
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "queryCurrentPlayGross")
	@ResponseBody
	public AjaxJson queryCurrentPlayGross(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
        j.setObj(systemService.getCurrentPlayGross());
		return j;
	}
	
	/**
	 * 查询播放总量
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "queryPlayGross")
	@ResponseBody
	public AjaxJson queryPlayGross(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
        j.setObj(systemService.getPlayGross());
		return j;
	}
	
	/**
	 * 查询所有用户
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "queryAllUser")
	@ResponseBody
	public AjaxJson queryAllUser(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
        j.setObj(userService.queryAllUser());
		return j;
	}
}
