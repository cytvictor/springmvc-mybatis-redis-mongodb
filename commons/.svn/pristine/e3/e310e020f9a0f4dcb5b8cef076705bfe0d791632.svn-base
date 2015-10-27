package com.codyy.rrt.commons.entity;

import java.util.HashMap;
import java.util.Map;


public class BaseOrg{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5939096603053220796L;
	public static final String ORG_TYPE_DJG = "DJG";//电教馆
	public static final String ORG_TYPE_JYJ = "JYJ";//教育局
	
	public static final String LEVEL_PROVINCE = "1";// 省机构
	public static final String LEVEL_CITY = "2";// 市机构
	public static final String LEVEL_DISTRICT = "3";// 县机构
	
	
	public static final String ORG_UNVERIFY = "0" ;// === 下发资源免审核
	public static final String ORG_VERIFY = "1" ;// === 下发资源需要审核
	
	public static final String LOCKED_OPEN = "OPEN";
	public static final String LOCKED_MANAGER = "LOCKEDMANAGER";//锁定此机构超级管理员帐号
	public static final String LOCKED_ALL = "LOCKEDALL";//锁定此机构辖区内所有用户帐号
	
	private String id;
	private String baseDistrictId;//区域ID
	private String baseDistrictName;//区域名称
	private String orgName;//机构名称
	private String orgLevel;//机构级别
	private String orgType;//机构类型
	private String parentOrgId;//父机构
	private String parentOrgName;//父机构名称
	private String contactAddr;//联系地址
	private String contactPhone;//联系电话
	private String contactPerson;
	private String contactEmail;//email
	private String createTime;//创建时间
	private String creatorId;//创建人ID
	private String creatorName;//创建人名称
	private int isverify;//0:不需要  1:需要
	private String locked;//CLOSE:锁定 OPEN:解锁
	
	
	public String getBaseDistrictId() {
		return baseDistrictId;
	}
	public void setBaseDistrictId(String baseDistrictId) {
		this.baseDistrictId = baseDistrictId;
	}
	public String getBaseDistrictName() {
		return baseDistrictName;
	}
	public void setBaseDistrictName(String baseDistrictName) {
		this.baseDistrictName = baseDistrictName;
	}
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
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getParentOrgId() {
		return parentOrgId;
	}
	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}
	public String getParentOrgName() {
		return parentOrgName;
	}
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}
	public String getContactAddr() {
		return contactAddr;
	}
	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public int getIsverify() {
		return isverify;
	}
	public void setIsverify(int isverify) {
		this.isverify = isverify;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	
	private static final Map<String, String> m = new HashMap<String, String>();
	static {
		String [] str = "省 市/直管县 县 学校".split(" ");
		for(int i = 1; i <= str.length; i++) {
			m.put(i + "", str[i - 1]);
		}
		
	}
	
	public static String getOrgLevel(String level) {
		return m.get(level);
	}
	public enum OrgType {
		//教育局、电教馆
		BUREAU("JYJ", "教育局") , EMBASSY("DJG", "电教馆") ;
		
		private OrgType(String typeValue, String typeDescription) {
			this.typeValue = typeValue;
			this.typeDescription = typeDescription;
		}
		private String typeValue;
		private String typeDescription;
		public String getTypeValue() {
			return typeValue;
		}
		public String getTypeDescription() {
			return typeDescription;
		}
		
		public static OrgType getTypeByValue(String value) {
			for(OrgType o : OrgType.values()) {
				if(o.typeValue.equals(value)) {
					return o;
				}
			}
			return null;
		}
		
	}

	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
