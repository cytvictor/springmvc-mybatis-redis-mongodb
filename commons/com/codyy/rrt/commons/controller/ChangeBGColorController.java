package com.codyy.rrt.commons.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codyy.base.web.SpringContext;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.commons.entity.SsoConfig;

@Controller
@RequestMapping("common")
public class ChangeBGColorController {

	@RequestMapping("changeBGColor")
	public void autoLogin(HttpServletRequest request,HttpServletResponse response,String colorName) throws UnsupportedEncodingException, IOException{
		if(StringUtils.isNotBlank(colorName)){
			LoginUser user = (LoginUser) request.getSession().getAttribute(CommonsConstant.SESSION_LOGIN_USER);
			SsoConfig config = SpringContext.getBean(SsoConfig.class);
			Cookie cookie = new Cookie("bgColor"+user.getUserId(), colorName);
			cookie.setDomain(config.getAppDomain());
			cookie.setMaxAge(864000);//10天 10*24*60*60  天*时*分*秒
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	}
}
