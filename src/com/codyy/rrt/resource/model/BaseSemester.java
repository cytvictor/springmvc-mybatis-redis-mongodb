package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 基础学段
 * 
 * @author kongchuifang
 * @date 2014-11-18
 */
public class BaseSemester extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8120301459307621388L;

	private String semesterName; // 学段名称

	public String getSemesterName() {
		return semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

}
