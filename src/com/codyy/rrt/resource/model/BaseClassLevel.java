package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

public class BaseClassLevel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4514630235319197241L;
	private String classLevelName; // 年级名称
	private String baseSemesterId; // 学段Id
	private long sort;

	public String getClassLevelName() {
		return classLevelName;
	}

	public void setClassLevelName(String classLevelName) {
		this.classLevelName = classLevelName;
	}

	public String getBaseSemesterId() {
		return baseSemesterId;
	}

	public void setBaseSemesterId(String baseSemesterId) {
		this.baseSemesterId = baseSemesterId;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

}
