package com.codyy.rrt.resource.dao;

import com.codyy.rrt.base.dao.BaseMongoDao;
import com.codyy.rrt.resource.model.ResourceDistribute;

/**
 * 资源分发Dao
 * 
 * @author kongchuifang
 * @date 2014-11-12
 */
public interface ResourceDistributeDao extends BaseMongoDao<ResourceDistribute> {

	/**
	 * 根据资源Id删除
	 * 
	 * @param resourceId
	 */
	public void deleteByResourceId(String resourceId);
}
