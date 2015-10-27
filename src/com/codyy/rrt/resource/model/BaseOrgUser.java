package com.codyy.rrt.resource.model;

public class BaseOrgUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6736538958849050487L;
	private String baseOrgUserId;
	private BaseUser baseUser;
	private String position = null;
	private String permission = null;

	public String getBaseOrgUserId() {
		return baseOrgUserId;
	}

	public void setBaseOrgUserId(String baseOrgUserId) {
		this.baseOrgUserId = baseOrgUserId;
	}

	public BaseUser getBaseUser() {
		return baseUser;
	}

	public void setBaseUser(BaseUser baseUser) {
		this.baseUser = baseUser;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public enum Permission {

		message("0", "信息管理"), org("3", "机构管理"), account("1", "帐号管理"), resource(
				"2", "资源中心"), count("4", "统计分析"), online("5", "在线观摩");

		private Permission(String value, String description) {
			this.value = value;
			this.description = description;
		}

		private String value;
		private String description;

		public String getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}

	}
}
