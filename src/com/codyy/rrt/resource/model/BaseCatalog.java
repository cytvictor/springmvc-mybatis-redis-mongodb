package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 分册、章、节
 * 
 * @author kongchuifang
 * @date 2014-11-18
 */
public class BaseCatalog extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2900987666625083971L;
	private String catalogName; // 名称
	private String parentId; // 所属Id

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
