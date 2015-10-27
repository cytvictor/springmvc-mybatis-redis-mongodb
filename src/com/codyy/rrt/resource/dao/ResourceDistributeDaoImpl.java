package com.codyy.rrt.resource.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.codyy.rrt.base.dao.BaseMongoDaoImpl;
import com.codyy.rrt.resource.model.ResourceDistribute;

/**
 * 资源分发Dao
 * 
 * @author kongchuifang
 * @date 2014-11-12
 */
@Repository("resourceDistributeDao")
public class ResourceDistributeDaoImpl extends
		BaseMongoDaoImpl<ResourceDistribute> implements ResourceDistributeDao {

	@Override
	public void deleteByResourceId(String resourceId) {
		Criteria criteria = Criteria.where("resourceId").is(resourceId);
		Query query = new Query(criteria);
		getMongoTemplate().remove(query, getModelClass());
	}

}
