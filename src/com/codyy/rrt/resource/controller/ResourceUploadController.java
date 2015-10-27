package com.codyy.rrt.resource.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codyy.base.web.SpringContext;
import com.codyy.rrt.base.Constant;
import com.codyy.rrt.base.util.FileUploadProgressListener;
import com.codyy.rrt.base.util.PathUtil;
import com.codyy.rrt.base.util.PrintScreenHelper;
import com.codyy.rrt.base.util.Progress;
import com.codyy.rrt.base.util.PropertiesUtils;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.model.BaseAttacheFile;
import com.codyy.rrt.resource.model.ResourceColumn;
import com.codyy.rrt.resource.model.Resources;
import com.codyy.rrt.resource.service.ResourceLogService;
import com.codyy.rrt.resource.service.ResourceUserService;
import com.codyy.rrt.resource.service.SkeletonService;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * 上传按钮，进入上传页面
 * 省，市，县
 * 学校
 * 老师
 * 学生
 */
@Controller
@RequestMapping("/front/resUpload/")
public class ResourceUploadController extends BaseController {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ResourceUploadController.class);

	@Autowired
	private ResourceUserService resourceUserService;
	@Autowired
	private SkeletonService skeletonService;
	@Autowired
	private ResourceLogService resourceLogService;

	/**
	 * 上传资源页面，默认为第一个资源栏目
	 * 
	 * @param request
	 * @param response
	 * @param module
	 * @return
	 */
	@RequestMapping("{module}/upload")
	public String uploadPage(HttpServletRequest request, HttpServletResponse response, @PathVariable String module) {
		return toUploadPage(request, module, "");
	}

	/**
	 * 上传资源页面，根据资源栏目Id
	 * 
	 * @param request
	 * @param response
	 * @param module  org，school，teacher，student
	 * @return
	 */
	@RequestMapping("{module}/upload/{resourceColumnId}")
	public String uploadPage(HttpServletRequest request, HttpServletResponse response, @PathVariable String module, @PathVariable String resourceColumnId) {
		return toUploadPage(request, module, resourceColumnId);
	}

	private String toUploadPage(HttpServletRequest request, String module, String resourceColumnId) {
		if (module.equals("school")) {
			request.setAttribute("schoolTag", "yes");
		}
		LoginUser loginUser = getLoginUser(request);
		request.setAttribute("loginUser", loginUser);

		List<ResourceColumn> allResourceColumn = skeletonService.findAllResourceColumn();
		request.setAttribute("allResourceColumn", allResourceColumn);

		ResourceColumn resourceColumn = null;

		//选中哪一个类型
		if (CollectionUtils.isNotEmpty(allResourceColumn)) {
			if (StringUtils.isNotEmpty(resourceColumnId)) {
				for (ResourceColumn resColumn : allResourceColumn) {
					if (resColumn.getId().equals(resourceColumnId)) {
						resourceColumn = resColumn;
						break;
					}
				}
			} else {
				resourceColumn = allResourceColumn.get(0);
			}
		}

		if (resourceColumn == null) {
			return "";
		}

		Long maxUploadSizeHDVideo = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_VIDEO);
		Long maxUploadSizeDocument = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_DOCUMENT);
		Long maxUploadSizePicture = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_PICTURE);
		Long maxUploadSizeThumb = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_THUMB);
		request.setAttribute("maxUploadSizeHDVideo", maxUploadSizeHDVideo);
		request.setAttribute("maxUploadSizeDocument", maxUploadSizeDocument);
		request.setAttribute("maxUploadSizePicture", maxUploadSizePicture);
		request.setAttribute("maxUploadSizeThumb", maxUploadSizeThumb);

		request.setAttribute("resourceColumn", resourceColumn);
		request.setAttribute("resourceColumnId", resourceColumn.getId());
		request.setAttribute("module", module);

		if (Constants.RESOURCE_COLUMN_VIDEO.equals(resourceColumn.getColumnType())) {
			// 视频类
			return "front/resource/upload_top_and_micro";
		} else if (Constants.RESOURCE_COLUMN_DOCUMENT.equals(resourceColumn.getColumnType())) {
			// 文档类
			return "front/resource/upload_study_resource";
		} else if (Constants.RESOURCE_COLUMN_MIXTURE.equals(resourceColumn.getColumnType())) {
			// 混合类
			return "front/resource/upload_fun";
		}
		return "front/common/error/404";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("{module}/addResource")
	public String addResource(HttpServletRequest request, String funType, @PathVariable String module, @RequestParam String resourceName, String description, @RequestParam String resourceColumnId, String knowledgeIds, String funContent, Resources res, String labels, String resourceImages, String thumbSequence) throws NoSuchAlgorithmException, FileNotFoundException {
		if (module.equals("school"))
			request.setAttribute("schoolTag", "yes");
		request.setAttribute("module", module);
		LoginUser loginUser = getLoginUser(request);
		request.setAttribute("loginUser", loginUser);
		boolean error = false;
		List<String> ids = new LinkedList<String>();
		if (StringUtils.isNotBlank(knowledgeIds)) {
			String[] arr = knowledgeIds.split(",");
			for (String str : arr) {
				ids.add(str);
			}
		}
		Resources resource = new Resources();
		resource.setBaseSemesterId(res.getBaseSemesterId()); // 学段Id
		resource.setBaseClasslevelId(res.getBaseClasslevelId()); // 年级Id
		resource.setBaseDisciplineId(res.getBaseDisciplineId()); // 学科Id
		resource.setBaseVersionId(res.getBaseVersionId()); // 版本Id
		resource.setBaseFascicleId(res.getBaseFascicleId()); // 分册Id
		resource.setBaseChapterId(res.getBaseChapterId()); // 章Id
		resource.setBasePartId(res.getBasePartId()); // 节Id
		resource.setAuthor(res.getAuthor()); // 作者
		resource.setResourceCatalogFirstId(res.getResourceCatalogFirstId()); // 一级资源分类
		resource.setResourceCatalogSecondId(res.getResourceCatalogSecondId()); // 二级资源分类

		resource.setThumb(res.getThumb()); // 缩略图
		resource.setResourceName(resourceName);
		resource.setDescription(description);
		resource.setResourceColumnId(resourceColumnId);
		resource.setFunContent(funContent);

		// 增加额外四个属性：县电教馆和市电教馆以及省电教馆方便跨数据库统计
		resource.setSchoolId(loginUser.getSchoolId());
		resource.setDistrictOrgId(loginUser.getXianOrgIdDJG());
		resource.setCityOrgId(loginUser.getShiOrgIdDJG());
		resource.setProvinceOrgId(loginUser.getShengOrgIdDJG());

		// 资源栏目
		ResourceColumn resourceColumn = skeletonService.findResourceColumnById(resourceColumnId);
		if (resourceColumn == null) {
			logger.debug("resourceColumn is null");
			error = true;
			request.setAttribute("error", "参数错误");
			return "front/resource/upload_end";
		}

		PrintScreenHelper helper = (PrintScreenHelper) request.getSession().getAttribute(FileUploadController.SESSION_PRINT_SCREEN);
		request.getSession().removeAttribute(FileUploadController.SESSION_PRINT_SCREEN);
		// 如果缩略图为视频截图
		if (StringUtils.isNotEmpty(thumbSequence)) {
			if (helper != null) {
				String filename = helper.getFullFilename();
				String prefix = filename.substring(0, filename.lastIndexOf('.'));
				File jpgfile = new File(prefix + "." + thumbSequence + ".jpeg");
				if (jpgfile.exists() && jpgfile.isFile()) {
					InputStream input = new FileInputStream(jpgfile);
					// save file
					MongoTemplate template = SpringContext.getBean(MongoTemplate.class);
					DB db = template.getDb();
					GridFS gfs = new GridFS(db, "resourceImages");
					String thumb = createFilename(gfs, jpgfile.getName());
					GridFSInputFile file = gfs.createFile(input, thumb);
					file.save();

					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					resource.setThumb(thumb);

					// 删除其他截图
					for (int i = 1; i <= helper.getPictureCount(); i++) {
						File deleteThumbFile = new File(prefix + "." + i + ".jpeg");
						if (deleteThumbFile.exists()) {
							deleteThumbFile.delete();
						}
					}

					jpgfile.delete();
				} else {
					error = true;
					logger.debug("Print screen sequence file not found:" + prefix + "." + thumbSequence + ".jpeg");
				}
			}
		}

		// 资源栏目类型：视频类、文档类、混合类
		resource.setResourceColumnType(resourceColumn.getColumnType());

		if (resourceColumn.getColumnType().equals(Constants.RESOURCE_COLUMN_MIXTURE)) {
			resource.setFunType(funType);
		}

		List<BaseAttacheFile> images = new ArrayList<BaseAttacheFile>();
		Map<String, Progress> map = (Map<String, Progress>) request.getSession().getAttribute(FileUploadProgressListener.SESSION_PROGRESS_NAME);
		request.getSession().removeAttribute(FileUploadProgressListener.SESSION_PROGRESS_NAME);
		if (map != null) {
			// 选择视频类,混合类(视频)
			if (resourceColumn.getColumnType().equals(Constants.RESOURCE_COLUMN_VIDEO) || (resourceColumn.getColumnType().equals(Constants.RESOURCE_COLUMN_MIXTURE) && funType.equals(Constants.FUN_TYPE_VIDEO))) {
				Progress progressHigh = map.remove("highdefine");
				if (progressHigh != null) {
					resource.setHighDefine(progressHigh.getPhysicalFilename());
				} else {
					logger.debug("highDefine progress is null");
				}

				Progress progressMiddle = map.remove("middledefine");
				if (progressMiddle != null) {
					resource.setNormalDefine(progressMiddle.getPhysicalFilename());
				} else {
					logger.debug("middleDefine progress is null");
				}
				if (progressHigh == null && progressMiddle == null)
					error = true;
				// 文档类型
			} else if (resourceColumn.getColumnType().equals(Constants.RESOURCE_COLUMN_DOCUMENT)) {
				Progress progress = map.remove("studyresource");
				if (progress != null) {
					final String physicalFilename = progress.getPhysicalFilename();
					resource.setStudyResource(physicalFilename);
					resource.setStudyResourceType(buildStudyResourceFileType(physicalFilename));
					String filename = PathUtil.buildPath(physicalFilename);
					File file = new File(filename);
					if (file.exists() && file.isFile()) {
						resource.setStudyResourceSize(file.length());
					}
				} else {
					logger.debug("studyresource progress is null");
					error = true;
				}
				// 混合类图片
			} else if (resourceColumn.getColumnType().equals(Constants.RESOURCE_COLUMN_MIXTURE) && funType.equals(Constants.FUN_TYPE_PICTURE)) {
				if (StringUtils.isEmpty(resourceImages)) {
					logger.debug("resource images is null");
					error = true;
				}
			} else {
				logger.debug("resource type unknown:" + resourceColumnId);
				error = true;
			}
		} else if (resourceColumn.getColumnType().equals(Constants.RESOURCE_COLUMN_MIXTURE) && Constants.FUN_TYPE_TEXT.equals(funType)) {
			logger.debug("add text fun resource");
		} else {
			logger.debug("map is null");
			// error = true;
		}
		if (!error) {
			if (loginUser != null) {
				String holdType = null;
				if (module.equals("student")) {
					holdType = Constants.HOLD_TYPE_STUDENT;
				} else if (module.equals("teacher")) {
					holdType = Constants.HOLD_TYPE_TEACHER;
				} else if (module.equals("school")) {
					holdType = Constants.HOLD_TYPE_SCHOOL;
				} else if (module.equals("org")) {
					holdType = Constants.HOLD_TYPE_ORG;
				}
				if (holdType != null) {
					resourceUserService.addResource(resource, ids, images, loginUser.getUserId(), holdType, labels, resourceImages);
					resourceLogService.addLogResource(loginUser.getUsername(), "上传", resource.getId(), resource.getResourceName());
				}
			} else {
				request.setAttribute("error", "用户未登陆");
			}
		} else {
			request.setAttribute("error", "参数错误");
		}

		return "front/resource/upload_end";
	}
	
	//构建学习资源类型
	private String buildStudyResourceFileType(String fileName) {
		
		String type = "";
		
		if(fileName == null) {
			return type;
		}
		
		int lastIndex = fileName.lastIndexOf(".");
		if(lastIndex < 0) {
			return type;
		}
		
		String suffix = fileName.substring(lastIndex + 1);
		
		if(Constant.XLSX.equalsIgnoreCase(suffix)) {
			type = Constant.EXCEL;
		} else if (Constant.DOC.equalsIgnoreCase(suffix) || Constant.DOCX.equalsIgnoreCase(suffix)) {
			type = Constant.WORD;
		} else if (Constant.PPT.equalsIgnoreCase(suffix) || Constant.PPTX.equalsIgnoreCase(suffix)) {
			type = Constant.PPT;
		} else if (Constant.PDF.equalsIgnoreCase(suffix)) {
			type = Constant.PDF;
		} 
		
		return type;
	}

	// 构建随机的文件名
	private String createFilename(GridFS gfs, String filename) {
		String suffix = "";
		if (StringUtils.isNotBlank(filename)) {
			int p = filename.lastIndexOf('.');
			if (p >= 0) {
				suffix = filename.substring(p);
			}
		}
		String randFilename = UUID.randomUUID().toString() + suffix;
		GridFSDBFile file = gfs.findOne(randFilename);
		if (file == null) {
			logger.debug("generate random image filename:" + randFilename);
			return randFilename;
		} else
			return createFilename(gfs, filename);
	}
}