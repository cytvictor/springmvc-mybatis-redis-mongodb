package com.codyy.rrt.resource.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codyy.rrt.base.Page;
import com.codyy.rrt.base.Pageable;
import com.codyy.rrt.base.easyui.RequestUtils;
import com.codyy.rrt.base.util.DateUtils;
import com.codyy.rrt.base.util.PropertyUtils;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.model.ResourceColumn;
import com.codyy.rrt.resource.model.ResourceDistribute;
import com.codyy.rrt.resource.model.ResourceDistributeTarget;
import com.codyy.rrt.resource.model.ResourceHold;
import com.codyy.rrt.resource.model.ResourcePush;
import com.codyy.rrt.resource.model.Resources;
import com.codyy.rrt.resource.service.BaseUserService;
import com.codyy.rrt.resource.service.SkeletonService;

/**
 * 资源访问首页
 * 学校
 */
@Controller
@RequestMapping("/front/schoolresource/")
public class ResourceSchoolController extends ResourceBaseController {

	private static final long serialVersionUID = 1L;
	@Resource
	private BaseUserService baseUserService;
	@Resource
	private SkeletonService skeletonService;
	// private static Logger logger =
	// Logger.getLogger(ResourceSchoolController.class);
	private static final Set<String> moduleSet = new HashSet<String>();
	static {
		moduleSet.add("higherpush");
		moduleSet.add("lowerpush");
		moduleSet.add("uploaded");
		moduleSet.add("shared");
	}

	@RequestMapping("{module}")
	public String list(HttpServletRequest request, HttpServletResponse response, @PathVariable String module) {
		return toListPage(request, module, "");
	}

	@RequestMapping("{module}/{id}")
	public String list(HttpServletRequest request, HttpServletResponse response, @PathVariable String module, @PathVariable String id) {
		return toListPage(request, module, id);
	}

	private String toListPage(HttpServletRequest request, String module, String id) {
		request.setAttribute("schoolTag", "yes");

		if (moduleSet.contains(module)) {
			LoginUser loginUser = getLoginUser(request);
			/*
			 * if (!Constants.USER_TYPE_SCHOOL.equals(loginUser.getUserType()))
			 * { return "front/resource/no_permission"; }
			 */

			request.setAttribute("loginUser", loginUser);
			request.setAttribute("resourceUrl", "schoolresource"); // 资源根Url
			request.setAttribute("module", module);
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
			request.setAttribute("resourceColumnId", id);
			return "front/resource/condition";
		}
		return "front/common/error/404";

	}

	@RequestMapping("{module}/content")
	public String content(HttpServletRequest request, HttpServletResponse response, @PathVariable String module, String resourceColumnId, String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String startTime, String endTime, String resourceName) throws UnsupportedEncodingException, ParseException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		request.setAttribute("schoolTag", "yes");

		if (moduleSet.contains(module)) {
			LoginUser loginUser = getLoginUser(request);
			// 只有学校超级管理员或者是老师又是管理员才能访问
			if (!CommonsConstant.USER_TYPE_SCHOOL.equals(loginUser.getUserType())) {
				String permission  = loginUser.getPermission();
				Boolean flag = false;
				if(!StringUtils.isBlank(permission)){
					flag = (","+permission+",").indexOf(",2,") != -1 ? true:false;
				}				
				if(!CommonsConstant.USER_TYPE_TEACHER.equals(loginUser.getUserType()) && !flag){
					return "front/resource/no_permission";
				}				
			}

			Page page = RequestUtils.buildPage(request);
			page.setPageCountData(6);
			if (loginUser != null) {
				Date st = null;
				Date et = null;
				if (StringUtils.isNotBlank(startTime)) {
					st = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm");
				}
				if (StringUtils.isNotBlank(endTime)) {
					et = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm");
				}
				String schoolId = null;
				schoolId = baseUserService.findSchoolIdBySchoolUserId(loginUser.getUserId());
				if (schoolId != null) {
					if (module.equals("uploaded")) {
						Pageable<Resources> pageable = resourceUserService.findUploadedResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
						String pageStr = ResourcePageUtil.buildPageInfo(pageable);
						request.setAttribute("list", pageable.getList());
						request.setAttribute("pageStr", pageStr);
						List<String> ids = PropertyUtils.getProperties(pageable.getList(), "id");
						// 推送状态
						List<ResourcePush> pushs = resourcePushService.findPushPusherRecord(Constants.HOLD_TYPE_SCHOOL, schoolId, ids);
						Map<String, ResourcePush> pushMap = new HashMap<String, ResourcePush>();
						for (ResourcePush push : pushs) {
							pushMap.put(push.getResourceId(), push);
						}
						request.setAttribute("pushMap", pushMap);
						// 下发状态
						List<ResourceDistribute> distributes = resourceDistributeService.findDistributeRecord(Constants.HOLD_TYPE_SCHOOL, schoolId, ids);
						Map<String, ResourceDistribute> distributeMap = new HashMap<String, ResourceDistribute>();
						for (ResourceDistribute distribute : distributes) {
							distributeMap.put(distribute.getResourceId(), distribute);
						}
						request.setAttribute("distributeMap", distributeMap);
					} else if (module.equals("lowerpush")) {
						Pageable<ResourcePush> pageable = resourcePushService.findPushReciveRecord(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
						String pageStr = ResourcePageUtil.buildPageInfo(pageable);
						request.setAttribute("list", pageable.getList());
						request.setAttribute("pageStr", pageStr);
						// 查询收到的ResourcePush再往上推送的状态
						Set<String> nextPushIds = new HashSet<String>();
						for (ResourcePush push : pageable.getList()) {
							if (StringUtils.isNotBlank(push.getNextResourcePushId()))
								nextPushIds.add(push.getNextResourcePushId());
						}
						List<ResourcePush> nextPushs = resourcePushService.findByResourcePushIds(nextPushIds);
						Map<String, ResourcePush> nextPushMap = new HashMap<String, ResourcePush>();
						for (ResourcePush nextPush : nextPushs) {
							nextPushMap.put(nextPush.getId(), nextPush);
						}
						request.setAttribute("nextPushMap", nextPushMap);
					} else if (module.equals("higherpush")) {
						Pageable<ResourceDistributeTarget> pageable = resourceDistributeService.findDistributeTargetRecord(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
						String pageStr = ResourcePageUtil.buildPageInfo(pageable);
						request.setAttribute("list", pageable.getList());
						request.setAttribute("pageStr", pageStr);
					} else if (module.equals("shared")) {
						Pageable<ResourceHold> pageable = resourceUserService.findHoldResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
						String pageStr = ResourcePageUtil.buildPageInfo(pageable);
						request.setAttribute("list", pageable.getList());
						request.setAttribute("pageStr", pageStr);
					}
				}
			}
			request.setAttribute("resourceUrl", "schoolresource"); // 资源根Url
			request.setAttribute("module", module);
		}
		return "front/resource/school/school_" + module;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("{module}/contentlist")
	public List contentlist(HttpServletRequest request, HttpServletResponse response, @PathVariable String module, String resourceColumnId, String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String startTime, String endTime, String resourceName, Integer pageCount) throws UnsupportedEncodingException, ParseException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List ls = new ArrayList();
		if (moduleSet.contains(module)) {
			LoginUser loginUser = getLoginUser(request);
			Page page = RequestUtils.buildPage(request);
			page.setPageCountData(pageCount);
			if (loginUser != null) {
				Date st = null;
				Date et = null;
				if (StringUtils.isNotBlank(startTime)) {
					st = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm");
				}
				if (StringUtils.isNotBlank(endTime)) {
					et = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm");
				}
				String schoolId = baseUserService.findSchoolIdBySchoolUserId(loginUser.getUserId());
				if (schoolId != null) {
					if (module.equals("uploaded")) {
						Pageable<Resources> pageable = resourceUserService.findUploadedResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
						String pageStr = ResourcePageUtil.buildPageInfo(pageable);
						request.setAttribute("list", pageable.getList());
						request.setAttribute("pageStr", pageStr);
						List<String> ids = PropertyUtils.getProperties(pageable.getList(), "id");
						// 推送状态
						List<ResourcePush> pushs = resourcePushService.findPushPusherRecord(Constants.HOLD_TYPE_SCHOOL, schoolId, ids);
						Map<String, ResourcePush> pushMap = new HashMap<String, ResourcePush>();
						for (ResourcePush push : pushs) {
							pushMap.put(push.getResourceId(), push);
						}
						request.setAttribute("pushMap", pushMap);
						// 下发状态
						List<ResourceDistribute> distributes = resourceDistributeService.findDistributeRecord(Constants.HOLD_TYPE_SCHOOL, schoolId, ids);
						Map<String, ResourceDistribute> distributeMap = new HashMap<String, ResourceDistribute>();
						for (ResourceDistribute distribute : distributes) {
							distributeMap.put(distribute.getResourceId(), distribute);
						}
						request.setAttribute("distributeMap", distributeMap);
						return pageable.getList();
					} else if (module.equals("lowerpush")) {
						Pageable<ResourcePush> pageable = resourcePushService.findPushReciveRecord(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
						String pageStr = ResourcePageUtil.buildPageInfo(pageable);
						request.setAttribute("list", pageable.getList());
						request.setAttribute("pageStr", pageStr);
						// 查询收到的ResourcePush再往上推送的状态
						Set<String> nextPushIds = new HashSet<String>();
						for (ResourcePush push : pageable.getList()) {
							if (StringUtils.isNotBlank(push.getNextResourcePushId()))
								nextPushIds.add(push.getNextResourcePushId());
							// TODO
							// push.setBaseUserName(push.getResources()
							// .getBaseUserName());
						}

						List<ResourcePush> nextPushs = resourcePushService.findByResourcePushIds(nextPushIds);
						Map<String, ResourcePush> nextPushMap = new HashMap<String, ResourcePush>();
						for (ResourcePush nextPush : nextPushs) {
							nextPushMap.put(nextPush.getId(), nextPush);
						}
						// return pageable.getList();
						List<ResourcePush> list = pageable.getList();
						// } else if (module.equals("higherpush")){

						Pageable<ResourceDistributeTarget> pageableh = resourceDistributeService.findDistributeTargetRecord(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
						// String pageStrh =
						// ResourcePageUtil.buildPageInfo(pageableh);

						for (ResourceDistributeTarget res : pageableh.getList()) {
							res.setBaseUserName(res.getBaseUserName());
						}

						// request.setAttribute("list", hpageable.getList());
						// request.setAttribute("pageStr", hpageStr);

						// return hpageable.getList();
						List<ResourceDistributeTarget> hlist = pageableh.getList();

						List<Object> result = new ArrayList<Object>();

						// 将获取的数据放入数组中
						Object[] obs = new Object[pageCount * 2];
						for (int i = 0; i < list.size(); i++) {
							obs[i] = list.get(i);
						}

						for (int i = 0; i < hlist.size(); i++) {
							obs[i + list.size()] = hlist.get(i);
						}

						// 将数组按照时间排序
						// int leng=obs.length;

						int len = list.size() + hlist.size();
						int i, j;
						Object temp;
						for (i = 1; i <= len; i++) {
							for (j = len - 1; j >= 1; j--) {
								Date t1 = (obs[j] instanceof ResourcePush) ? ((ResourcePush) obs[j]).getCreateTime() : ((ResourceDistributeTarget) obs[j]).getCreateTime();
								Date t2 = (obs[j - 1] instanceof ResourcePush) ? ((ResourcePush) obs[j - 1]).getCreateTime() : ((ResourceDistributeTarget) obs[j - 1]).getCreateTime();
								// if (n2[j] > n2[j - 1]) {
								if (t1.after(t2)) {
									temp = obs[j];
									obs[j] = obs[j - 1];
									obs[j - 1] = temp;
								}
							}
						}
						for (int k = 0; k < pageCount; k++) {
							if (obs[k] != null) {
								ls.add(obs[k]);
							}
						}
						return ls;

					} else if (module.equals("shared")) {
						Pageable<ResourceHold> pageable = resourceUserService.findHoldResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
						String pageStr = ResourcePageUtil.buildPageInfo(pageable);
						request.setAttribute("list", pageable.getList());
						request.setAttribute("pageStr", pageStr);
						return pageable.getList();
					}
				}
			}
			request.setAttribute("resourceUrl", "schoolresource"); // 资源根Url
			request.setAttribute("module", module);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/content")
	public List allcontent(HttpServletRequest request, HttpServletResponse response) {

		Page page = RequestUtils.buildPage(request);
		page.setPageCountData(4);
		LoginUser loginUser = getLoginUser(request);
		String schoolId = baseUserService.findSchoolIdBySchoolUserId(loginUser.getUserId());
		List ls = new ArrayList();
		if (schoolId == null) {
			return ls;
		}
		Pageable<ResourcePush> pageable = resourcePushService.findPushReciveRecord(null, null, null, null, null, null, null, null, null, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
		Pageable<ResourceDistributeTarget> pageableDT = resourceDistributeService.findDistributeTargetRecord(null, null, null, null, null, null, null, null, null, Constants.HOLD_TYPE_SCHOOL, schoolId, page);
		List<ResourcePush> rps = pageable.getList();
		List<ResourceDistributeTarget> rdts = pageableDT.getList();
		// 将获取的数据放入数组中
		Object[] obs = new Object[8];
		for (int i = 0; i < rps.size(); i++) {
			obs[i] = rps.get(i);
		}

		for (int i = 0; i < rdts.size(); i++) {
			obs[i + rps.size()] = rdts.get(i);
		}

		// 将数组按照时间排序
		// int leng=obs.length;

		int len = rps.size() + rdts.size();
		int i, j;
		Object temp;
		for (i = 1; i <= len; i++) {
			for (j = len - 1; j >= 1; j--) {
				Date t1 = (obs[j] instanceof ResourcePush) ? ((ResourcePush) obs[j]).getCreateTime() : ((ResourceDistributeTarget) obs[j]).getCreateTime();
				Date t2 = (obs[j - 1] instanceof ResourcePush) ? ((ResourcePush) obs[j - 1]).getCreateTime() : ((ResourceDistributeTarget) obs[j - 1]).getCreateTime();
				// if (n2[j] > n2[j - 1]) {
				if (t1.after(t2)) {
					temp = obs[j];
					obs[j] = obs[j - 1];
					obs[j - 1] = temp;
				}
			}
		}
		for (int k = 0; k < 4; k++) {
			if (obs[k] != null) {
				ls.add(obs[k]);
			}
		}
		return ls;

	}
}