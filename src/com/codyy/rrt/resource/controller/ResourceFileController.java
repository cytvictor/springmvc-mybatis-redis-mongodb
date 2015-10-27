package com.codyy.rrt.resource.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.codyy.base.web.SpringContext;
import com.codyy.rrt.base.ResultJson;
import com.codyy.rrt.base.util.PathUtil;
import com.codyy.rrt.base.util.PropertiesUtils;
import com.codyy.rrt.base.util.VideoUtil;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.service.ResourceLogService;
import com.codyy.rrt.resource.service.ResourceUserService;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * 资源的下载和上传控制
 * @author Peter
 * 
 */
@Controller
@RequestMapping("/public/")
public class ResourceFileController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ResourceUserService resourceUserService;
	@Autowired
	private ResourceLogService resourceLogService;

	@RequestMapping("downloadVideo")
	public void downloadVideo(HttpServletRequest request, HttpServletResponse response, String filename, String resourceId) throws IOException, NoSuchAlgorithmException {
		if (StringUtils.isNotBlank(filename)) {
			resourceUserService.addResourceDownloadCount(resourceId);
			VideoUtil.getFile(request, response, VideoUtil.buildTransPath(filename));
		} else
			response.sendError(404);
	}

	@RequestMapping("downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String filename, String resourceId) throws IOException, NoSuchAlgorithmException {
		if (StringUtils.isNotBlank(filename)) {
			resourceUserService.addResourceDownloadCount(resourceId);
			VideoUtil.getFile(request, response, PathUtil.buildPath(filename));
		} else
			response.sendError(404);
	}

	/**
	 * 上传文件
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadImage", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response, Writer writer) throws Exception {
		JSONObject json = new JSONObject();

		// resolver upload
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		Long maxUploadSize = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_PICTURE);
		resolver.setMaxUploadSize(maxUploadSize * 1024 * 1024);
		resolver.setServletContext(request.getSession().getServletContext());
		MultipartHttpServletRequest multiRequest = null;
		try {
			multiRequest = resolver.resolveMultipart(request);
		} catch (MaxUploadSizeExceededException e) {
			writer.write(new ResultJson(false, 0, "文件过大").toString());
			logger.debug("multiFile is big");
			return;
		}

		MultipartFile multiFile = null;
		Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			multiFile = entry.getValue();
			break;
		}
		if (multiFile == null || multiFile.getSize() == 0) {
			writer.write(new ResultJson(false, "文件内容为空!").toString());
			logger.debug("multiFile is empty");
			return;
		}

		// save file
		MongoTemplate template = SpringContext.getBean(MongoTemplate.class);
		// MultipartFile multiFile = filemap.values().iterator().next();
		String originalFilename = multiFile.getOriginalFilename().toLowerCase();
		InputStream in = multiFile.getInputStream();
		DB db = template.getDb();
		GridFS gfs = new GridFS(db, "resourceImages");
		String filename = createFilename(gfs, originalFilename);
		GridFSInputFile file = gfs.createFile(in, filename);
		file.save();
		json.put("name", filename);
		json.put("fullName", "ResourceImageServlet/" + filename);

		json.put("result", true);
		writer.write(json.toString());
		logger.debug("transferTo over");
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
