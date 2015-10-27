package com.codyy.rrt.resource.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codyy.rrt.base.easyui.ResponseUtils;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.model.BaseClassLevel;
import com.codyy.rrt.resource.model.BaseOrg;
import com.codyy.rrt.resource.model.BaseSchool;
import com.codyy.rrt.resource.service.BaseUserService;
import com.codyy.rrt.resource.service.ResourceDistributeService;

/**
 * 资源的分发推送控制
 * @author Peter
 */
@Controller
@RequestMapping("/front/distribute/")
public class ResourceDistributeController extends BaseController {

	private static final long serialVersionUID = 1L;
	// private static Logger logger =
	// Logger.getLogger(ResourceOrgController.class);

	@Autowired
	private ResourceDistributeService resourceDistributeService;
	@Autowired
	private BaseUserService baseUserService;

	// 老师上传的分发给学生
	@RequestMapping("uploadedToStudent")
	public void uploadedToStudent(HttpServletRequest request, Writer writer, String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null && loginUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
			boolean result = resourceDistributeService.uploadedToStudent(loginUser.getUserId(), id);
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 学生推送给老师的资源分发给学生
	@RequestMapping("pushedDistributeStudent")
	public void pushedDistributeStudent(HttpServletRequest request, Writer writer, String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null && loginUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
			boolean result = resourceDistributeService.pushedToStudent(id, loginUser.getUserId());
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 学校分发给老师的资源分发给学生
	@RequestMapping("distributedDistributeStudent")
	public void distributedDistributeStudent(HttpServletRequest request, Writer writer, String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null && loginUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
			boolean result = resourceDistributeService.distributedToStudent(loginUser.getUserId(), id);
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 学校上传的资源分发给老师
	@RequestMapping("schoolUploadedDistributeTeacher")
	public void schoolUploadedDistributeTeacher(HttpServletRequest request, Writer writer, @RequestParam String id, @RequestParam String classlevelIds) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null && StringUtils.isNotBlank(classlevelIds)) {
			String[] clsLvlIds = classlevelIds.split(",");
			List<String> ids = Arrays.asList(clsLvlIds);
			boolean result = resourceDistributeService.uploadedToTeacher(loginUser.getUserId(), id, ids);
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 老师推送的分发给老师
	@RequestMapping("pushedDistributeTeacher")
	public void pushedDistributeTeacher(HttpServletRequest request, Writer writer, @RequestParam String id, @RequestParam String classlevelIds) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null && StringUtils.isNotBlank(classlevelIds)) {
			String[] clsLvlIds = classlevelIds.split(",");
			List<String> ids = Arrays.asList(clsLvlIds);
			boolean result = resourceDistributeService.pushedToTeacher(loginUser.getUserId(), id, ids);
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 上级分发的资源分发给老师
	@RequestMapping("distributedToTeacher")
	public void distributedToTeacher(HttpServletRequest request, Writer writer, @RequestParam String id, @RequestParam String classlevelIds) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null && StringUtils.isNotBlank(classlevelIds)) {
			String[] clsLvlIds = classlevelIds.split(",");
			List<String> ids = Arrays.asList(clsLvlIds);
			boolean result = resourceDistributeService.distributedToTeacher(loginUser.getUserId(), id, ids);
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 机构上传的分发给下级机构或学校
	@RequestMapping("uploadedToSchoolAndOrg")
	public void uploadedToSchoolAndOrg(HttpServletRequest request, Writer writer, @RequestParam String id, String orgIds, String schoolIds) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			String[] a = orgIds.split(",");
			List<String> orgIdList = Arrays.asList(a);
			String[] b = schoolIds.split(",");
			List<String> schoolIdList = Arrays.asList(b);
			boolean result = resourceDistributeService.uploadedToSchoolAndOrg(loginUser.getUserId(), id, orgIdList, schoolIdList);
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 推送给机构的分发给下级机构或学校
	@RequestMapping("pushedToSchoolAndOrg")
	public void pushedToSchoolAndOrg(HttpServletRequest request, Writer writer, @RequestParam String id, String orgIds, String schoolIds) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			String[] a = orgIds.split(",");
			List<String> orgIdList = Arrays.asList(a);
			String[] b = schoolIds.split(",");
			List<String> schoolIdList = Arrays.asList(b);
			boolean result = resourceDistributeService.pushedToSchoolAndOrg(loginUser.getUserId(), id, orgIdList, schoolIdList);
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 分发给机构的再分发给下级机构
	@RequestMapping("distributedToSchoolAndOrg")
	public void distributedToSchoolAndOrg(HttpServletRequest request, Writer writer, @RequestParam String id, String orgIds, String schoolIds) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			String[] a = orgIds.split(",");
			List<String> orgIdList = Arrays.asList(a);
			String[] b = schoolIds.split(",");
			List<String> schoolIdList = Arrays.asList(b);
			boolean result = resourceDistributeService.distributedToSchoolAndOrg(loginUser.getUserId(), id, orgIdList, schoolIdList);
			writer.write(ResponseUtils.buildResultJson(result));
		}
	}

	// 显示选择年级的页面，根据选择的年级来确定分发的老师
	@RequestMapping("classlevelSelect")
	public String classlevelSelect(HttpServletRequest request, Writer writer, @RequestParam String id, @RequestParam String action) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			String schoolId = baseUserService.findSchoolIdBySchoolUserId(loginUser.getUserId());
			if (StringUtils.isNotEmpty(schoolId)) {
				List<BaseClassLevel> list = baseUserService.findClassLevelBySchoolId(schoolId);
				request.setAttribute("list", list);
				request.setAttribute("id", id);
				request.setAttribute("action", action);
				return "front/resource/classlevel_select";
			}
		}
		return null;
	}

	// 显示选择机构或学校的页面
	@RequestMapping("orgAndSchoolSelect")
	public String orgAndSchoolSelect(HttpServletRequest request, Writer writer, @RequestParam String id, @RequestParam String action) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			String orgId = baseUserService.findOrgIdByOrgUserId(loginUser.getUserId());

			if (StringUtils.isNotEmpty(orgId)) {
				List<BaseOrg> orgList = baseUserService.findOrgByParentBaseOrgId(orgId);
				List<BaseSchool> schoolList = baseUserService.findSchoolByBaseOrgId(orgId);
				request.setAttribute("orgList", orgList);
				request.setAttribute("schoolList", schoolList);
				request.setAttribute("id", id);
				request.setAttribute("action", action);
				return "front/resource/org_and_school_select";

			}
		}
		return null;
	}
}