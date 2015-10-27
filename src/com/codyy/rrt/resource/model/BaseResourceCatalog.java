package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 位于基础数据库
 * 
 * 资源分类
 * 
 * @author kongchuifang
 * @date 2014-11-20
 */
public class BaseResourceCatalog extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2542827845886312752L;
	private String catalogName; // 资源分类名称
	private String catalogLevel; // 资源分类级别
	private String parentId; // 父级分类Id
	private String resourceColumnId; // 资源栏目Id

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getCatalogLevel() {
		return catalogLevel;
	}

	public void setCatalogLevel(String catalogLevel) {
		this.catalogLevel = catalogLevel;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getResourceColumnId() {
		return resourceColumnId;
	}

	public void setResourceColumnId(String resourceColumnId) {
		this.resourceColumnId = resourceColumnId;
	}

}
