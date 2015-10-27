package com.codyy.rrt.resource.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codyy.rrt.base.Page;
import com.codyy.rrt.base.Pageable;
import com.codyy.rrt.base.SimplePageable;
import com.codyy.rrt.baseinfo.mapper.BaseUserMapper;
import com.codyy.rrt.baseinfo.mapper.ResourceLogMapper;
import com.codyy.rrt.baseinfo.mapper.SkeletonMapper;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.dao.ResourceDistributeDao;
import com.codyy.rrt.resource.dao.ResourcePushDao;
import com.codyy.rrt.resource.mapper.ResourceMapper;
import com.codyy.rrt.resource.model.BaseUser;
import com.codyy.rrt.resource.model.ResourceColumn;
import com.codyy.rrt.resource.model.ResourcePush;
import com.codyy.rrt.resource.model.Resources;

@Transactional
@Service("resourcePushService")
public class ResourcePushService {

	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private BaseUserMapper baseUserMapper;
	@Autowired
	private BaseUserService baseUserService;
	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ResourcePushDao resourcePushDao;
	@Autowired
	private ResourceDistributeDao resourceDistributeDao;
	@Autowired
	private SkeletonMapper skeletonMapper;
	@Autowired
	private ResourceLogMapper resourceLogMapper;

	private Logger logger = Logger.getLogger(this.getClass());

	public List<ResourcePush> findByResourcePushIds(Set<String> setResourcePushId) {
		List<ResourcePush> listResourcePush = new ArrayList<ResourcePush>();
		if (CollectionUtils.isEmpty(setResourcePushId)) {
			return listResourcePush;
		}

		Map<String, Collection<Object>> inMap = new HashMap<String, Collection<Object>>();
		Collection<Object> collection = new ArrayList<Object>();
		collection.addAll(setResourcePushId);
		inMap.put("id", collection);

		listResourcePush = resourcePushDao.findList(resourcePushDao.createCriteria(null, null, inMap));

		return listResourcePush;
	}

	/**
	 * 查询推送者自己的推荐记录
	 * 
	 * @param holdType
	 * @param holdId
	 * @param resourceIds
	 * @return
	 */
	public List<ResourcePush> findPushPusherRecord(String holdType, String holdId, List<String> resourceIds) {
		List<ResourcePush> listResourcePush = new ArrayList<ResourcePush>();
		if (CollectionUtils.isEmpty(resourceIds)) {
			return listResourcePush;
		}

		// 根据pusherId和pusherType相等
		Map<String, Object> eqMap = new HashMap<String, Object>();
		eqMap.put("pusherType", holdType);
		eqMap.put("pusherId", holdId);

		// in
		Map<String, Collection<Object>> inMap = new HashMap<String, Collection<Object>>();
		Collection<Object> collection = new ArrayList<Object>();
		collection.addAll(resourceIds);
		inMap.put("resourceId", collection);

		listResourcePush = resourcePushDao.findList(resourcePushDao.createCriteria(eqMap, null, inMap));

		return listResourcePush;
	}

	/**
	 * 推送接收者查询记录
	 * 
	 * @param semesterId
	 * @param classlevelId
	 * @param disciplineId
	 * @param resourceCatalogFirstId
	 * @param resourceName
	 * @param resourceColumnId
	 * @param startTime
	 * @param endTime
	 * @param holdType
	 * @param holdId
	 * @param page
	 * @return
	 */
	public Pageable<ResourcePush> findPushReciveRecord(String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String resourceName, String resourceColumnId, Date startTime, Date endTime, String holdType, String holdId, Page page) {
		// 相等
		Map<String, Object> eqMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(semesterId)) {
			eqMap.put("baseSemesterId", semesterId);
		}
		if (StringUtils.isNotEmpty(classlevelId)) {
			eqMap.put("baseClasslevelId", classlevelId);
		}
		if (StringUtils.isNotEmpty(disciplineId)) {
			eqMap.put("baseDisciplineId", disciplineId);
		}
		if (StringUtils.isNotEmpty(resourceCatalogFirstId)) {
			eqMap.put("resourceCatalogFirstId", resourceCatalogFirstId);
		}
		if (StringUtils.isNotEmpty(resourceCatalogSecondId)) {
			eqMap.put("resourceCatalogSecondId", resourceCatalogSecondId);
		}
		if (StringUtils.isNotEmpty(resourceColumnId)) {
			eqMap.put("resourceColumnId", resourceColumnId);
		}
		if (StringUtils.isNotEmpty(holdType)) {
			eqMap.put("reciveType", holdType);
		}
		if (StringUtils.isNotEmpty(holdId)) {
			eqMap.put("reciveId", holdId);
		}

		// 大于等于
		Map<String, Object> gteMap = new HashMap<String, Object>();
		if (startTime != null) {
			gteMap.put("resourceCreateTime", startTime);
		}
		// 小于等于
		Map<String, Object> lteMap = new HashMap<String, Object>();
		if (endTime != null) {
			lteMap.put("resourceCreateTime", endTime);
		}

		// like
		Map<String, String> regexMap = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(resourceName)) {
			regexMap.put("resourceName", resourceName);
		}

		int pageIndex = page.getCurrentPage();
		int pageSize = page.getPageCountData();

		// 排序
		List<Order> listOrder = new ArrayList<Order>();
		listOrder.add(new Sort.Order(Sort.Direction.DESC, "createTime"));

		List<ResourcePush> listResourcePush = resourcePushDao.findListByPage(resourcePushDao.createCriteria(eqMap, lteMap, gteMap, regexMap), listOrder, pageIndex, pageSize);

		// 封装Resources
		packageResources(listResourcePush);

		// 如果是机构，则封装资源分发
		if (Constants.HOLD_TYPE_ORG.equals(holdType)) {
			packageResourceDistribute(listResourcePush);
		}

		// 封装分页
		Pageable<ResourcePush> pageable = new SimplePageable<ResourcePush>();
		pageable.addData(listResourcePush);
		pageable.setTotalRecords(resourcePushDao.findCount(resourcePushDao.createCriteria(eqMap, lteMap, gteMap, regexMap)));
		// 当前页
		pageable.setCurrentPage(page.getCurrentPage());
		// 每页的数量
		pageable.setPageCountData(page.getPageCountData());
		// 总页数
		pageable.setTotalPages(pageable.calTotalPages());

		return pageable;
	}

	/**
	 * 封装Resources
	 * 
	 * @param listResourcePush
	 */
	private void packageResources(List<ResourcePush> listResourcePush) {
		if (CollectionUtils.isNotEmpty(listResourcePush)) {
			for (ResourcePush resourcePush : listResourcePush) {
				resourcePush.setResources(resourceService.packageResourceColumn(resourceMapper.findByResourceId(resourcePush.getResourceId())));
			}
		}
	}

	/**
	 * 封装资源分发
	 * 
	 * @param listResourcePush
	 */
	private void packageResourceDistribute(List<ResourcePush> listResourcePush) {
		if (CollectionUtils.isNotEmpty(listResourcePush)) {
			for (ResourcePush resourcePush : listResourcePush) {
				resourcePush.setResourceDistribute(resourceDistributeDao.findById(resourcePush.getDistributeId()));
			}
		}
	}

	/**
	 * 向上推荐，用户审核通过
	 * 
	 * @param pushId
	 * @param userId
	 * @return
	 */
	public boolean reviewAcceptPush(String pushId, String userId) {
		ResourcePush push = resourcePushDao.findById(pushId);
		if (push != null) {
			resourcePushDao.updateStatusByResourcePushId(pushId, Constants.REVIEW_ACCEPT);

			return true;
		}
		return false;
	}

	/**
	 * 向上推荐，用户审核不通过
	 * 
	 * @param pushId
	 * @param userId
	 * @return
	 */
	public boolean reviewRejectPush(String pushId, String userId) {
		ResourcePush push = resourcePushDao.findById(pushId);
		if (push != null) {
			resourcePushDao.updateStatusByResourcePushId(pushId, Constants.REVIEW_REJECT);
			return true;
		}
		return false;
	}

	/**
	 * 学生推送给老师
	 * 
	 * @param userId
	 * @param resourceId
	 * @return
	 */
	public boolean studentUploadedPushTeacher(String userId, String resourceId) {
		Resources resource = resourceMapper.findByResourceId(resourceId);
		if (resource != null) {
			BaseUser baseUser = baseUserMapper.findByBaseUserId(resource.getBaseUserId());
			if (baseUser != null && baseUser.getUserType().equals(CommonsConstant.USER_TYPE_STUDENT) && baseUser.getId().equals(userId)) {
				List<String> listTeacherUserId = null;
				// 如果资源的学科Id不为空，则推送给学科的任课老师，否则推送给学生的班主任
				if (StringUtils.isNotEmpty(resource.getBaseDisciplineId())) {
					Map<String, Object> parMap = new HashMap<String, Object>();
					parMap.put("studentUserId", userId);
					parMap.put("baseDisciplineId", resource.getBaseDisciplineId());
					listTeacherUserId = baseUserMapper.findTeacherUserIdByStudentUserId(parMap);
				} else {
					listTeacherUserId = baseUserMapper.findHeadTeacherUserIdByStudentUserId(userId);
				}

				String targetTeacherUserId = "";
				if (CollectionUtils.isNotEmpty(listTeacherUserId)) {
					targetTeacherUserId = listTeacherUserId.get(0);
				}
				if (StringUtils.isNotEmpty(targetTeacherUserId)) {
					// 保存资源推送到MongoDB中
					ResourcePush resourcePush = new ResourcePush();
					resourcePush.setPusherType(Constants.HOLD_TYPE_STUDENT);
					resourcePush.setPusherId(baseUser.getId());
					resourcePush.setReciveType(Constants.HOLD_TYPE_TEACHER);
					resourcePush.setReciveId(targetTeacherUserId);
					resourcePush.setStatus(Constants.REVIEW_NOT_REVIEW);
					resourcePush = packageResourcePush(resource, resourcePush);

					resourcePushDao.insert(resourcePush);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 老师自己上传的推送给学校
	 * 
	 * @param userId
	 * @param resourceId
	 * @return
	 */
	public boolean teacherUploadedPushSchool(String userId, String resourceId) {
		Resources resource = resourceMapper.findByResourceId(resourceId);
		if (resource != null) {
			BaseUser baseUser = baseUserMapper.findByBaseUserId(resource.getBaseUserId());
			if (baseUser != null && baseUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER) && baseUser.getId().equals(userId)) {
				String baseSchoolId = baseUserMapper.findSchoolIdByTeacherUserId(baseUser.getId());
				if (StringUtils.isNotEmpty(baseSchoolId)) {
					// 保存ResourcePush到Mongodb中
					ResourcePush resourcePush = new ResourcePush();
					resourcePush.setPusherType(Constants.HOLD_TYPE_TEACHER);
					resourcePush.setPusherId(baseUser.getId());
					resourcePush.setReciveType(Constants.HOLD_TYPE_SCHOOL);
					resourcePush.setReciveId(baseSchoolId);
					resourcePush.setStatus(Constants.REVIEW_NOT_REVIEW);
					resourcePush = packageResourcePush(resource, resourcePush);

					resourcePushDao.insert(resourcePush);
					return true;
				} else {
					logger.debug("uploadedPushSchool:Teacher is null");
				}
			} else {
				logger.debug("uploadedPushSchool:BaseUser is null");
			}
		} else {
			logger.debug("uploadedPushSchool:resource is null");
		}
		return false;
	}

	/**
	 * 封装资源推送
	 */
	private ResourcePush packageResourcePush(Resources resource, ResourcePush resourcePush) {
		resourcePush.setId(UUID.randomUUID().toString());
		resourcePush.setResourceId(resource.getId());
		resourcePush.setCreateTime(new Date());
		// 冗余字段，方便查询使用
		resourcePush.setResourceName(resource.getResourceName());
		resourcePush.setResourceColumnId(resource.getResourceColumnId());
		resourcePush.setResourceCreateTime(resource.getCreateTime());
		resourcePush.setBaseSemesterId(resource.getBaseSemesterId());
		resourcePush.setBaseClasslevelId(resource.getBaseClasslevelId());
		resourcePush.setBaseDisciplineId(resource.getBaseDisciplineId());
		resourcePush.setResourceCatalogFirstId(resource.getResourceCatalogFirstId());
		resourcePush.setResourceCatalogSecondId(resource.getResourceCatalogSecondId());

		return resourcePush;
	}

	/**
	 * 封装资源再次推送
	 */
	private ResourcePush packageResourcePush(ResourcePush resourcePush, ResourcePush nextResourcePush) {
		nextResourcePush.setId(UUID.randomUUID().toString());
		nextResourcePush.setResourceId(resourcePush.getResourceId());
		nextResourcePush.setCreateTime(new Date());
		// 冗余字段，方便查询使用
		nextResourcePush.setResourceName(resourcePush.getResourceName());
		nextResourcePush.setResourceColumnId(resourcePush.getResourceColumnId());
		nextResourcePush.setResourceCreateTime(resourcePush.getResourceCreateTime()); // 资源创建时间
		nextResourcePush.setBaseSemesterId(resourcePush.getBaseSemesterId());
		nextResourcePush.setBaseClasslevelId(resourcePush.getBaseClasslevelId());
		nextResourcePush.setBaseDisciplineId(resourcePush.getBaseDisciplineId());
		nextResourcePush.setResourceCatalogFirstId(resourcePush.getResourceCatalogFirstId());
		nextResourcePush.setResourceCatalogSecondId(resourcePush.getResourceCatalogSecondId());

		return nextResourcePush;
	}

	/**
	 * 学校上传的资源推送给上级电教馆
	 * 
	 * @param resourceId
	 * @param userId
	 * @return
	 */
	public boolean schoolUploadedPushOrg(String resourceId, String userId) {
		Resources resource = resourceMapper.findByResourceId(resourceId);
		if (resource != null) {
			String schoolId = "";
			List<String> listSchoolId = baseUserMapper.findSchoolIdBySchoolUserId(userId);
			if (CollectionUtils.isNotEmpty(listSchoolId)) {
				schoolId = listSchoolId.get(0);
			}

			if (StringUtils.isNotEmpty(schoolId) && resource.getHoldType().equals(Constants.HOLD_TYPE_SCHOOL) && resource.getHoldId().equals(schoolId)) {
				String orgId = baseUserMapper.findOrgIdBySchoolId(schoolId);
				if (StringUtils.isNotEmpty(orgId)) {
					// 保存资源推送到Mongodb
					ResourcePush resourcePush = new ResourcePush();
					resourcePush.setPusherType(Constants.HOLD_TYPE_SCHOOL);
					resourcePush.setPusherId(schoolId);
					resourcePush.setReciveType(Constants.HOLD_TYPE_ORG);
					resourcePush.setReciveId(orgId);
					resourcePush.setStatus(Constants.REVIEW_NOT_REVIEW);

					resourcePush = packageResourcePush(resource, resourcePush);

					resourcePushDao.insert(resourcePush);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 电教馆上传的资源推送给上级电教馆
	 * 
	 * @param resourceId
	 * @param userId
	 * @return
	 */
	public boolean orgUploadedPushOrg(String resourceId, String userId) {
		Resources resource = resourceMapper.findByResourceId(resourceId);
		if (resource != null) {
			String orgId = baseUserService.findOrgIdByOrgUserId(userId);

			if (StringUtils.isNotEmpty(orgId) && resource.getHoldType().equals(Constants.HOLD_TYPE_ORG) && resource.getHoldId().equals(orgId)) {
				List<String> listParentOrgId = baseUserMapper.findParentOrgIdByOrgId(orgId);
				// 父级机构Id
				String parentOrgId = "";
				if (CollectionUtils.isNotEmpty(listParentOrgId)) {
					parentOrgId = listParentOrgId.get(0);
				}
				if (StringUtils.isNotEmpty(parentOrgId)) {
					ResourcePush resourcePush = new ResourcePush();
					resourcePush.setPusherType(Constants.HOLD_TYPE_ORG);
					resourcePush.setPusherId(orgId);
					resourcePush.setReciveType(Constants.HOLD_TYPE_ORG);
					resourcePush.setReciveId(parentOrgId);
					resourcePush.setStatus(Constants.REVIEW_NOT_REVIEW);

					resourcePush = packageResourcePush(resource, resourcePush);

					resourcePushDao.insert(resourcePush);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 学生推送给老师的审批通过后推送给学校
	 * 
	 * @param pushId
	 * @param userId
	 * @return
	 */
	public boolean pushedPushSchool(String pushId, String userId) {
		ResourcePush push = resourcePushDao.findById(pushId);
		if (push != null && push.getReciveType().equals(Constants.HOLD_TYPE_TEACHER) && push.getReciveId().equals(userId) && push.getStatus().equals(Constants.REVIEW_ACCEPT)) {
			BaseUser baseUser = baseUserMapper.findByBaseUserId(userId);

			if (baseUser != null && baseUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
				String schoolId = baseUserMapper.findSchoolIdByTeacherUserId(userId);
				if (StringUtils.isNotEmpty(schoolId)) {
					ResourcePush pushNext = new ResourcePush();
					pushNext.setPusherType(Constants.HOLD_TYPE_TEACHER);
					pushNext.setPusherId(baseUser.getId());
					pushNext.setReciveType(Constants.HOLD_TYPE_SCHOOL);
					pushNext.setReciveId(schoolId);
					pushNext.setStatus(Constants.REVIEW_NOT_REVIEW);

					pushNext = packageResourcePush(push, pushNext);

					// 保存资源再次推送
					resourcePushDao.insert(pushNext);
					push.setNextResourcePushId(pushNext.getId());
					// 修改资源推送
					resourcePushDao.updateNextResourcePushIdByResourcePushId(push.getId(), pushNext.getId());
					return true;
				} else {
					logger.debug("pushedPushSchool:School is null");
				}
			}
		}
		return false;
	}

	/**
	 * 老师推送给学校的由学校推送给电教馆 学校推送给电教馆的推送给上级电教馆 下级电教馆推送给电教馆的推送给上级电教馆
	 * 
	 * @param pushId
	 * @param userId
	 * @return
	 */
	public boolean pushedPushOrg(String pushId, String userId) {
		ResourcePush push = resourcePushDao.findById(pushId);
		if (push == null) {
			return false;
		}

		BaseUser baseUser = baseUserMapper.findByBaseUserId(userId);
		if (baseUser == null) {
			return false;
		}

		String pusherType = null;
		String pusherId = null;
		String receivId = null;
		if (baseUser.getUserType().equals(CommonsConstant.USER_TYPE_SCHOOL)) {
			pusherType = Constants.HOLD_TYPE_SCHOOL;
			List<String> listSchoolId = baseUserMapper.findSchoolIdBySchoolUserId(userId);
			if (CollectionUtils.isNotEmpty(listSchoolId)) {
				pusherId = listSchoolId.get(0);
				receivId = baseUserMapper.findOrgIdBySchoolId(pusherId);
			}
		} else if (baseUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
			pusherType = Constants.HOLD_TYPE_SCHOOL;
			pusherId = baseUserMapper.findSchoolIdByTeacherUserId(userId);
			receivId = baseUserMapper.findOrgIdBySchoolId(pusherId);
		} else if (baseUser.getUserType().equals(CommonsConstant.USER_TYPE_ORG_MANAGER)) {
			pusherType = Constants.HOLD_TYPE_ORG;
			List<String> listOrgId = baseUserMapper.findOrgIdByOrgUserId(userId);
			if (CollectionUtils.isNotEmpty(listOrgId)) {
				pusherId = listOrgId.get(0);
				List<String> listParentOrgId = baseUserMapper.findParentOrgIdByOrgId(pusherId);
				if (CollectionUtils.isNotEmpty(listParentOrgId)) {
					receivId = listParentOrgId.get(0);
				}
			}
		} else if (baseUser.getUserType().equals(CommonsConstant.USER_TYPE_ORG)) {
			pusherType = Constants.HOLD_TYPE_ORG;
			List<String> listOrgId = baseUserMapper.findOrgIdByOrgUserId(userId);
			if (CollectionUtils.isNotEmpty(listOrgId)) {
				pusherId = listOrgId.get(0);
				List<String> listParentOrgId = baseUserMapper.findParentOrgIdByOrgId(pusherId);
				if (CollectionUtils.isNotEmpty(listParentOrgId)) {
					receivId = listParentOrgId.get(0);
				}
			}
		}

		if (pusherType != null && pusherId != null && receivId != null && push.getStatus().equals(Constants.REVIEW_ACCEPT)) {
			ResourcePush pushNext = new ResourcePush();
			pushNext.setPusherType(pusherType);
			pushNext.setPusherId(pusherId);
			pushNext.setReciveType(Constants.HOLD_TYPE_ORG);
			pushNext.setReciveId(receivId);
			pushNext.setStatus(Constants.REVIEW_NOT_REVIEW);

			pushNext = packageResourcePush(push, pushNext);

			// 保存资源再次推送
			resourcePushDao.insert(pushNext);
			push.setNextResourcePushId(pushNext.getId());
			// 修改资源推送
			resourcePushDao.updateNextResourcePushIdByResourcePushId(push.getId(), pushNext.getId());
			return true;
		}
		return false;
	}
	
	/**
	 * 获取省级视频资源
	 * @param semesterId
	 * @param classlevelId
	 * @param subjectId
	 * @param columnId
	 * @param downLoadList
	 * @param startTime
	 * @param endTime
	 * @param resourceName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getGDResourcePush(LoginUser loginUser,String semesterId,String classlevelId,String subjectId,List<String> columnIds,String isDownLoad,List<String> downLoadList,Date startTime,Date endTime,String resourceName,int start,int end){
		Map<String, Object> result = new HashMap<String, Object>();
		List<ResourceColumn> list = skeletonMapper.findAllResourceColumn();
		for (ResourceColumn dto : list) {
			if (dto.getColumnType().equalsIgnoreCase("VIDEO")) {
				columnIds.add(dto.getId());
			}
		}

		downLoadList = resourceLogMapper.getDownLoadResourceLog(loginUser.getUsername());

		result = resourcePushDao.getGDResourcePush(semesterId, classlevelId, subjectId, columnIds, isDownLoad,downLoadList, startTime, endTime, resourceName, start, end);
		// 封装Resources
		if (!result.get("total").equals("0")) {
			packageResources((List<ResourcePush>) result.get("data"));
			// 添加是否下载标记
			for (ResourcePush dto1 : (List<ResourcePush>) result.get("data")) {
				if (downLoadList.size() > 0) {
					if (downLoadList.contains(dto1.getResourceId())) {
						dto1.setIsDownLoad("已下载");
					} else {
						dto1.setIsDownLoad("未下载");
					}
				} else {
					dto1.setIsDownLoad("未下载");
				}
			}
		}
		return result;
	}
	
}
