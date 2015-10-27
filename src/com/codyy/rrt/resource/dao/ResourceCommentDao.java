package com.codyy.rrt.resource.dao;

import java.util.List;

import com.codyy.rrt.base.dao.BaseMongoDao;
import com.codyy.rrt.resource.model.ResourceComment;

public interface ResourceCommentDao extends BaseMongoDao<ResourceComment> {
	/**
	 * 添加评论
	 */
	void addComment(final ResourceComment resourceComment);

	List<ResourceComment> getCommentByResId(final String resourceId,
			final Integer start, final Integer end);

	int getTotalCommentCountByResId(final String resourceId);
}
