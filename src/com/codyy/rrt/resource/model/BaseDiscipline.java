package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 学科
 * 
 * @author kongchuifang
 * @date 2014-11-18
 */
public class BaseDiscipline extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -414713728566466585L;

	private String disciplineName; // 学科名称
	private String classlevelId; // 年级Id

	public String getDisciplineName() {
		return disciplineName;
	}

	public void setDisciplineName(String disciplineName) {
		this.disciplineName = disciplineName;
	}

	public String getClasslevelId() {
		return classlevelId;
	}

	public void setClasslevelId(String classlevelId) {
		this.classlevelId = classlevelId;
	}

}
