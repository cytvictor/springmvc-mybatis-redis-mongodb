package com.codyy.rrt.resource.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.codyy.rrt.base.model.BaseModel;

public class ResourceDistribute extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5710392890526463945L;
	private String resourceId; // 资源Id
	private String pusherOrgSource; // 推送机构:学校，省级电教馆，市级电教馆，县级电教馆
	private String pusherType; // 推送类型
	private String pusherId; // 推送Id
	private Date createTime; // 创建时间
	private String status; // 状态
	private Set<ResourceDistributeTarget> resourceDistributeTargets = new HashSet<ResourceDistributeTarget>(
			0);
	private String resourceName; // 冗余资源名称，为了分页查询使用
	private String resourceColumnId; // 冗余资源栏目
	private Date resourceCreateTime; // 冗余资源创建时间
	private String baseSemesterId; // 冗余学段Id，为了分页查询使用
	private String baseClasslevelId; // 冗余年级Id，为了分页查询使用
	private String baseDisciplineId; // 冗余学科Id，为了分页查询使用
	private String resourceCatalogFirstId; // 冗余资源分类一级分类Id，为了分页查询使用
	private String resourceCatalogSecondId; // 冗余资源分类二级分类Id，为了分页查询使用

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getPusherType() {
		return pusherType;
	}

	public void setPusherType(String pusherType) {
		this.pusherType = pusherType;
	}

	public String getPusherId() {
		return pusherId;
	}

	public void setPusherId(String pusherId) {
		this.pusherId = pusherId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<ResourceDistributeTarget> getResourceDistributeTargets() {
		return resourceDistributeTargets;
	}

	public void setResourceDistributeTargets(
			Set<ResourceDistributeTarget> resourceDistributeTargets) {
		this.resourceDistributeTargets = resourceDistributeTargets;
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

	public String getPusherOrgSource() {
		return pusherOrgSource;
	}

	public void setPusherOrgSource(String pusherOrgSource) {
		this.pusherOrgSource = pusherOrgSource;
	}

}
