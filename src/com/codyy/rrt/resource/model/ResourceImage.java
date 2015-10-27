package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 资源的图片
 * 
 * @author kongchuifang
 * @date 2014-12-9
 */
public class ResourceImage extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceId; // 资源Id
	private String imageName; // 资源图片名称

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
