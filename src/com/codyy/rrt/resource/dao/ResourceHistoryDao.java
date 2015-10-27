package com.codyy.rrt.resource.dao;

import java.util.Date;

import com.codyy.rrt.base.dao.BaseMongoDao;
import com.codyy.rrt.resource.model.ResourceHistory;

/**
 * 资源浏览Dao
 * 
 * @author kongchuifang
 * @date 2014-11-10
 */
public interface ResourceHistoryDao extends BaseMongoDao<ResourceHistory> {

	/**
	 * 删除浏览记录
	 * 
	 * @param resourceId
	 * @param baseUserId
	 */
	public void deleteByResourceIdAndUserId(String resourceId, String baseUserId);

	/**
	 * 根据资源id和用户id查询资源浏览记录
	 * 
	 * @param resourceId
	 * @param baseUserId
	 */
	public ResourceHistory findByResourceIdAndUserId(String resourceId,
			String baseUserId);

	/**
	 * 修改浏览时间
	 * 
	 * @param resourceHistoryId
	 * @param createTime
	 */
	public void updateCreateTime(String resourceHistoryId, Date createTime);

	/**
	 * 根据资源Id删除
	 * 
	 * @param resourceId
	 */
	public void deleteByResourceId(String resourceId);
}
