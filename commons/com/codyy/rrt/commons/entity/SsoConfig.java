package com.codyy.rrt.commons.entity;

public class SsoConfig {

	private String appDomain;
	private String appRedirect;
	private Integer appSessionTimeout;
	
	public String getAppDomain() {
		return appDomain;
	}
	public void setAppDomain(String appDomain) {
		this.appDomain = appDomain;
	}
	public String getAppRedirect() {
		return appRedirect;
	}
	public void setAppRedirect(String appRedirect) {
		this.appRedirect = appRedirect;
	}
	public Integer getAppSessionTimeout() {
		return appSessionTimeout;
	}
	public void setAppSessionTimeout(Integer appSessionTimeout) {
		this.appSessionTimeout = appSessionTimeout;
	}
}
