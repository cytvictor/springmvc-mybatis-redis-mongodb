package com.codyy.rrt.commons.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="sysConfig")
public class SysConfig {
	public final static String SYSCONFIG_KEYWORD = "KEYWORD"; // 关键字主键
	public final static String SYSCONFIG_IP = "IP"; // ip白名单主键
	public final static String KEYWORD_STATE_OPEN = "OPEN"; // 使用中
	public final static String KEYWORD_STATE_CLOSE = "CLOSE"; // 未使用
	
	@Id
	private String id;
	private String state;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
