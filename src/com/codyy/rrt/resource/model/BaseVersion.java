package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 版本
 * 
 * @author kongchuifang
 * @date 2014-11-18
 */
public class BaseVersion extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2939154774663040674L;

	private String name; // 版本名称
	private String disciplineId; // 学科Id
	private String classlevelId; // 年级Id

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(String disciplineId) {
		this.disciplineId = disciplineId;
	}

	public String getClasslevelId() {
		return classlevelId;
	}

	public void setClasslevelId(String classlevelId) {
		this.classlevelId = classlevelId;
	}

}
