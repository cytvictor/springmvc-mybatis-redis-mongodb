package com.codyy.rrt.resource.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codyy.rrt.base.util.DateUtils;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.model.ResourcePush;
import com.codyy.rrt.resource.service.BaseUserService;
import com.codyy.rrt.resource.service.ResourceLogService;
import com.codyy.rrt.resource.service.ResourcePushService;
import com.codyy.rrt.resource.service.ResourceService;
import com.codyy.rrt.resource.service.SkeletonService;

@Controller
public class ResourceDownLoadController extends BaseController{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	@Autowired
	protected ResourcePushService resourcePushService;
	@Autowired
	private BaseUserService baseUserService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private SkeletonService skeletonService;
	@Autowired
	private ResourceLogService resourceLogService;
	
	@RequestMapping("resourceList")
	public String resourceList(HttpServletRequest request){
		return "front/resourceDownLoad/resourceList";
	}
	
	@RequestMapping("getListInfo")
	@ResponseBody
	public Map<String,Object> getListInfo(HttpServletRequest request, String semesterId, String classlevelId, String disciplineId, String startTime, String endTime, String resourceName,String isDownLoad,int start,int end) throws ParseException, IOException {
		Map<String , Object> map = new HashMap<String , Object>();
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			Date st = null;
			Date et = null;
			if (StringUtils.isNotBlank(startTime)) {
				st = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm");
			}
			if (StringUtils.isNotBlank(endTime)) {
				et = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm");
			}
			List<String> downLoadList = new ArrayList<String>();
			List<String> columnIds = new ArrayList<String>();
			map = resourcePushService.getGDResourcePush(loginUser,semesterId, classlevelId, disciplineId,columnIds, isDownLoad,downLoadList, st, et, resourceName,start,end);
		}
		return map;
	}
	
	@RequestMapping("addDownLoadLog")
	public void addDownLoadLog(HttpServletRequest request , String resourceId , String resourceName){
		LoginUser loginUser = getLoginUser(request);
		resourceLogService.logResourceDownLoad(resourceId, loginUser);
	}
	
	@RequestMapping("toReourceViewVideo")
	public String toReourceViewVideo(String videoPath,HttpServletRequest request,String thumb){
		request.setAttribute("videoPath", videoPath);
		request.setAttribute("thumb", thumb);
		return "front/resourceDownLoad/viewVideo";
	}
}
