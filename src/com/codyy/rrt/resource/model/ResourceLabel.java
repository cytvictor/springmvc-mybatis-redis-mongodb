package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

public class ResourceLabel extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = -6236621514538484034L;
	private String resourceLabelName; // 资源标签名称

	public String getResourceLabelName() {
		return resourceLabelName;
	}

	public void setResourceLabelName(String resourceLabelName) {
		this.resourceLabelName = resourceLabelName;
	}

}
