package com.codyy.rrt.resource.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.codyy.rrt.base.dao.BaseMongoDaoImpl;
import com.codyy.rrt.resource.model.ResourceHold;

/**
 * 资源持有Dao
 * 
 * @author kongchuifang
 * @date 2014-11-10
 */
@Repository("resourceHoldDao")
public class ResourceHoldDaoImpl extends BaseMongoDaoImpl<ResourceHold>
		implements ResourceHoldDao {

	@Override
	public void deleteByResourceId(String resourceId) {
		Criteria criteria = Criteria.where("resourceId").is(resourceId);
		Query query = new Query(criteria);
		getMongoTemplate().remove(query, getModelClass());
	}
}
