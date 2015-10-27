package com.codyy.rrt.resource.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.model.PageResourceCommentDTO;
import com.codyy.rrt.resource.service.ResourceCommentService;

/**
 * 资源评论控制器（以及该页面其他功能的控制）
 * 
 */
@Controller
@RequestMapping("front")
public class ResourceCommentController {

	@Autowired
	private ResourceCommentService commentService;

	/**
	 * 添加评论
	 * 
	 * @param response
	 * @param writer
	 * @param resourceId
	 * @param comment
	 * @param baseUserId
	 */
	@RequestMapping("resource/comment/addComment")
	public void addComment(HttpServletRequest request, final Writer writer, final String resourceId, final String comment) {
		LoginUser user = (LoginUser) request.getSession().getAttribute(CommonsConstant.SESSION_LOGIN_USER);
		commentService.addComment(resourceId, comment, user.getUserId());
	}

	/**
	 * 获得评论（分页）
	 * 
	 * @param response
	 * @param writer
	 * @param resourceId
	 * @param start
	 * @param end
	 */
	@RequestMapping("resource/comment/getComment")
	public void getComment(HttpServletResponse response, final Writer writer, final String resourceId, final Integer start, final Integer end) {
		final PageResourceCommentDTO commentList = commentService.getComment(resourceId, start, end);

		try {
			writer.write(JSON.toJSONString(commentList));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("resource/comment/evaluationResource")
	public void evaluationResource(final Writer writer, final String resourceId, final double evaluation) {
		final double result = commentService.updateEvaluation(resourceId, evaluation);
		try {
			writer.write(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
