package com.codyy.rrt.commons.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="keywordConfig")
public class KeywordConfig {
	
	public final static String KEYWORD_STATE_ON = "ON"; // 使用中
	public final static String KEYWORD_STATE_OFF = "OFF"; // 未使用
	
	@Id
	private String id;
	private String keywordName;
	private String state;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

}
