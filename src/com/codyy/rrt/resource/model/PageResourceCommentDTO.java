package com.codyy.rrt.resource.model;

import java.util.List;

/**
 * 资源评论分页实体
 * 
 */
public class PageResourceCommentDTO {

	private List<ResourceCommentDTO> data; // 课程详情数据
	private Integer total; // 课程详情数量

	public List<ResourceCommentDTO> getData() {
		return data;
	}

	public void setData(List<ResourceCommentDTO> data) {
		this.data = data;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
