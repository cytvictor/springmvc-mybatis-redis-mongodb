package com.codyy.rrt.resource.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.codyy.rrt.base.util.MinuteDateSerializer;

public class ResourceLog {
    private String resourceLogId;
    private String resourceDescribe;
    private String resourceId;
    private Date visitTime;;
    private String userName;
    private String resourceName;
    private String realName;
    
    public String getResourceLogId() {
        return resourceLogId;
    }

    public void setResourceLogId(String resourceLogId) {
        this.resourceLogId = resourceLogId == null ? null : resourceLogId.trim();
    }

    public String getResourceDescribe() {
        return resourceDescribe;
    }

    public void setResourceDescribe(String resourceDescribe) {
        this.resourceDescribe = resourceDescribe == null ? null : resourceDescribe.trim();
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    @JsonSerialize(using=MinuteDateSerializer.class)
    public Date getVisitTime() {
 		return visitTime;
 	}

 	public void setVisitTime(Date visitTime) {
 		this.visitTime = visitTime;
 	}
    
	@Override
	public String toString() {
		return "ResourceLog [resourceLogId=" + resourceLogId
				+ ", resourceDescribe=" + resourceDescribe + ", resourceId="
				+ resourceId + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}