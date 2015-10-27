package com.codyy.rrt.resource.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codyy.rrt.resource.service.ResourceLogService;

@Controller
@RequestMapping("resourceLog")
public class ResourceLogController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ResourceLogService resourceLogService;
	
	@ResponseBody
	@RequestMapping("getResourceLogByCondition")
	public Map<String,Object> getResourceLogByCondition(String resourceName,String realName,String start,String end){
		return resourceLogService.getResourceLogByCondition(resourceName, realName, start, end);
	}
	
	@RequestMapping("resourceLogIndex")
	public String toReourceLog(){
		return "front/resourceLog/resourceLog";
	}
}