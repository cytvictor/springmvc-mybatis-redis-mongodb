package com.codyy.rrt.resource.model;

import java.util.Date;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 浏览记录实体
 * 
 * @author kongchuifang
 * @date 2014-11-10
 */
public class ResourceHistory extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4290960362766118547L;
	private String resourceId; // 资源ID
	private String baseUserId; // 用户ID
	private Date createTime; // 创建时间
	private String resourceName; // 冗余资源名称，为了分页查询使用
	private String resourceColumnId; // 冗余资源栏目
	private Date resourceCreateTime; // 冗余资源创建时间
	private String baseSemesterId; // 冗余学段Id，为了分页查询使用
	private String baseClasslevelId; // 冗余年级Id，为了分页查询使用
	private String baseDisciplineId; // 冗余学科Id，为了分页查询使用
	private String resourceCatalogFirstId; // 冗余资源分类一级分类Id，为了分页查询使用
	private String resourceCatalogSecondId; // 冗余资源分类二级分类Id，为了分页查询使用

	private Resources resources; // 资源实体，查询给界面使用

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getBaseUserId() {
		return baseUserId;
	}

	public void setBaseUserId(String baseUserId) {
		this.baseUserId = baseUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
