package com.codyy.rrt.resource.mapper;

import java.util.List;
import java.util.Map;

import com.codyy.rrt.resource.model.Resources;

public interface ResourceMapper {

	/**
	 * 条件分页查询用户上传的资源记录
	 * 
	 * @param parMap
	 * @return
	 */
	public List<Resources> findUploadedResource(Map<String, Object> parMap);

	/**
	 * 条件查询用户上传的资源个数
	 * 
	 * @param parMap
	 * @return
	 */
	public Long countUploadedResource(Map<String, Object> parMap);

	/**
	 * 查询用户上传的资源记录，但不包含用户删除的或管理员删除的，且转换状态是转换成功的，用于访客页面的显示
	 * 
	 * @param parMap
	 * @return
	 */
	public List<Resources> findUploadedResourceExcludeDelete(
			Map<String, Object> parMap);

	/**
	 * 查询用户上传的资源记录，但不包含用户删除的或管理员删除的，且转换状态是转换成功的，用于访客页面的显示
	 * 
	 * @param parMap
	 * @return
	 */
	public Long countUploadedResourceExcludeDelete(Map<String, Object> parMap);

	/**
	 * 保存资源
	 * 
	 * @param resources
	 */
	public void save(Resources resources);

	/**
	 * 修改资源
	 * 
	 * @param resources
	 */
	public void updateResource(Resources resources);

	/**
	 * 修改资源转换状态
	 * 
	 * @param resources
	 */
	public void updateResourceTrans(Resources resources);

	/**
	 * 修改资源评分
	 * 
	 * @param resources
	 */
	public void updateEvaluation(Resources resources);

	/**
	 * 修改资源的相关数量
	 * 
	 * @param parMap
	 */
	public void updateResourceCount(Map<String, Object> parMap);

	/**
	 * 逻辑删除资源
	 * 
	 * @param resource
	 */
	public void deleteResource(Resources resource);

	/**
	 * 根据资源ID查询资源
	 * 
	 * @param resourceId
	 * @return
	 */
	public Resources findByResourceId(String resourceId);

	/**
	 * 根据filename查询视频资源
	 * 
	 * @param fileName
	 * @return
	 */
	public Resources findByVideoFilename(String filename);

	/**
	 * 根据filename查询文档资源
	 * 
	 * @param filename
	 * @return
	 */
	public Resources findByDocumentFilename(String filename);

	/**
	 * 
	 * @param mp4transThread
	 * @return
	 */
	public List<Resources> findVideoTransPendding(Integer mp4transThread);

	/**
	 * 
	 * @param transThread
	 * @return
	 */
	public List<Resources> findStudyResourceTransPendding(Integer transThread);

	/**
	 * 
	 * @return
	 */
	public List<Resources> findStudyResourceTransTransing();

}
