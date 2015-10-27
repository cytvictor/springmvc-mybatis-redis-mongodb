package com.codyy.rrt.resource.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codyy.rrt.base.ResultJson;
import com.codyy.rrt.base.easyui.ResponseUtils;
import com.codyy.rrt.base.util.PropertiesUtils;
import com.codyy.rrt.commons.entity.DomainConfig;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.model.ResourceColumn;
import com.codyy.rrt.resource.model.ResourceImage;
import com.codyy.rrt.resource.model.Resources;
import com.codyy.rrt.resource.service.ResourceService;
import com.codyy.rrt.resource.service.ResourceUserService;
import com.codyy.rrt.resource.service.SkeletonService;

/**
 * 公共资源控制
 * @author Peter
 */
@Controller
@RequestMapping("/front/respub/")
public class ResourcePublicController extends ResourceBaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4945291701385608063L;
	@Autowired
	private SkeletonService skeletonService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ResourceUserService resourceUserService;

	@Autowired
	private DomainConfig domainCfg;

	// 下级推荐的资源审核通过
	@RequestMapping("review/accept")
	public void reviewAccept(HttpServletRequest request, Writer writer, @RequestParam String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			boolean result = resourcePushService.reviewAcceptPush(id, loginUser.getUserId());
			writer.write(ResponseUtils.buildResultJson(result));
			return;
		}
	}

	// 下级推荐的资源审核不通过
	@RequestMapping("review/reject")
	public void reviewReject(HttpServletRequest request, Writer writer, @RequestParam String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			boolean result = resourcePushService.reviewRejectPush(id, loginUser.getUserId());
			writer.write(ResponseUtils.buildResultJson(result));
			return;
		}
	}

	// 用户上传的记录自己删除
	@RequestMapping("edit/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, Writer writer, @RequestParam String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			boolean result = resourceUserService.deleteResource(id, loginUser.getUserId());
			writer.write(ResponseUtils.buildResultJson(result));
			return;
		}
		writer.write(ResponseUtils.buildResultJson(false));
	}

	/**
	 * 修改资源信息，只有未推送或分发的资源可以修改
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("edit/{id}")
	public String toEditPage(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, String schoolTag) {
		request.setAttribute("schoolTag", schoolTag);

		Resources resource = resourceService.findByResourceId(id);
		if (resource == null) {
			return "";
		}

		// 封装知识点和资源标签
		resource = resourceService.packageResourceLabelAndResourceKnowledge(resource);

		LoginUser loginUser = getLoginUser(request);
		request.setAttribute("loginUser", loginUser);

		ResourceColumn resourceColumn = skeletonService.findResourceColumnById(resource.getResourceColumnId());
		request.setAttribute("resource", resource);
		request.setAttribute("resourceColumn", resourceColumn);

		Long maxUploadSizeThumb = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_THUMB);
		request.setAttribute("maxUploadSizeThumb", maxUploadSizeThumb);

		if (Constants.RESOURCE_COLUMN_VIDEO.equals(resourceColumn.getColumnType())) {
			// 视频类
			return "front/resource/edit_top_and_micro";
		} else if (Constants.RESOURCE_COLUMN_DOCUMENT.equals(resourceColumn.getColumnType())) {
			// 文档类
			return "front/resource/edit_study_resource";
		} else if (Constants.RESOURCE_COLUMN_MIXTURE.equals(resourceColumn.getColumnType())) {
			// 混合类
			return "front/resource/edit_fun";
		}

		return "";
	}

	@RequestMapping("editResource")
	public String editResource(HttpServletRequest request, Resources resource, String knowledgeIds, String schoolTag, String thumb) {
		request.setAttribute("schoolTag", schoolTag);

		LoginUser loginUser = getLoginUser(request);
		request.setAttribute("loginUser", loginUser);
		List<String> ids = new LinkedList<String>();
		if (StringUtils.isNotBlank(knowledgeIds)) {
			String[] arr = knowledgeIds.split(",");
			for (String str : arr) {
				ids.add(str);
			}
		}

		Resources oldResource = resourceService.findByResourceId(resource.getId());
		if (oldResource == null) {
			request.setAttribute("error", "资源不存在");
			return "admin/resource/edit_end";
		}
		if (StringUtils.isNotEmpty(thumb)) {
			resource.setThumb(thumb);
		} else {
			resource.setThumb(oldResource.getThumb());
		}

		if (StringUtils.isEmpty(resource.getId())) {
			logger.debug("resourceId is null");
			request.setAttribute("error", "参数传递出错");
		} else {
			if (loginUser != null) {
				boolean b = resourceUserService.editResource(resource, oldResource, ids);
				if (!b) {
					request.setAttribute("error", "修改资源失败");
				}
			} else {
				request.setAttribute("error", "用户未登陆");
			}
		}

		return "front/resource/edit_end";
	}

	// 查看资源
	@RequestMapping("view/{id}")
	public String view(HttpServletRequest request, HttpServletResponse response, Writer writer, @PathVariable String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {
			/*Resources resource = resourceUserService.addResourceViewCount(id,
					loginUser.getUserId());
			ResourceColumn resourceColumn = skeletonService
					.findResourceColumnById(resource.getResourceColumnId());
			resource.setResourceColumn(resourceColumn);
			if (resource != null) {
				StringBuffer buffer = new StringBuffer();
				if (resourceColumn.getColumnType().equals(
						Constants.RESOURCE_COLUMN_VIDEO)
						|| (resourceColumn.getColumnType().equals(
								Constants.RESOURCE_COLUMN_MIXTURE) && resource
								.getFunType().equals(Constants.FUN_TYPE_VIDEO))) {
					buffer.append("时长：");
					int duration = resource.getDuration();
					if (duration <= 60) {
						buffer.append(duration).append("秒");
					} else {
						buffer.append(duration / 60).append("分")
								.append(duration % 60).append("秒");
					}
				} else if (resourceColumn.getColumnType().equals(
						Constants.RESOURCE_COLUMN_DOCUMENT)) {
					buffer.append("大小：");
					Long size = resource.getStudyResourceSize();
					if (size != null) {
						if (size < 1024 * 1024) {
							buffer.append(size / 1024 + "K");
						} else {
							buffer.append(size / 1024 / 1024 + "M");
						}
					}
				} else if (resourceColumn.getColumnType().equals(
						Constants.RESOURCE_COLUMN_MIXTURE)
						&& resource.getFunType().equals(
								Constants.FUN_TYPE_PICTURE)) {
					buffer.append("图片：");
					List<ResourceImage> images = resourceUserService
							.findResourceImageByResourceId(resource.getId());
					buffer.append(images.size() + "张");
					request.setAttribute("images", images);
				}
				request.setAttribute("duration", buffer.toString());
				request.setAttribute("resource", resource);
				request.setAttribute("user", getLoginUser(request));
				
				String str = request.getHeader("User-Agent");
				str = str.toLowerCase();
				if(str.indexOf("android")>0 || str.indexOf("ipad")>0 || str.indexOf("macintosh")>0 || str.indexOf("iphone")>0){
					if(resource.getTransferFlag().equals("TRANS_SUCCESS") && (resource.getResourceColumn().getColumnType().equals("VIDEO") || (resource.getResourceColumn().getColumnType().equals("MIXTURE") && resource.getFunType().equals("FUN_TYPE_VIDEO")))){
						return "redirect:/Video/"+resource.getNormalDefine();
					}
				}*/
			String url = domainCfg.getResourcesearch() + "/front/comment/comment.html?resourceId=" + id;
			return "redirect:" + url;
			// return "front/resource/view";
			// }
		}
		return null;
	}

	// 用户收藏资源
	@RequestMapping("favorite")
	public void favorite(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) throws IOException {
		LoginUser loginUser = getLoginUser(request);
		if (loginUser != null) {// 检查用户是否已经登录
			int r = resourceUserService.favoriteResource(id, loginUser.getUserId());
			if (r == 0)
				response.getWriter().write(new ResultJson(false, r, "此资源已经在您的收藏夹中").toString());
			else if (r == -1)
				response.getWriter().write(new ResultJson(false, r, "错误, 资源或用户不存在").toString());
			else if (r == 1)
				response.getWriter().write(new ResultJson(false, r, "收藏成功").toString());
		} else {
			response.getWriter().write(new ResultJson(false, 2, "用户未登录").toString());
		}
	}
}