package com.core.controller;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.common.AjaxJson;
import com.core.common.Constant;
import com.core.model.Token;
import com.core.model.User;
import com.core.service.SystemServiceI;
import com.core.service.TokenServiceI;
import com.core.service.UserServiceI;
import com.core.util.CheckUtil;
import com.core.util.PasswordUtil;
import com.core.util.ResourceUtil;
import com.core.vo.UserVo;

@Scope("prototype")
@Controller
@RequestMapping("/userController")
public class UserController {
	
	@Autowired
	private UserServiceI userService;
	@Autowired
	private TokenServiceI tokenService;
	@Autowired
	private SystemServiceI systemService;
	

	/**
	 * 发送验证码
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "sendAuthCode", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson checkuser(String phone, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
        if(StringUtils.isEmpty(phone)){
        	j.setMsg(Constant.INFO_NOT_FULL);
        }else{
        	String code = systemService.sendAuthCode(phone);
        	if(StringUtils.isEmpty(code)){
        		j.setMsg(Constant.HANDLE_ERROR);
        	}else{
        		j.setObj(code);
        		j.setMsg(Constant.HANDLE_SUCCESS);        		
        	}
        }
		return j;
	}
	
	
	/**
	 * 检查用户名密码
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "login", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson checkuser(String phone, String code, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
        	j.setMsg(Constant.INFO_NOT_FULL);
        }else{
        	User dbUser = userService.checkUser(phone, code);
        	if(dbUser == null){
        		j.setStatus(Constant.ERROR_CODE_PHONE_CODE);
        		j.setMsg(Constant.USERNAME_OR_PASSWORD_ERROR);
        	}else{
        		UserVo userVo = new UserVo();
        		try {
					BeanUtils.copyProperties(userVo, dbUser);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		Token token = tokenService.getTokenByUserId(userVo.getId());
        		if(token == null){
        			String tokenValue = PasswordUtil.encrypt(phone, code, PasswordUtil.getStaticSalt());
        			token = new Token();
            		token.setUserId(userVo.getId());
            		token.setToken(tokenValue);
            		tokenService.add(token);
        		}else{
        			tokenService.update(token);
        		}
        		userVo.setToken(token.getToken());
        		j.setStatus(Constant.SUCCESS_CODE);
        		j.setObj(userVo);
        	}
        }
		return j;
	}
	
	/**
	 * 获取金币数量
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getGoldCount", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson getGoldCount(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
        if(CheckUtil.isUnLogin(req)){
        	j.setMsg(Constant.USER_UN_LOGIN);
        	j.setStatus(Constant.ERROR_CODE_USER_UN_LOGIN);
        }else{
        	j.setObj(userService.getGoldCount(ResourceUtil.getCurrentUserId(req)));
        }
		return j;
	}
}
