package com.codyy.rrt.resource.model;

import java.util.Date;

import com.codyy.rrt.base.model.BaseModel;

public class ResourceDistributeTarget extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4902254615922454771L;
	private String distributeId; // 分发Id
	private String resourceId; // 资源Id
	private String reciveType; // 接受类型
	private String reciveId; // 接受Id
	private String status; // 状态
	private String nextResourceDistributeId; // 下次分发Id
	private Date createTime; // 创建时间
	private String baseUserName;
	private String resourceName; // 冗余资源名称，为了分页查询使用
	private String resourceColumnId; // 冗余资源栏目
	private Date resourceCreateTime; // 冗余资源创建时间
	private String baseSemesterId; // 冗余学段Id，为了分页查询使用
	private String baseClasslevelId; // 冗余年级Id，为了分页查询使用
	private String baseDisciplineId; // 冗余学科Id，为了分页查询使用
	private String resourceCatalogFirstId; // 冗余资源分类一级分类Id，为了分页查询使用
	private String resourceCatalogSecondId; // 冗余资源分类二级分类Id，为了分页查询使用

	private Resources resources; // 依赖资源实体，界面查询使用

	public String getDistributeId() {
		return distributeId;
	}

	public void setDistributeId(String distributeId) {
		this.distributeId = distributeId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getReciveType() {
		return reciveType;
	}

	public void setReciveType(String reciveType) {
		this.reciveType = reciveType;
	}

	public String getReciveId() {
		return reciveId;
	}

	public void setReciveId(String reciveId) {
		this.reciveId = reciveId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNextResourceDistributeId() {
		return nextResourceDistributeId;
	}

	public void setNextResourceDistributeId(String nextResourceDistributeId) {
		this.nextResourceDistributeId = nextResourceDistributeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBaseUserName() {
		return baseUserName;
	}

	public void setBaseUserName(String baseUserName) {
		this.baseUserName = baseUserName;
	}

	public String getBaseSemesterId() {
		return baseSemesterId;
	}

	public void setBaseSemesterId(String baseSemesterId) {
		this.baseSemesterId = baseSemesterId;
	}

	public String getBaseClasslevelId() {
		return baseClasslevelId;
	}

	public void setBaseClasslevelId(String baseClasslevelId) {
		this.baseClasslevelId = baseClasslevelId;
	}

	public String getBaseDisciplineId() {
		return baseDisciplineId;
	}

	public void setBaseDisciplineId(String baseDisciplineId) {
		this.baseDisciplineId = baseDisciplineId;
	}

	public String getResourceCatalogFirstId() {
		return resourceCatalogFirstId;
	}

	public void setResourceCatalogFirstId(String resourceCatalogFirstId) {
		this.resourceCatalogFirstId = resourceCatalogFirstId;
	}

	public String getResourceCatalogSecondId() {
		return resourceCatalogSecondId;
	}

	public void setResourceCatalogSecondId(String resourceCatalogSecondId) {
		this.resourceCatalogSecondId = resourceCatalogSecondId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceColumnId() {
		return resourceColumnId;
	}

	public void setResourceColumnId(String resourceColumnId) {
		this.resourceColumnId = resourceColumnId;
	}

	public Date getResourceCreateTime() {
		return resourceCreateTime;
	}

	public void setResourceCreateTime(Date resourceCreateTime) {
		this.resourceCreateTime = resourceCreateTime;
	}

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

}
