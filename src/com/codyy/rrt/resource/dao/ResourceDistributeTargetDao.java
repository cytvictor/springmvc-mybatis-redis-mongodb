package com.codyy.rrt.resource.dao;

import com.codyy.rrt.base.dao.BaseMongoDao;
import com.codyy.rrt.resource.model.ResourceDistributeTarget;

/**
 * 资源分发目标Dao
 * 
 * @author kongchuifang
 * @date 2014-11-12
 */
public interface ResourceDistributeTargetDao extends
		BaseMongoDao<ResourceDistributeTarget> {

	/**
	 * 修改资源再次分发Id
	 * 
	 * @param resourceDistributeTargetId
	 * @param nextResourceDistributeId
	 */
	public void updateNextResourceDistributeId(
			String resourceDistributeTargetId, String nextResourceDistributeId);

	/**
	 * 根据资源Id删除
	 * 
	 * @param resourceId
	 */
	public void deleteByResourceId(String resourceId);
}
