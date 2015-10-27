package com.codyy.rrt.resource.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.codyy.rrt.base.dao.BaseMongoDaoImpl;
import com.codyy.rrt.commons.utils.RegexUtils;
import com.codyy.rrt.resource.model.ResourcePush;

/**
 * 资源推送Dao
 * 
 * @author kongchuifang
 * @date 2014-11-10
 */
@Repository("resourcePushDao")
public class ResourcePushDaoImpl extends BaseMongoDaoImpl<ResourcePush> implements ResourcePushDao {

	@Override
	public void updateStatusByResourcePushId(String resourcePushId, String status) {
		Criteria criteria = Criteria.where("id").is(resourcePushId);
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("status", status);
		getMongoTemplate().updateFirst(query, update, getModelClass());
	}

	@Override
	public void updateNextResourcePushIdByResourcePushId(String resourcePushId, String nextResourcePushId) {
		Criteria criteria = Criteria.where("id").is(resourcePushId);
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("nextResourcePushId", nextResourcePushId);
		getMongoTemplate().updateFirst(query, update, getModelClass());
	}

	@Override
	public void updateDistributeIdByResourcePushId(String resourcePushId, String distributeId) {
		Criteria criteria = Criteria.where("id").is(resourcePushId);
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("distributeId", distributeId);
		getMongoTemplate().updateFirst(query, update, getModelClass());
	}

	@Override
	public void deleteByResourceId(String resourceId) {
		Criteria criteria = Criteria.where("resourceId").is(resourceId);
		Query query = new Query(criteria);
		getMongoTemplate().remove(query, getModelClass());
	}

	@SuppressWarnings("static-access")
	public Map<String , Object> getGDResourcePush(String semesterId, String classlevelId, String subjectId,
			List<String> columnIds, String isDownLoad, List<String> downLoadList, Date startTime, Date endTime,
			String resourceName, int start, int end) {
		Map<String , Object> _map = new HashMap<String , Object>();
		int page = 0;
		if (start % 8 == 0) {
			page = start / 8;
		} else {
			page = start / 8 + 1;
		}

		Criteria c1 = new Criteria();
		Criteria c2 = new Criteria();
		Criteria c3 = new Criteria();
		Criteria c4 = new Criteria();
		Criteria c5 = new Criteria();
		Criteria c6 = new Criteria();
		Criteria c7 = new Criteria();
		Criteria c8 = new Criteria();
		Criteria c9 = new Criteria();
		Criteria c10 = new Criteria();
		if (StringUtils.isNotBlank(semesterId)) {
			c1 = c1.where("baseSemesterId").is(semesterId);
		}
		if (StringUtils.isNotBlank(classlevelId)) {
			c2 = c2.where("baseClasslevelId").is(classlevelId);
		}
		if (StringUtils.isNotBlank(subjectId)) {
			c3 = c3.where("baseDisciplineId").is(subjectId);
		}
		if (CollectionUtils.isNotEmpty(downLoadList)) {
			if (StringUtils.isNotBlank(isDownLoad)) {
				if (isDownLoad.equals("Y")) {// 已经下载
					c5 = c5.where("resourceId").in(downLoadList);
				} else if (isDownLoad.equals("N")) { // 未下载
					c5 = c5.where("resourceId").nin(downLoadList);
				}
			}
		}
		if (startTime != null) {
			c6 = c6.where("createTime").gte(startTime);
		}
		if (endTime != null) {
			c7 = c7.where("createTime").lte(endTime);
		}
		if (StringUtils.isNotBlank(resourceName)) {
			c8 = Criteria.where("resourceName").regex(".*(" + RegexUtils.filterRegex(resourceName) + ").*");
		}
		c9 = c9.where("reciveType").is("ORG");
		if (CollectionUtils.isNotEmpty(columnIds)) {
			c10 = c10.where("resourceColumnId").in(columnIds);
		}
		Criteria criteria = new Criteria();
		criteria.andOperator(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
		Query query = new Query(criteria);
		query.with(new PageRequest(page, 8, Direction.DESC, "createTime"));
		long total = findCount(criteria);
		List<ResourcePush> list = getMongoTemplate().find(query, ResourcePush.class);
		if(total!=0){
			_map.put("data", list);
		}
		_map.put("total", Long.toString(total));
		return _map;
	}

}
