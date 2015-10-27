package com.codyy.rrt.resource.mapper;

import java.util.List;

import com.codyy.rrt.resource.model.ResourceKnowledge;

public interface ResourceKnowledgeMapper {

	/**
	 * 保存资源的知识点
	 * 
	 * @param resourceKnowledge
	 */
	public void saveResourceKnowledge(ResourceKnowledge resourceKnowledge);

	/**
	 * 根据资源Id查询知识点集合
	 * 
	 * @param resourceId
	 * @return
	 */
	public List<ResourceKnowledge> findByResourceId(String resourceId);

	/**
	 * 根据资源Id删除相关知识点
	 * 
	 * @param resourceId
	 */
	public void deleteByResourceId(String resourceId);
}
