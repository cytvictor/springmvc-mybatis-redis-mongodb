package com.codyy.rrt.resource.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codyy.rrt.baseinfo.mapper.BaseUserMapper;
import com.codyy.rrt.resource.dao.ResourceCommentDao;
import com.codyy.rrt.resource.mapper.ResourceMapper;
import com.codyy.rrt.resource.model.BaseUser;
import com.codyy.rrt.resource.model.PageResourceCommentDTO;
import com.codyy.rrt.resource.model.ResourceComment;
import com.codyy.rrt.resource.model.ResourceCommentDTO;
import com.codyy.rrt.resource.model.Resources;

@Service("resourceCommentService")
public class ResourceCommentService {

	@Autowired
	private ResourceCommentDao commentDao;
	@Autowired
	private BaseUserMapper baseUserMapper;
	@Autowired
	private ResourceMapper resourceMapper;

	public void addComment(final String resourceId, final String comment,
			final String baseUserId) {
		final ResourceComment resourceComment = new ResourceComment();
		resourceComment.setResourceId(resourceId);
		resourceComment.setComment(comment);
		resourceComment.setBaseUserId(baseUserId);
		resourceComment.setCreateDate(new Date());
		commentDao.addComment(resourceComment);
	}

	public PageResourceCommentDTO getComment(final String resourceId,
			final Integer start, final Integer end) {

		final PageResourceCommentDTO page = new PageResourceCommentDTO();

		final List<ResourceComment> commentList = commentDao.getCommentByResId(
				resourceId, start, end);

		final int totalCommentCount = commentDao
				.getTotalCommentCountByResId(resourceId);

		final List<ResourceCommentDTO> dtoList = transferCommentToDTO(commentList);

		page.setData(dtoList);

		page.setTotal(totalCommentCount);

		return page;
	}

	/*
	 * 根据resourceComment创建dto
	 */
	private List<ResourceCommentDTO> transferCommentToDTO(
			List<ResourceComment> commentList) {
		final List<ResourceCommentDTO> dtoList = new ArrayList<ResourceCommentDTO>();

		if (CollectionUtils.isEmpty(commentList)) {
			return dtoList;
		}

		final Iterator<ResourceComment> it = commentList.iterator();

		while (it.hasNext()) {
			final ResourceComment comment = it.next();
			final ResourceCommentDTO dto = new ResourceCommentDTO();
			dto.copyValueFromComment(comment);

			final String userId = comment.getBaseUserId();

			BaseUser user = baseUserMapper.findByBaseUserId(userId);

			final String userName = user.getUsername();
			final String headPic = user.getHeadPic();

			dto.setBaseUserName(userName);
			dto.setHeadPic(headPic);

			dtoList.add(dto);
		}

		return dtoList;
	}

	/**
	 * 修改评分
	 * 
	 * @param resources
	 */
	public double updateEvaluation(String resourceId, double evaluation) {
		Resources resource = resourceMapper.findByResourceId(resourceId);
		resource.setEvaluateCount(resource.getEvaluateCount() + 1);
		resource.setEvaluate(resource.getEvaluate() + evaluation);
		final DecimalFormat df = new DecimalFormat("#.0");

		double evaluateAvg = Double.parseDouble(df.format(resource
				.getEvaluate() / resource.getEvaluateCount()));
		resource.setEvaluateAvg(evaluateAvg);
		resourceMapper.updateEvaluation(resource);
		return evaluateAvg;
	}
}
