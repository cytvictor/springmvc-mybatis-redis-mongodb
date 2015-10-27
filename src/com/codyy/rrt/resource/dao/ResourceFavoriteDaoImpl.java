package com.codyy.rrt.resource.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.codyy.rrt.base.dao.BaseMongoDaoImpl;
import com.codyy.rrt.resource.model.ResourceFavorite;

/**
 * 资源收藏Dao
 * 
 * @author kongchuifang
 * @date 2014-11-10
 */
@Repository("resourceFavoriteDao")
public class ResourceFavoriteDaoImpl extends BaseMongoDaoImpl<ResourceFavorite>
		implements ResourceFavoriteDao {

	@Override
	public void deleteByResourceIdAndUserId(String resourceId, String baseUserId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("resourceId").is(resourceId),
				Criteria.where("baseUserId").is(baseUserId));
		Query query = new Query(criteria);
		getMongoTemplate().remove(query, getModelClass());
	}

	@Override
	public ResourceFavorite findByResourceIdAndUserId(String resourceId,
			String baseUserId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("resourceId").is(resourceId),
				Criteria.where("baseUserId").is(baseUserId));
		Query query = new Query(criteria);
		List<ResourceFavorite> listResourceFavorite = getMongoTemplate().find(
				query, getModelClass());
		if (CollectionUtils.isNotEmpty(listResourceFavorite)) {
			return listResourceFavorite.get(0);
		}

		return null;
	}
	
	@Override
	public List<ResourceFavorite> listResourceFavorite(String baseUserId){
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("baseUserId").is(baseUserId));
		Query query = new Query(criteria);
		List<ResourceFavorite> listResourceFavorite = getMongoTemplate().find(
				query, getModelClass());		
		return listResourceFavorite;
	}

	@Override
	public void deleteByResourceId(String resourceId) {
		Criteria criteria = Criteria.where("resourceId").is(resourceId);
		Query query = new Query(criteria);
		getMongoTemplate().remove(query, getModelClass());
	}
}
