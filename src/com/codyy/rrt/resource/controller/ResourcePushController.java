package com.codyy.rrt.resource.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codyy.rrt.base.easyui.ResponseUtils;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.service.ResourceLogService;
import com.codyy.rrt.resource.service.ResourcePushService;

/**
 * @author Peter
 */
@Controller
@RequestMapping("/front/push/")
public class ResourcePushController extends BaseController {

	private static final long serialVersionUID = 1L;
	// private static Logger logger =
	// Logger.getLogger(ResourceOrgController.class);
	@Autowired
	private ResourcePushService resourcePushService;
	
	@Autowired
	private ResourceLogService resourceLogService;

	// 学生的推送给老师
	@RequestMapping("pushTeacher")
	public void studentUploadedPushTeacher(HttpServletRequest request, Writer writer, String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null && loginUser.getUserType().equals(CommonsConstant.USER_TYPE_STUDENT)) {
			boolean result = resourcePushService.studentUploadedPushTeacher(loginUser.getUserId(), id);
			if(result){
				resourceLogService.logResource(id, loginUser);
			}
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 老师自己上传的推送给学校
	@RequestMapping("uploadedPushSchool")
	public void teacherUploadedPushSchool(HttpServletRequest request, Writer writer, String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null && loginUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
			boolean result = resourcePushService.teacherUploadedPushSchool(loginUser.getUserId(), id);
			if(result){
				resourceLogService.logResource(id, loginUser);
			}
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 学校上传的推送给电教馆
	@RequestMapping("schoolUploadPushOrg")
	public void schoolUploadedPushOrg(HttpServletRequest request, Writer writer, String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			boolean result = resourcePushService.schoolUploadedPushOrg(id, loginUser.getUserId());
			if(result){
				resourceLogService.logResource(id, loginUser);
			}
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 电教馆上传的资源推送给上级电教馆
	@RequestMapping("orgUploadedPushOrg")
	public void orgUploadedPushOrg(HttpServletRequest request, Writer writer, String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			boolean result = resourcePushService.orgUploadedPushOrg(id, loginUser.getUserId());
			if(result){
				resourceLogService.logResource(id, loginUser);
			}
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 学生推送给老师的审批通过后推送给学校
	@RequestMapping("pushedPushSchool")
	public void pushedPushSchool(HttpServletRequest request, Writer writer, String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null && loginUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
			boolean result = resourcePushService.pushedPushSchool(id, loginUser.getUserId());
			if(result){
				resourceLogService.logPush(id, loginUser);
			}
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 老师推送的推送给电教馆
	// 学校推送给电教馆的推送给上级电教馆
	// 下级电教馆推送给电教馆的推送给上级电教馆
	@RequestMapping("pushedPushOrg")
	public void pushedPushOrg(HttpServletRequest request, Writer writer, String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			boolean result = resourcePushService.pushedPushOrg(id, loginUser.getUserId());
			if(result){
				resourceLogService.logPush(id, loginUser);
			}
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}
}