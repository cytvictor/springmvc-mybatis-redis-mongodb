package com.codyy.rrt.resource.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codyy.rrt.base.Page;
import com.codyy.rrt.base.Pageable;
import com.codyy.rrt.base.easyui.RequestUtils;
import com.codyy.rrt.base.util.DateUtils;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.model.BaseUser;
import com.codyy.rrt.resource.model.ResourceColumn;
import com.codyy.rrt.resource.model.ResourceFavorite;
import com.codyy.rrt.resource.model.ResourceHold;
import com.codyy.rrt.resource.model.Resources;
import com.codyy.rrt.resource.service.BaseUserService;
import com.codyy.rrt.resource.service.SkeletonService;

/**
 * @author Peter
 */
@Controller
@RequestMapping("/front/resource/visitor")
public class ResourceVistorController extends ResourceBaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1443691309794155119L;
	@Resource
	private BaseUserService baseUserService;
	@Resource
	private SkeletonService skeletonService;

	// 浏览页面
	@RequestMapping("{userId}/{module}")
	public String list(HttpServletRequest request, HttpServletResponse response, @PathVariable String userId, @PathVariable String module) {
		return toListPage(request, userId, module, "");
	}

	// 浏览页面
	@RequestMapping("{userId}/{module}/{id}")
	public String list(HttpServletRequest request, HttpServletResponse response, @PathVariable String userId, @PathVariable String module, @PathVariable String id) {
		return toListPage(request, userId, module, id);
	}

	private String toListPage(HttpServletRequest request, String userId, String module, String id) {
		// 新增
		if (StringUtils.isBlank(userId)) {
			return "front/common/error/404";
		}
		request.setAttribute("ids", userId);
		String visitedUserType = request.getParameter("visitedUserType");
		request.setAttribute("visitedUserType", visitedUserType);
		Map<String, String> map = baseUserService.getUserInfo(userId, visitedUserType);
		request.setAttribute("visitedName", map.get("REALNAME"));
		request.setAttribute("visitedHeadPic", map.get("HEADPIC"));
		// 新增
		request.setAttribute("userId", userId);
		request.setAttribute("module", module);
		request.setAttribute("sideTag", "resource");
		request.setAttribute("resourceUrl", "resource/visitor/" + userId);
		List<ResourceColumn> listResourceColumn = skeletonService.findAllResourceColumn();
		request.setAttribute("listResourceColumn", listResourceColumn);

		ResourceColumn resourceColumn = null;
		if (StringUtils.isEmpty(id) && CollectionUtils.isNotEmpty(listResourceColumn)) {
			id = listResourceColumn.get(0).getId();
			resourceColumn = listResourceColumn.get(0);
		} else {
			resourceColumn = skeletonService.findResourceColumnById(id);
		}

		request.setAttribute("resourceColumn", resourceColumn); // 资源栏目
		request.setAttribute("resourceColumnId", id); // 资源栏目Id
		return "front/resource/visitor_condition";
	}

	@RequestMapping("{userId}/uploaded/content")
	public String uploaded(HttpServletRequest request, HttpServletResponse response, @PathVariable String userId, String resourceColumnId, String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String startTime, String endTime, String resourceName) throws Exception {
		Page page = RequestUtils.buildPage(request);
		page.setPageCountData(6);
		if (userId != null) {
			BaseUser user = baseUserService.findByBaseUserId(userId);

			if (user != null) {
				Pageable<Resources> pageable = null;
				if (user.getUserType().equals(CommonsConstant.USER_TYPE_STUDENT))
					pageable = resourceUserService.findUploadedResourceExcludeDelete(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, formateDate(startTime), formateDate(endTime), Constants.HOLD_TYPE_STUDENT, userId, page);
				else if (user.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER))
					pageable = resourceUserService.findUploadedResourceExcludeDelete(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, formateDate(startTime), formateDate(endTime), Constants.HOLD_TYPE_TEACHER, userId, page);
				String pageStr = ResourcePageUtil.buildPageInfo(pageable);
				output(request, userId, "uploaded", pageable.getList(), pageStr, resourceColumnId);
			}
		}
		return "front/resource/visitor_detail";
	}

	@RequestMapping("{userId}/favorite/content")
	public String favorite(HttpServletRequest request, HttpServletResponse response, @PathVariable String userId, String resourceColumnId, String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String startTime, String endTime, String resourceName) throws Exception {
		Page page = RequestUtils.buildPage(request);
		page.setPageCountData(6);
		if (userId != null) {
			Pageable<ResourceFavorite> pageable = resourceUserService.findFavoriteResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, formateDate(startTime), formateDate(endTime), userId, page);
			String pageStr = ResourcePageUtil.buildPageInfo(pageable);
			output(request, userId, "favorite", pageable.getList(), pageStr, resourceColumnId);
		}
		return "front/resource/visitor_favorite";
	}

	@RequestMapping("{userId}/school/content")
	public String school(HttpServletRequest request, HttpServletResponse response, @PathVariable String userId, String resourceColumnId, String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String startTime, String endTime, String resourceName) throws Exception {
		Page page = RequestUtils.buildPage(request);
		page.setPageCountData(6);
		/*
		 * if (userId != null) { BaseUser user =
		 * baseUserService.findByBaseUserId(userId); String schoolId =
		 * baseUserService.findSchoolIdBySchoolUserId(user .getId()); if (user
		 * != null && StringUtils.isNotEmpty(schoolId)) { Pageable<ResourceHold>
		 * pageable = resourceUserService .findHoldResource(semesterId,
		 * classlevelId, disciplineId, resourceCatalogFirstId,
		 * resourceCatalogSecondId, resourceName, resourceColumnId,
		 * formateDate(startTime), formateDate(endTime),
		 * Constants.HOLD_TYPE_SCHOOL, schoolId, page); String pageStr =
		 * ResourcePageUtil.buildPageInfo(pageable); output(request, userId,
		 * "school", pageable.getList(), pageStr, resourceColumnId); } }
		 */
		// 新增
		if (StringUtils.isNotEmpty(userId)) {
			Pageable<ResourceHold> pageable = resourceUserService.findHoldResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, formateDate(startTime), formateDate(endTime), Constants.HOLD_TYPE_SCHOOL, userId, page);
			String pageStr = ResourcePageUtil.buildPageInfo(pageable);
			output(request, userId, "school", pageable.getList(), pageStr, resourceColumnId);
		}
		// 新增
		return "front/resource/visitor_shared";
	}

	private void output(HttpServletRequest request, String userId, String module, Object list, String pageStr, String resourceColumnId) {
		request.setAttribute("list", list);
		request.setAttribute("pageStr", pageStr);
		request.setAttribute("userId", userId);
		request.setAttribute("module", module);
		request.setAttribute("resourceUrl", "resource/visitor/" + userId);
		request.setAttribute("resourceColumnId", resourceColumnId);
	}

	private Date formateDate(String str) throws ParseException {
		Date st = null;
		if (StringUtils.isNotBlank(str)) {
			st = DateUtils.parseDate(str, "yyyy-MM-dd HH:mm");
		}
		return st;
	}

}