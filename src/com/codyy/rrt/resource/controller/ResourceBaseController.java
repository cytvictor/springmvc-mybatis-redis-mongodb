package com.codyy.rrt.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.codyy.rrt.resource.service.ResourceDistributeService;
import com.codyy.rrt.resource.service.ResourcePushService;
import com.codyy.rrt.resource.service.ResourceUserService;

/**
 * @author Peter
 */
public class ResourceBaseController extends BaseController {

	private static final long serialVersionUID = 1L;
	// protected static final Map<String, String> typeMap = new HashMap<String,
	// String>();
	//
	// static {
	// typeMap.put("top", Constants.TYPE_RESOURCE_TOP);
	// typeMap.put("micro", Constants.TYPE_RESOURCE_MICRO);
	// typeMap.put("fun", Constants.TYPE_RESOURCE_FUN);
	// typeMap.put("resource", Constants.TYPE_RESOURCE_STUDY_RESOURCE);
	// }

	@Autowired
	protected ResourceUserService resourceUserService;
	@Autowired
	protected ResourcePushService resourcePushService;
	@Autowired
	protected ResourceDistributeService resourceDistributeService;

}