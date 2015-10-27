package com.codyy.rrt.resource.model;

import java.util.Date;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 
 * 资源评论model
 */
public class ResourceComment extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1339863931677212205L;
	private String resourceId; // 资源Id
	private String baseUserId; // 用户Id
	private String comment; // 评论内容
	private Date createDate; // 评论时间

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getBaseUserId() {
		return baseUserId;
	}

	public void setBaseUserId(String baseUserId) {
		this.baseUserId = baseUserId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
