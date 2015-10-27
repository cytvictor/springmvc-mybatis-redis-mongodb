package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

/**
 * 位于基础数据库
 * 
 * 资源栏目
 * 
 * @author kongchuifang
 * @date 2014-11-20
 */
public class ResourceColumn extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4033172477206933951L;
	private String columnName; // 栏目名称
	private String columnType; // 栏目类型
	private String columnTypeLower; // 栏目类型小写字段
	private String baseCatalogKnowledgeFlag; // 是否关联基础分类和知识点标示
	private String resourceCatalogFlag; // 资源分类标示
	private Integer sort; // 排序

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getBaseCatalogKnowledgeFlag() {
		return baseCatalogKnowledgeFlag;
	}

	public void setBaseCatalogKnowledgeFlag(String baseCatalogKnowledgeFlag) {
		this.baseCatalogKnowledgeFlag = baseCatalogKnowledgeFlag;
	}

	public String getResourceCatalogFlag() {
		return resourceCatalogFlag;
	}

	public void setResourceCatalogFlag(String resourceCatalogFlag) {
		this.resourceCatalogFlag = resourceCatalogFlag;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getColumnTypeLower() {
		return columnTypeLower;
	}

	public void setColumnTypeLower(String columnTypeLower) {
		this.columnTypeLower = columnTypeLower;
	}

}
