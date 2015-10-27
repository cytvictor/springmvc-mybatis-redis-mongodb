package com.codyy.rrt.resource.dao;

import java.util.List;

import com.codyy.rrt.base.dao.BaseMongoDao;
import com.codyy.rrt.resource.model.ResourceFavorite;

/**
 * 资源收藏Dao
 * 
 * @author kongchuifang
 * @date 2014-11-10
 */
public interface ResourceFavoriteDao extends BaseMongoDao<ResourceFavorite> {

	/**
	 * 删除收藏记录
	 * 
	 * @param resourceId
	 * @param baseUserId
	 */
	public void deleteByResourceIdAndUserId(String resourceId, String baseUserId);

	/**
	 * 根据资源id和用户id查询资源收藏
	 * 
	 * @param resourceId
	 * @param baseUserId
	 */
	public ResourceFavorite findByResourceIdAndUserId(String resourceId,
			String baseUserId);

	/**
	 * 根据资源Id删除
	 * 
	 * @param resourceId
	 */
	public void deleteByResourceId(String resourceId);
	
	
	/**
	 * 根据指定用户ID获取相关的收藏个数
	 */
	public List<ResourceFavorite> listResourceFavorite(String baseUserId);
	
}
