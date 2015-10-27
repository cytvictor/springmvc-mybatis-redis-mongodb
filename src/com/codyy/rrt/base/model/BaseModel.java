package com.codyy.rrt.base.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseModel implements Serializable {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
