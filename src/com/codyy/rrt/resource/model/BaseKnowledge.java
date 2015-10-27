package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 知识点
 * 
 * @author kongchuifang
 * @date 2014-11-18
 */
public class BaseKnowledge extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8428375777807178927L;
	private String baseSemesterId; // 学段Id
	private String baseDisciplineId; // 学科Id
	private String knowledgeName; // 知识点名称
	private String parentId; // 父级Id

	public String getBaseSemesterId() {
		return baseSemesterId;
	}

	public void setBaseSemesterId(String baseSemesterId) {
		this.baseSemesterId = baseSemesterId;
	}

	public String getBaseDisciplineId() {
		return baseDisciplineId;
	}

	public void setBaseDisciplineId(String baseDisciplineId) {
		this.baseDisciplineId = baseDisciplineId;
	}

	public String getKnowledgeName() {
		return knowledgeName;
	}

	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
