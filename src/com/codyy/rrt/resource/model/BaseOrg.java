package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

public class BaseOrg extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5618342596453824681L;
	private String orgName; // 机构名称
	private String orgLevel; // 机构级别
	private String isVerify; // 是否免审核

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getIsVerify() {
		return isVerify;
	}

	public void setIsVerify(String isVerify) {
		this.isVerify = isVerify;
	}

}
