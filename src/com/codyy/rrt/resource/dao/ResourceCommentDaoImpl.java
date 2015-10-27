package com.codyy.rrt.resource.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.codyy.rrt.base.dao.BaseMongoDaoImpl;
import com.codyy.rrt.resource.model.ResourceComment;

@Repository("resourceCommentDao")
public class ResourceCommentDaoImpl extends BaseMongoDaoImpl<ResourceComment>
		implements ResourceCommentDao {

	@Override
	public void addComment(final ResourceComment resourceComment) {
		super.insert(resourceComment);
	}

	public List<ResourceComment> getCommentByResId(final String resourceId,
			final Integer start, final Integer end) {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourceId", resourceId);
		Criteria eqSourceId = super.createCriteria(map);
		final int pageSize = end - start + 1;
		final int pageIndex = start / pageSize + 1;

		final Order order = new Order(Direction.DESC, "createDate");

		final List<Order> orderList = new ArrayList<Order>();

		orderList.add(order);

		final List<ResourceComment> resourceCommentList = super.findListByPage(
				eqSourceId, orderList, pageIndex, pageSize);
		return resourceCommentList;
	}

	public int getTotalCommentCountByResId(final String resourceId) {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourceId", resourceId);
		Criteria eqSourceId = super.createCriteria(map);
		return (int) super.findCount(eqSourceId);
	}

}
