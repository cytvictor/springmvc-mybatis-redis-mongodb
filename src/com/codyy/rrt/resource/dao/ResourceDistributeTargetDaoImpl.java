package com.codyy.rrt.resource.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.codyy.rrt.base.dao.BaseMongoDaoImpl;
import com.codyy.rrt.resource.model.ResourceDistributeTarget;

/**
 * 资源分发目标Dao
 * 
 * @author kongchuifang
 * @date 2014-11-12
 */
@Repository("resourceDistributeTargetDao")
public class ResourceDistributeTargetDaoImpl extends
		BaseMongoDaoImpl<ResourceDistributeTarget> implements
		ResourceDistributeTargetDao {

	@Override
	public void updateNextResourceDistributeId(
			String resourceDistributeTargetId, String nextResourceDistributeId) {
		Criteria criteria = Criteria.where("id").is(resourceDistributeTargetId);
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("nextResourceDistributeId", nextResourceDistributeId);
		getMongoTemplate().updateFirst(query, update, getModelClass());
	}

	@Override
	public void deleteByResourceId(String resourceId) {
		Criteria criteria = Criteria.where("resourceId").is(resourceId);
		Query query = new Query(criteria);
		getMongoTemplate().remove(query, getModelClass());
	}

}
