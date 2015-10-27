package com.codyy.rrt.resource.mapper;

import java.util.List;

import com.codyy.rrt.resource.model.ResourceImage;

/**
 * 资源图片Mapper
 * 
 * @author kongchuifang
 * @date 2014-12-9
 */
public interface ResourceImageMapper {

	/**
	 * 根据资源Id查询资源图片
	 * 
	 * @param resourceId
	 * @return
	 */
	public List<ResourceImage> findByResourceId(String resourceId);

	/**
	 * 添加资源图片
	 * 
	 * @param resourceImage
	 */
	public void saveResourceImage(ResourceImage resourceImage);
}
