package com.codyy.rrt.resource.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.codyy.rrt.base.util.PathUtil;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.model.Resources;
import com.codyy.rrt.resource.service.ResourceService;

/**
 * @author Peter
 */
@Controller
@RequestMapping("/index/")
public class ResourceIndexController extends ResourceBaseController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ResourceService resourceService;

	@RequestMapping("getwpadinfo")
	public void getwpadinfo(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	}

	@RequestMapping("showdoc")
	public void showdoc(HttpServletRequest request, HttpServletResponse response, @RequestParam String did) throws IOException {
		Resources resource = resourceService.findByResourceId(did);
		if (resource != null && resource.getTransFlag().equals(Constants.TRANS_SUCCESS)) {
			Map<String, String> map = buildInfo(resource);
			response.getWriter().write(JSON.toJSONString(map));
		}
	}

	private Map<String, String> buildInfo(Resources resource) {
		Map<String, String> map = new HashMap<String, String>();
		String filename = resource.getStudyResource();
		String fileType = filename.substring(filename.lastIndexOf('.') + 1);
		map.put("ext", fileType);
		map.put("url", resource.getId().toString() + "//," + resource.getTransPageCount().toString());
		map.put("isshow", "1");
		map.put("current", "0");
		return map;
	}

	@RequestMapping("getpadimg")
	public void getPadimg(HttpServletRequest request, HttpServletResponse response, @RequestParam String urlpath) throws NoSuchAlgorithmException {
		System.out.println("urlpath=" + urlpath);
		String[] arr = urlpath.split("/");
		String id = arr[0];
		String fileNum = arr[1];
		Resources resource = resourceService.findByResourceId(id);
		String filename = resource.getStudyResource();
		String fullFilename = PathUtil.buildPath(filename);
		String prex = fullFilename.substring(0, fullFilename.lastIndexOf('.'));
		String page = prex + File.separatorChar + fileNum;
		File file = new File(page);
		try {
			StreamUtils.copy(new FileInputStream(file), response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}