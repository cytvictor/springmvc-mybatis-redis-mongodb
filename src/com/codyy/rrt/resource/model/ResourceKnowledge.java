package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 资源知识
 * 
 * @author kongchuifang
 * @date 2014-11-14
 */
public class ResourceKnowledge extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 8363025108595667870L;

	private String resourceId; // 资源Id
	private String baseSemesterId; // 学段Id
	private String baseDisciplineId; // 学科Id
	private String baseKnowledgeId; // 最后一级知识点Id
	private String firstBaseKnowledgeId; // 一级知识点Id
	private String secondBaseKnowledgeId; // 二级知识点Id
	private String thirdBaseKnowledgeId; // 三级知识点Id
	private String fourthBaseKnowledgeId; // 四级知识点Id
	private String fifthBaseKnowledgeId; // 五级知识点Id
	private String sixthBaseKnowledgeId; // 六级知识点Id

	private String knowledges; // 资源的知识点拼接字符串

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

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

	public String getBaseKnowledgeId() {
		return baseKnowledgeId;
	}

	public void setBaseKnowledgeId(String baseKnowledgeId) {
		this.baseKnowledgeId = baseKnowledgeId;
	}

	public String getFirstBaseKnowledgeId() {
		return firstBaseKnowledgeId;
	}

	public void setFirstBaseKnowledgeId(String firstBaseKnowledgeId) {
		this.firstBaseKnowledgeId = firstBaseKnowledgeId;
	}

	public String getSecondBaseKnowledgeId() {
		return secondBaseKnowledgeId;
	}

	public void setSecondBaseKnowledgeId(String secondBaseKnowledgeId) {
		this.secondBaseKnowledgeId = secondBaseKnowledgeId;
	}

	public String getThirdBaseKnowledgeId() {
		return thirdBaseKnowledgeId;
	}

	public void setThirdBaseKnowledgeId(String thirdBaseKnowledgeId) {
		this.thirdBaseKnowledgeId = thirdBaseKnowledgeId;
	}

	public String getFourthBaseKnowledgeId() {
		return fourthBaseKnowledgeId;
	}

	public void setFourthBaseKnowledgeId(String fourthBaseKnowledgeId) {
		this.fourthBaseKnowledgeId = fourthBaseKnowledgeId;
	}

	public String getFifthBaseKnowledgeId() {
		return fifthBaseKnowledgeId;
	}

	public void setFifthBaseKnowledgeId(String fifthBaseKnowledgeId) {
		this.fifthBaseKnowledgeId = fifthBaseKnowledgeId;
	}

	public String getSixthBaseKnowledgeId() {
		return sixthBaseKnowledgeId;
	}

	public void setSixthBaseKnowledgeId(String sixthBaseKnowledgeId) {
		this.sixthBaseKnowledgeId = sixthBaseKnowledgeId;
	}

	public String getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(String knowledges) {
		this.knowledges = knowledges;
	}

}
