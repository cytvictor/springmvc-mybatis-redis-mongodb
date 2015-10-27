package com.codyy.rrt.baseinfo.mapper;

import java.util.List;
import java.util.Map;

import com.codyy.rrt.resource.model.ResourceLog;

public interface ResourceLogMapper {
    int deleteByPrimaryKey(String resourceLogId);

    int insert(ResourceLog record);

    int insertSelective(ResourceLog record);

    ResourceLog selectByPrimaryKey(String resourceLogId);

    int updateByPrimaryKeySelective(ResourceLog record);

    int updateByPrimaryKey(ResourceLog record);
    
    /**
     * 获取资源日志
     * @param map(resourceName,realName,start,end)
     * @return
     */
    List<ResourceLog> getResourceLogByCondition(Map<String, Object> map);
    
    /**
     * 资源日志统计
     * @param map
     * @return
     */
    int getResourcelogCountByCondition(Map<String, Object> map);
    
    /**
     * 获取用户下载了哪些资源
     * @return
     */
    List<String> getDownLoadResourceLog(String username);
    
    /**
     * 判断用户是否下载过某个资源
     * @param map
     * @return
     */
    List<ResourceLog> getDownLoadResourceLogByIdAndUsername(Map<String, Object> map);
}