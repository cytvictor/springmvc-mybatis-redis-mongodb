package com.codyy.rrt.resource.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.codyy.rrt.base.dao.BaseMongoDaoImpl;
import com.codyy.rrt.resource.model.ResourceHistory;

/**
 * 资源浏览Dao
 * 
 * @author kongchuifang
 * @date 2014-11-10
 */
@Repository("resourceHistoryDao")
public class ResourceHistoryDaoImpl extends BaseMongoDaoImpl<ResourceHistory>
		implements ResourceHistoryDao {

	@Override
	public void deleteByResourceIdAndUserId(String resourceId, String baseUserId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("resourceId").is(resourceId),
				Criteria.where("baseUserId").is(baseUserId));
		Query query = new Query(criteria);
		getMongoTemplate().remove(query, getModelClass());
	}

	@Override
	public ResourceHistory findByResourceIdAndUserId(String resourceId,
			String baseUserId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("resourceId").is(resourceId),
				Criteria.where("baseUserId").is(baseUserId));
		Query query = new Query(criteria);
		List<ResourceHistory> listResourceHistory = getMongoTemplate().find(
				query, getModelClass());

		if (CollectionUtils.isNotEmpty(listResourceHistory)) {
			return listResourceHistory.get(0);
		}
		return null;
	}

	@Override
	public void updateCreateTime(String resourceHistoryId, Date createTime) {
		Criteria criteria = Criteria.where("id").is(resourceHistoryId);
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("status", createTime);
		getMongoTemplate().updateFirst(query, update, getModelClass());
	}

	@Override
	public void deleteByResourceId(String resourceId) {
		Criteria criteria = Criteria.where("resourceId").is(resourceId);
		Query query = new Query(criteria);
		getMongoTemplate().remove(query, getModelClass());
	}

}
