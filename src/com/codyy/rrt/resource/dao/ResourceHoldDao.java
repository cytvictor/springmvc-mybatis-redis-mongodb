package com.codyy.rrt.resource.dao;

import com.codyy.rrt.base.dao.BaseMongoDao;
import com.codyy.rrt.resource.model.ResourceHold;

/**
 * 资源持有Dao
 * 
 * @author kongchuifang
 * @date 2014-11-10
 */
public interface ResourceHoldDao extends BaseMongoDao<ResourceHold> {

	/**
	 * 根据资源Id删除
	 * 
	 * @param resourceId
	 */
	public void deleteByResourceId(String resourceId);
}
