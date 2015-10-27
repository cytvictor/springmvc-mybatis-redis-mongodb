package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

public class BaseSchool extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3094998501606857237L;
	private String schoolName; // 学校名称

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}
