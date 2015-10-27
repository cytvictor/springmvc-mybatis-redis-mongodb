package com.codyy.rrt.resource.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.codyy.rrt.base.Page;
import com.codyy.rrt.base.Pageable;
import com.codyy.rrt.base.dao.BaseMongoDao;
import com.codyy.rrt.resource.model.ResourcePush;

/**
 * 资源推送Dao
 * 
 * @author kongchuifang
 * @date 2014-11-10
 */
public interface ResourcePushDao extends BaseMongoDao<ResourcePush> {

	/**
	 * 根据资源推送Id修改资源推送状态
	 */
	public void updateStatusByResourcePushId(String resourcePushId,
			String status);

	/**
	 * 根据资源推送Id修改资源再次推送Id
	 * 
	 * @param resourcePushId
	 * @param nextResourcePushId
	 */
	public void updateNextResourcePushIdByResourcePushId(String resourcePushId,
			String nextResourcePushId);

	/**
	 * 根据资源推送Id修改资源再分发Id
	 * 
	 * @param resourcePushId
	 * @param distributeId
	 */
	public void updateDistributeIdByResourcePushId(String resourcePushId,
			String distributeId);

	/**
	 * 根据资源Id删除
	 * 
	 * @param resourceId
	 */
	public void deleteByResourceId(String resourceId);
	
	public Map<String , Object> getGDResourcePush(String semesterId,String classlevelId,String subjectId,List<String> columnIds,String isDownLoad,List<String> downLoadList,Date startTime,Date endTime,String resourceName,int start,int end);

}
