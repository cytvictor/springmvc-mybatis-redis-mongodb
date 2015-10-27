package com.codyy.rrt.resource.model;

import java.text.SimpleDateFormat;

/**
 * 
 * 资源评论model
 */
public class ResourceCommentDTO {
	
	private String resourceId; // 资源Id
	private String baseUserId; // 用户Id
	private String baseUserName;//用户名
	private String headPic;//用户头像
	private String comment; // 评论内容
	private String createDate; // 评论时间

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

	public String getBaseUserName() {
		return baseUserName;
	}

	public void setBaseUserName(String baseUserName) {
		this.baseUserName = baseUserName;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateDate() {
		return createDate;
	}
	
	/**
	 * 将ResourceComment的数据装到该实体中
	 * @param comment
	 */
	public void copyValueFromComment(ResourceComment comment) {
		this.resourceId = comment.getResourceId();
		this.baseUserId = comment.getBaseUserId();
		this.comment = comment.getComment();
		final SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm ");
		this.createDate = sdf.format(comment.getCreateDate());
	}

}
