package com.codyy.rrt.resource.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codyy.rrt.base.Constant;
import com.codyy.rrt.baseinfo.mapper.ResourceLogMapper;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.commons.utils.OracleKeyWordUtils;
import com.codyy.rrt.commons.utils.UUIDUtils;
import com.codyy.rrt.resource.dao.ResourcePushDao;
import com.codyy.rrt.resource.mapper.ResourceMapper;
import com.codyy.rrt.resource.model.ResourceLog;
import com.codyy.rrt.resource.model.ResourcePush;
import com.codyy.rrt.resource.model.Resources;

/**
 * @author xierongbing
 * @date 2015年7月24日 上午10:29:13 
 * @description service
 */
@Service
public class ResourceLogService {
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	@Autowired
	private ResourceLogMapper resourceLogMapper;
	
	@Autowired
	private ResourcePushDao resourcePushDao;
	
	public int deleteByPrimaryKey(String resourceLogId){
		return resourceLogMapper.deleteByPrimaryKey(resourceLogId);
	}
	
	public int insert(ResourceLog resourceLog){
		return resourceLogMapper.insert(resourceLog);
	}
	
	public int insertSelective(ResourceLog resourceLog){
		return resourceLogMapper.insertSelective(resourceLog);
	}
	
	public ResourceLog selectByPrimaryKey(String resourceLogId){
		return resourceLogMapper.selectByPrimaryKey(resourceLogId);
	}

	public int updateByPrimaryKeySelective(ResourceLog resourceLog){
		return resourceLogMapper.updateByPrimaryKeySelective(resourceLog);
	}
	
	public int updateByPrimaryKey(ResourceLog resourceLog){
		return resourceLogMapper.updateByPrimaryKey(resourceLog);
	}
	
	public void logResource(String id, LoginUser loginUser){
		Resources resources = resourceMapper.findByResourceId(id);
		this.addLogResource(loginUser.getUsername(), Constant.RES_ACTION_RECOMMEND, id , resources.getResourceName());
	}
	
	public void logPush(String id, LoginUser loginUser){
		ResourcePush push = resourcePushDao.findById(id);
		this.logResource(push.getResourceId(), loginUser);
	}
	
	/**
	 * @param userName
	 * @param describe
	 * @param resourceId
	 * @param resourceName
	 * 传入数据
	 */
	public void addLogResource(String userName,String describe,String resourceId,String resourceName){
		ResourceLog resourceLog = new ResourceLog();
		resourceLog.setResourceLogId(UUIDUtils.getUUID());
		resourceLog.setResourceDescribe(describe);
		resourceLog.setResourceId(resourceId);
		resourceLog.setResourceName(resourceName);
		resourceLog.setUserName(userName);
		resourceLogMapper.insert(resourceLog);
	}
	
	
	public Map<String, Object> getResourceLogByCondition(String resourceName,String realName,String strStart,String strEnd){
		int start = 0,end = 19;
		if (StringUtils.isNotBlank(strStart)) {
			start = Integer.parseInt(strStart);
		}
		if (StringUtils.isNotBlank(strEnd)) {
			end = Integer.parseInt(strEnd);
		}
		end++;
		if (StringUtils.isNotBlank(resourceName)) {
			resourceName = OracleKeyWordUtils.oracleKeyWordReplace(resourceName);
		}
		if (StringUtils.isNotBlank(realName)) {
			realName = OracleKeyWordUtils.oracleKeyWordReplace(realName);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("resourceName", resourceName);
		map.put("realName", realName);
		int total = resourceLogMapper.getResourcelogCountByCondition(map);
		Map<String, Object> result = new HashMap<String, Object>();
		if (total != 0) {
			result.put("data", resourceLogMapper.getResourceLogByCondition(map));
		}
		result.put("total", total);
		return result;
	}
	
	public void logResourceDownLoad(String id, LoginUser loginUser){
		//首先判断用户是否下载过该资源
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourceId", id);
		map.put("username", loginUser.getUsername());
		List<ResourceLog> list = resourceLogMapper.getDownLoadResourceLogByIdAndUsername(map);
		if(list.size()==0){
			Resources resources = resourceMapper.findByResourceId(id);
			this.addLogResource(loginUser.getUsername(), Constant.RES_ACTION_DOWNLOAD, id , resources.getResourceName());
		}
	}
}
