package com.codyy.rrt.commons.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codyy.base.web.SpringContext;
import com.codyy.rrt.commons.entity.DomainConfig;

@Controller
@RequestMapping("/common")
public class AutoLoginController {

	@RequestMapping("/autoLogin")
	public void autoLogin(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		
		DomainConfig domainConfig = SpringContext.getBean(DomainConfig.class);
		request.getSession().invalidate();// 重置本地session
		response.sendRedirect(domainConfig.getSso()+"autoLogin.html");
	}
}
