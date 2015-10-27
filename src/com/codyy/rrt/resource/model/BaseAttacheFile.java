package com.codyy.rrt.resource.model;

import com.codyy.rrt.base.model.BaseModel;

public class BaseAttacheFile extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8078187499061418191L;
	private String attacheType;
	private String foreignId;
	private String sourceFilename;
	private String storeFilename;
	private Long fileSize;
	private Short sort;

	public String getAttacheType() {
		return attacheType;
	}

	public void setAttacheType(String attacheType) {
		this.attacheType = attacheType;
	}

	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	public String getSourceFilename() {
		return sourceFilename;
	}

	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}

	public String getStoreFilename() {
		return storeFilename;
	}

	public void setStoreFilename(String storeFilename) {
		this.storeFilename = storeFilename;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

}
