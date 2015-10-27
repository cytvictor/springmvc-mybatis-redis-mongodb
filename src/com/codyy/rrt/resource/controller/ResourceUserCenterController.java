package com.codyy.rrt.resource.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.codyy.rrt.base.Page;
import com.codyy.rrt.base.Pageable;
import com.codyy.rrt.base.easyui.RequestUtils;
import com.codyy.rrt.base.easyui.ResponseUtils;
import com.codyy.rrt.base.util.DateUtils;
import com.codyy.rrt.base.util.PropertyUtils;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.model.ResourceColumn;
import com.codyy.rrt.resource.model.ResourceDistribute;
import com.codyy.rrt.resource.model.ResourceDistributeTarget;
import com.codyy.rrt.resource.model.ResourceFavorite;
import com.codyy.rrt.resource.model.ResourceHistory;
import com.codyy.rrt.resource.model.ResourcePush;
import com.codyy.rrt.resource.model.Resources;
import com.codyy.rrt.resource.service.SkeletonService;

/**
 * 资源访问首页 老师 学生
 */
@Controller
@RequestMapping("/front/resource/")
public class ResourceUserCenterController extends ResourceBaseController {

	private static final long serialVersionUID = 1L;

	@Resource
	private SkeletonService skeletonService;

	private static final Set<String> moduleSet = new HashSet<String>();
	static {
		moduleSet.add("uploaded");// 上传资源页面
		moduleSet.add("favorite");// 我的浏览
		moduleSet.add("history");// 我的收藏
		moduleSet.add("recommend"); // 学生的推荐资源
		moduleSet.add("schoolpush"); // 学校推荐给老师
		moduleSet.add("studentpush"); // 学生推荐给老师
	}

	// 浏览页面
	@RequestMapping("{module}")
	public String list(HttpServletRequest request, HttpServletResponse response, @PathVariable String module) {
		return toListPage(request, module, "");
	}

	// 浏览页面
	@RequestMapping("{module}/{id}")
	public String list(HttpServletRequest request, HttpServletResponse response, @PathVariable String module, @PathVariable String id) {
		return toListPage(request, module, id);
	}

	private String toListPage(HttpServletRequest request, String module, String id) {
		if (moduleSet.contains(module)) {
			LoginUser loginUser = getLoginUser(request);
			// 只有学生或老师才能访问
			if (!CommonsConstant.USER_TYPE_STUDENT.equals(loginUser.getUserType()) && !CommonsConstant.USER_TYPE_TEACHER.equals(loginUser.getUserType())) {
				return "front/resource/no_permission";
			}

			request.setAttribute("loginUser", loginUser);
			request.setAttribute("resourceUrl", "resource"); // 资源根Url
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
			request.setAttribute("resourceColumnId", id); // 资源栏目Id
			return "front/resource/condition";
		}
		return "front/common/error/404";
	}

	/**
	 * 资源内容查询页面
	 * 
	 * @param request
	 * @param response
	 * @param module
	 * @param resourceColumnId
	 * @param semesterId
	 * @param classlevelId
	 * @param disciplineId
	 * @param resourceCatalogFirstId
	 * @param resourceCatalogSecondId
	 * @param startTime
	 * @param endTime
	 * @param resourceName
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@RequestMapping("{module}/content")
	public String content(HttpServletRequest request, HttpServletResponse response, @PathVariable String module, String resourceColumnId, String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String startTime, String endTime, String resourceName) throws UnsupportedEncodingException, ParseException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		LoginUser loginUser = getLoginUser(request);
		// 只有学生或老师才能访问
		if (!CommonsConstant.USER_TYPE_STUDENT.equals(loginUser.getUserType()) && !CommonsConstant.USER_TYPE_TEACHER.equals(loginUser.getUserType())) {
			return "front/resource/no_permission";
		}

		request.setAttribute("loginUser", loginUser);
		resourceName = resourceName.trim();
		if (moduleSet.contains(module)) {
			String userId = loginUser.getUserId();
			Page page = RequestUtils.buildPage(request);
			page.setPageCountData(6);
			if (userId != null) {
				Date st = null;
				Date et = null;
				if (StringUtils.isNotBlank(startTime)) {
					st = DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm");
				}
				if (StringUtils.isNotBlank(endTime)) {
					et = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm");
				}
				if (module.equals("uploaded")) {
					Pageable<Resources> pageable = null;
					if (loginUser.getUserType().equals(CommonsConstant.USER_TYPE_STUDENT))
						pageable = resourceUserService.findUploadedResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_STUDENT, userId, page);
					else if (loginUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER))
						pageable = resourceUserService.findUploadedResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_TEACHER, userId, page);
					String pageStr = ResourcePageUtil.buildPageInfo(pageable);
					request.setAttribute("list", pageable.getList());
					request.setAttribute("pageStr", pageStr);
					// 如果是学生，检查学生的资源推送状态
					if (loginUser.getUserType().equals(CommonsConstant.USER_TYPE_STUDENT)) {
						List<String> ids = PropertyUtils.getProperties(pageable.getList(), "id");
						List<ResourcePush> pushs = resourcePushService.findPushPusherRecord(Constants.HOLD_TYPE_STUDENT, loginUser.getUserId(), ids);
						Map<String, ResourcePush> pushMap = new HashMap<String, ResourcePush>();
						for (ResourcePush push : pushs) {
							pushMap.put(push.getResourceId(), push);
						}
						request.setAttribute("pushMap", pushMap);
						// 如果是老师，检查资源的推送状态和下发状态
					} else if (loginUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
						List<String> ids = PropertyUtils.getProperties(pageable.getList(), "id");
						// 推送状态
						List<ResourcePush> pushs = resourcePushService.findPushPusherRecord(Constants.HOLD_TYPE_TEACHER, loginUser.getUserId(), ids);
						Map<String, ResourcePush> pushMap = new HashMap<String, ResourcePush>();
						for (ResourcePush push : pushs) {
							pushMap.put(push.getResourceId(), push);
						}
						request.setAttribute("pushMap", pushMap);
						// 下发状态
						List<ResourceDistribute> distributes = resourceDistributeService.findDistributeRecord(Constants.HOLD_TYPE_TEACHER, loginUser.getUserId(), ids);
						Map<String, ResourceDistribute> distributeMap = new HashMap<String, ResourceDistribute>();
						for (ResourceDistribute distribute : distributes) {
							distributeMap.put(distribute.getResourceId(), distribute);
						}
						request.setAttribute("distributeMap", distributeMap);
					}
				} else if (module.equals("favorite")) {
					Pageable<ResourceFavorite> pageable = resourceUserService.findFavoriteResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, userId, page);
					String pageStr = ResourcePageUtil.buildPageInfo(pageable);
					request.setAttribute("list", pageable.getList());
					request.setAttribute("pageStr", pageStr);
				} else if (module.equals("history")) {
					Pageable<ResourceHistory> pageable = resourceUserService.findHistoryResource(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, userId, page);
					String pageStr = ResourcePageUtil.buildPageInfo(pageable);
					request.setAttribute("list", pageable.getList());
					request.setAttribute("pageStr", pageStr);
				} else if (module.equals("recommend") && loginUser.getUserType().equals(CommonsConstant.USER_TYPE_STUDENT)) {
					Pageable<ResourceDistributeTarget> pageable = resourceDistributeService.findDistributeTargetRecord(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_STUDENT, userId, page);
					String pageStr = ResourcePageUtil.buildPageInfo(pageable);
					request.setAttribute("list", pageable.getList());
					request.setAttribute("pageStr", pageStr);
				} else if (module.equals("studentpush") && loginUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
					Pageable<ResourcePush> pageable = resourcePushService.findPushReciveRecord(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_TEACHER, userId, page);
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
				} else if (module.equals("schoolpush") && loginUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
					Pageable<ResourceDistributeTarget> pageable = resourceDistributeService.findDistributeTargetRecord(semesterId, classlevelId, disciplineId, resourceCatalogFirstId, resourceCatalogSecondId, resourceName, resourceColumnId, st, et, Constants.HOLD_TYPE_TEACHER, userId, page);
					String pageStr = ResourcePageUtil.buildPageInfo(pageable);
					request.setAttribute("list", pageable.getList());
					request.setAttribute("pageStr", pageStr);
				}
			}
			request.setAttribute("resourceUrl", "resource"); // 资源根Url
			request.setAttribute("module", module);
		}
		return "front/resource/usercenter/center_" + module;
	}

	// 取消收藏
	@RequestMapping("{module}/cancelFavorite")
	public void cancelFavorite(HttpServletRequest request, HttpServletResponse response, Writer writer, @PathVariable String module, @RequestParam String id) throws IOException {
		if (moduleSet.contains(module)) {
			LoginUser loginUser = getLoginUser(request);
			if (loginUser != null) {
				boolean result = resourceUserService.cancelFavorite(id, loginUser.getUserId());
				writer.write(ResponseUtils.buildResultJson(result));
				return;
			}
		}
		writer.write(ResponseUtils.buildResultJson(false));
	}

	// 删除浏览历史记录
	@RequestMapping("{module}/deleteHistory")
	public void deleteHistory(HttpServletRequest request, HttpServletResponse response, Writer writer, @PathVariable String module, @RequestParam String id) throws IOException {
		if (moduleSet.contains(module)) {
			LoginUser loginUser = getLoginUser(request);
			if (loginUser != null) {
				boolean result = resourceUserService.deleteHistory(id, loginUser.getUserId());
				writer.write(ResponseUtils.buildResultJson(result));
				return;
			}
		}
		writer.write(ResponseUtils.buildResultJson(false));
	}

	@RequestMapping("favorite/countResouce")
	/* @ResponseBody */
	public void countResouce(HttpServletRequest request, HttpServletResponse response, Writer writer, String callback) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		List<ResourceFavorite> li = resourceUserService.listResourceFavorite(loginUser.getUserId());
		String cnt = "0";
		if (li != null) {
			cnt = String.valueOf(li.size());
		}
		writer.write(callback + "(" + cnt + ")");
		/* return cnt; */
	}

}