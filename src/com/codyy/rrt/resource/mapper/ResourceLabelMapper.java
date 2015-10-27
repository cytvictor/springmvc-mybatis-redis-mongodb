package com.codyy.rrt.resource.mapper;

import java.util.List;
import java.util.Map;

import com.codyy.rrt.resource.model.ResourceLabel;

public interface ResourceLabelMapper {

	/**
	 * 根据名称查询标签
	 * 
	 * @param labelName
	 * @return
	 */
	public ResourceLabel findByLabelName(String labelName);

	/**
	 * 根据资源Id查询资源标签
	 * 
	 * @param resourceId
	 * @return
	 */
	public List<ResourceLabel> findByResourceId(String resourceId);

	/**
	 * 添加资源标签
	 * 
	 * @param resourceLabel
	 */
	public void saveResourceLabel(ResourceLabel resourceLabel);

	/**
	 * 添加资源标签和资源关系
	 * 
	 * @param parMap
	 */
	public void saveResourceLabelResources(Map<String, Object> parMap);

	/**
	 * 根据资源Id删除标签和资源关系
	 * 
	 * @param resourceId
	 */
	public void deleteRLRByResourceId(String resourceId);
}
