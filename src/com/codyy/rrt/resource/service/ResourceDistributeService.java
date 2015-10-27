package com.codyy.rrt.resource.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codyy.rrt.base.Page;
import com.codyy.rrt.base.Pageable;
import com.codyy.rrt.base.SimplePageable;
import com.codyy.rrt.baseinfo.mapper.BaseUserMapper;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.dao.ResourceDistributeDao;
import com.codyy.rrt.resource.dao.ResourceDistributeTargetDao;
import com.codyy.rrt.resource.dao.ResourceHoldDao;
import com.codyy.rrt.resource.dao.ResourcePushDao;
import com.codyy.rrt.resource.mapper.ResourceMapper;
import com.codyy.rrt.resource.model.BaseOrg;
import com.codyy.rrt.resource.model.BaseSchool;
import com.codyy.rrt.resource.model.BaseUser;
import com.codyy.rrt.resource.model.ResourceDistribute;
import com.codyy.rrt.resource.model.ResourceDistributeTarget;
import com.codyy.rrt.resource.model.ResourceHold;
import com.codyy.rrt.resource.model.ResourcePush;
import com.codyy.rrt.resource.model.Resources;

@Transactional
@Service("resourceDistributeService")
public class ResourceDistributeService {

	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private BaseUserMapper baseUserMapper;

	@Autowired
	private ResourceDistributeDao resourceDistributeDao;
	@Autowired
	private ResourceDistributeTargetDao resourceDistributeTargetDao;
	@Autowired
	private ResourcePushDao resourcePushDao;
	@Autowired
	private ResourceHoldDao resourceHoldDao;

	@Autowired
	private ResourceService resourceService;

	public List<ResourceDistribute> findByDistributeIds(Set<String> setDistributeId) {
		List<ResourceDistribute> listResourceDistribute = new ArrayList<ResourceDistribute>();
		if (CollectionUtils.isEmpty(setDistributeId)) {
			return listResourceDistribute;
		}

		Map<String, Collection<Object>> inMap = new HashMap<String, Collection<Object>>();
		Collection<Object> collection = new ArrayList<Object>();
		collection.addAll(setDistributeId);
		inMap.put("id", collection);

		listResourceDistribute = resourceDistributeDao.findList(resourceDistributeDao.createCriteria(null, null, inMap));

		return listResourceDistribute;

	}

	/**
	 * 查找分发记录
	 * 
	 * @param pusherType
	 * @param pusherId
	 * @param resourceIds
	 * @return
	 */
	public List<ResourceDistribute> findDistributeRecord(String pusherType, String pusherId, List<String> resourceIds) {
		List<ResourceDistribute> listResourceDistribute = new ArrayList<ResourceDistribute>();
		if (CollectionUtils.isEmpty(resourceIds)) {
			return listResourceDistribute;
		}

		// 根据pusherId和pusherType相等
		Map<String, Object> eqMap = new HashMap<String, Object>();
		eqMap.put("pusherType", pusherType);
		eqMap.put("pusherId", pusherId);

		// in
		Map<String, Collection<Object>> inMap = new HashMap<String, Collection<Object>>();
		Collection<Object> collection = new ArrayList<Object>();
		collection.addAll(resourceIds);
		inMap.put("resourceId", collection);

		listResourceDistribute = resourceDistributeDao.findList(resourceDistributeDao.createCriteria(eqMap, null, inMap));

		return listResourceDistribute;
	}

	/**
	 * 查找接收到的分发记录
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
	public Pageable<ResourceDistributeTarget> findDistributeTargetRecord(String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String resourceName, String resourceColumnId, Date startTime, Date endTime, String holdType, String holdId, Page page) {
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
		if (StringUtils.isNotEmpty(holdType)) {
			eqMap.put("status", Constants.REVIEW_ACCEPT);
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

		List<ResourceDistributeTarget> listResourceDistributeTarget = resourceDistributeTargetDao.findListByPage(resourceDistributeTargetDao.createCriteria(eqMap, lteMap, gteMap, regexMap), listOrder, pageIndex, pageSize);

		// 封装Resources
		packageResources(listResourceDistributeTarget);

		// 封装分页
		Pageable<ResourceDistributeTarget> pageable = new SimplePageable<ResourceDistributeTarget>();
		pageable.addData(listResourceDistributeTarget);
		pageable.setTotalRecords(resourceDistributeTargetDao.findCount(resourceDistributeTargetDao.createCriteria(eqMap, lteMap, gteMap, regexMap)));
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
	 * @param listResourceDistributeTarget
	 */
	private void packageResources(List<ResourceDistributeTarget> listResourceDistributeTarget) {
		if (CollectionUtils.isNotEmpty(listResourceDistributeTarget)) {
			for (ResourceDistributeTarget target : listResourceDistributeTarget) {
				target.setResources(resourceService.packageResourceColumn(resourceMapper.findByResourceId(target.getResourceId())));
			}
		}
	}

	/**
	 * 老师自己上传的分发给学生
	 * 
	 * @param userId
	 * @param resourceId
	 * @return
	 */
	public boolean uploadedToStudent(String userId, String resourceId) {
		Resources resource = resourceMapper.findByResourceId(resourceId);

		if (resource != null) {
			BaseUser baseUser = baseUserMapper.findByBaseUserId(resource.getBaseUserId());
			if (baseUser != null && baseUser.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER) && baseUser.getId().equals(userId)) {
				distributeStudent(baseUser.getId(), resource, userId);
				return true;
			}
		}
		return false;
	}

	/**
	 * 学生推送给老师的资源分发给学生
	 * 
	 * @param pushId
	 * @param userId
	 * @return
	 */
	public boolean pushedToStudent(String pushId, String userId) {
		ResourcePush resourcePush = resourcePushDao.findById(pushId);
		if (resourcePush != null && resourcePush.getReciveType().equals(Constants.HOLD_TYPE_TEACHER) && resourcePush.getReciveId().equals(userId)) {
			BaseUser user = baseUserMapper.findByBaseUserId(userId);
			if (user != null && user.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
				Resources resource = resourceMapper.findByResourceId(resourcePush.getResourceId());
				// 资源分发给学生
				ResourceDistribute distribute = distributeStudent(userId, resource, userId);

				// 修改推送资源的分发Id
				resourcePushDao.updateDistributeIdByResourcePushId(resourcePush.getId(), distribute.getId());
				return true;
			}
		}
		return false;
	}

	/**
	 * 学校分发给老师的资源再分发给学生
	 * 
	 * @param userId
	 * @param distributeTargetId
	 * @return
	 */
	public boolean distributedToStudent(String userId, String distributeTargetId) {
		BaseUser user = baseUserMapper.findByBaseUserId(userId);
		if (user == null) {
			return false;
		}

		ResourceDistributeTarget resourceDistributeTarget = resourceDistributeTargetDao.findById(distributeTargetId);

		Resources resource = resourceMapper.findByResourceId(resourceDistributeTarget.getResourceId());
		if (resourceDistributeTarget == null || resource == null) {
			return false;
		}

		if (resourceDistributeTarget.getReciveType().equals(Constants.HOLD_TYPE_TEACHER) && resourceDistributeTarget.getReciveId().equals(userId) && user.getUserType().equals(CommonsConstant.USER_TYPE_TEACHER)) {
			ResourceDistribute distribute = distributeStudent(userId, resource, userId);

			// 修改资源分发再次分你发Id
			resourceDistributeTargetDao.updateNextResourceDistributeId(resourceDistributeTarget.getId(), distribute.getId());
			return true;
		}
		return false;
	}

	private ResourceDistribute distributeStudent(String teacherUserId, Resources resource, String userId) {
		List<String> studentIds = new LinkedList<String>();
		// 班主任所管理班级的学生ID
		List<String> stuIdsOfHead = baseUserMapper.findStudentUserIdByHeadTeacherUserId(teacherUserId);
		if (CollectionUtils.isNotEmpty(stuIdsOfHead)) {
			studentIds.addAll(stuIdsOfHead);
		}

		// 教师任课班级的学生ID
		List<String> stuIds = baseUserMapper.findStudentUserIdByTeacherUserId(teacherUserId);
		if (CollectionUtils.isNotEmpty(stuIds)) {
			// 判断学生ID是否重复
			for (String stuId : stuIds) {
				if (!studentIds.contains(stuId)) {
					studentIds.add(stuId);
				}
			}
		}

		ResourceDistribute distribute = new ResourceDistribute();
		distribute.setPusherType(Constants.HOLD_TYPE_TEACHER);
		distribute.setPusherOrgSource(Constants.ORG_SOURCE_SCHOOL);
		distribute.setPusherId(userId);
		distribute.setStatus(Constants.REVIEW_ACCEPT); // 老师分发给学生不需要审核

		distribute = packageResourceDistribute(resource, distribute);

		// 保存资源分发到MongoDB中
		resourceDistributeDao.insert(distribute);

		for (String studentId : studentIds) {
			ResourceDistributeTarget target = new ResourceDistributeTarget();
			target.setReciveType(Constants.HOLD_TYPE_STUDENT);
			target.setReciveId(studentId);
			target.setStatus(Constants.REVIEW_ACCEPT);// 老师分发给学生不需要审核

			target = packageResourceDistributeTarget(resource, distribute.getId(), target);

			// 保存资源分发目标到MongoDB中
			resourceDistributeTargetDao.insert(target);
		}
		return distribute;
	}

	/**
	 * 封装资源分发实体
	 * 
	 * @param resource
	 * @param userId
	 * @return
	 */
	private ResourceDistribute packageResourceDistribute(Resources resource, ResourceDistribute distribute) {
		distribute.setId(UUID.randomUUID().toString());
		distribute.setResourceId(resource.getId());
		distribute.setCreateTime(new Date());
		// 冗余字段，方便查询使用
		distribute.setResourceName(resource.getResourceName());
		distribute.setResourceColumnId(resource.getResourceColumnId());
		distribute.setResourceCreateTime(resource.getCreateTime());
		distribute.setBaseSemesterId(resource.getBaseSemesterId());
		distribute.setBaseClasslevelId(resource.getBaseClasslevelId());
		distribute.setBaseDisciplineId(resource.getBaseDisciplineId());
		distribute.setResourceCatalogFirstId(resource.getResourceCatalogFirstId());
		distribute.setResourceCatalogSecondId(resource.getResourceCatalogSecondId());
		return distribute;
	}

	/**
	 * 封装资源分发目标实体
	 * 
	 * @param resource
	 * @param studentId
	 * @param resourceDistributeId
	 * @return
	 */
	private ResourceDistributeTarget packageResourceDistributeTarget(Resources resource, String resourceDistributeId, ResourceDistributeTarget target) {
		target.setId(UUID.randomUUID().toString());
		target.setDistributeId(resourceDistributeId);
		target.setResourceId(resource.getId());
		target.setCreateTime(new Date());

		// 冗余字段，方便查询使用
		target.setResourceName(resource.getResourceName());
		target.setResourceColumnId(resource.getResourceColumnId());
		target.setResourceCreateTime(resource.getCreateTime());
		target.setBaseSemesterId(resource.getBaseSemesterId());
		target.setBaseClasslevelId(resource.getBaseClasslevelId());
		target.setBaseDisciplineId(resource.getBaseDisciplineId());
		target.setResourceCatalogFirstId(resource.getResourceCatalogFirstId());
		target.setResourceCatalogSecondId(resource.getResourceCatalogSecondId());
		return target;
	}

	/**
	 * 学校上传的资源分发给老师
	 * 
	 * @param userId
	 * @param resourceId
	 * @param classlevelIds
	 * @return
	 */
	public boolean uploadedToTeacher(String userId, String resourceId, List<String> classlevelIds) {
		BaseUser user = baseUserMapper.findByBaseUserId(userId);
		Resources resource = resourceMapper.findByResourceId(resourceId);
		if (user != null && resource != null) {
			// 学校Id
			String schoolId = "";
			List<String> listSchoolId = baseUserMapper.findSchoolIdBySchoolUserId(user.getId());
			if (CollectionUtils.isNotEmpty(listSchoolId)) {
				schoolId = listSchoolId.get(0);
			}
			if (StringUtils.isNotEmpty(schoolId)) {
				// 分发资源
				distributeTeacher(schoolId, classlevelIds, resource);
				return true;
			}
		}
		return false;
	}

	/**
	 * 封装资源共享实体
	 * 
	 * @param resource
	 * @param resourceHold
	 * @return
	 */
	private ResourceHold packageResourceHold(Resources resource, ResourceHold resourceHold) {
		resourceHold.setId(UUID.randomUUID().toString());
		resourceHold.setResourceId(resource.getId());
		resourceHold.setCreateTime(new Date());

		// 冗余字段，方便查询使用
		resourceHold.setResourceName(resource.getResourceName());
		resourceHold.setResourceColumnId(resource.getResourceColumnId());
		resourceHold.setResourceCreateTime(resource.getCreateTime());
		resourceHold.setBaseSemesterId(resource.getBaseSemesterId());
		resourceHold.setBaseClasslevelId(resource.getBaseClasslevelId());
		resourceHold.setBaseDisciplineId(resource.getBaseDisciplineId());
		resourceHold.setResourceCatalogFirstId(resource.getResourceCatalogFirstId());
		resourceHold.setResourceCatalogSecondId(resource.getResourceCatalogSecondId());
		return resourceHold;
	}

	/**
	 * 老师推送的分发给老师
	 * 
	 * @param userId
	 * @param pushId
	 * @param classlevelIds
	 * @return
	 */
	public boolean pushedToTeacher(String userId, String pushId, List<String> classlevelIds) {
		BaseUser baseUser = baseUserMapper.findByBaseUserId(userId);
		if (baseUser == null) {
			return false;
		}
		String schoolId = "";
		List<String> listSchoolId = baseUserMapper.findSchoolIdBySchoolUserId(userId);
		if (CollectionUtils.isNotEmpty(listSchoolId)) {
			schoolId = listSchoolId.get(0);
		}
		if (StringUtils.isEmpty(schoolId)) {
			return false;
		}

		ResourcePush push = resourcePushDao.findById(pushId);
		if (push != null && push.getReciveType().equals(Constants.HOLD_TYPE_SCHOOL) && push.getReciveId().equals(schoolId) && push.getStatus().equals(Constants.REVIEW_ACCEPT)) {
			Resources resource = resourceMapper.findByResourceId(push.getResourceId());
			if (resource != null) {
				// 分发给老师
				ResourceDistribute distribute = distributeTeacher(schoolId, classlevelIds, resource);

				// 修改资源推送的分发Id
				resourcePushDao.updateDistributeIdByResourcePushId(push.getId(), distribute.getId());
				return true;
			}
		}
		return false;
	}

	/**
	 * 上级分发的分发给老师
	 * 
	 * @param userId
	 * @param targetId
	 * @param classlevelIds
	 * @return
	 */
	public boolean distributedToTeacher(String userId, String targetId, List<String> classlevelIds) {
		BaseUser user = baseUserMapper.findByBaseUserId(userId);
		if (user == null) {
			return false;
		}

		String schoolId = "";
		List<String> listSchoolId = baseUserMapper.findSchoolIdBySchoolUserId(userId);
		if (CollectionUtils.isNotEmpty(listSchoolId)) {
			schoolId = listSchoolId.get(0);
		}
		if (StringUtils.isEmpty(schoolId)) {
			return false;
		}

		ResourceDistributeTarget target = resourceDistributeTargetDao.findById(targetId);
		if (target != null && target.getReciveType().equals(Constants.HOLD_TYPE_SCHOOL) && target.getReciveId().equals(schoolId) && target.getStatus().equals(Constants.REVIEW_ACCEPT)) {
			Resources resource = resourceMapper.findByResourceId(target.getResourceId());
			if (resource != null) {
				// 分发资源给老师
				ResourceDistribute distribute = distributeTeacher(schoolId, classlevelIds, resource);

				// 修改分发资源实体的再次分发Id
				resourceDistributeTargetDao.updateNextResourceDistributeId(target.getId(), distribute.getId());
				return true;
			}
		}
		return false;
	}

	/**
	 * 分发给老师
	 * 
	 * @param schoolId
	 * @param classlevelIds
	 * @param resource
	 * @return
	 */
	private ResourceDistribute distributeTeacher(String schoolId, List<String> classlevelIds, Resources resource) {
		String baseClassLevelIds = "";
		if (CollectionUtils.isNotEmpty(classlevelIds)) {
			for (String classLevelId : classlevelIds) {
				baseClassLevelIds += "'" + classLevelId + "',";
			}

			if (baseClassLevelIds.endsWith(",")) {
				baseClassLevelIds = baseClassLevelIds.substring(0, baseClassLevelIds.length() - 1);
			}
		} else {
			baseClassLevelIds = "''";
		}

		List<String> listTeacherUserId = null;
		Map<String, Object> parMap = new HashMap<String, Object>();
		parMap.put("schoolId", schoolId);
		parMap.put("baseClassLevelIds", baseClassLevelIds);

		if (StringUtils.isNotEmpty(resource.getBaseDisciplineId())) {
			// 有学科的资源分发给对应学科的任课老师
			parMap.put("baseDisciplineId", resource.getBaseDisciplineId());
			listTeacherUserId = baseUserMapper.findTeacherUserIdBySIdAndClIdAndDId(parMap);
		} else {
			// 没有学科的资源分发给班级的班主任
			listTeacherUserId = baseUserMapper.findHeadTeacherUserIdBySIdAndCLId(parMap);
		}

		ResourceDistribute distribute = new ResourceDistribute();
		distribute.setPusherType(Constants.HOLD_TYPE_SCHOOL);
		distribute.setPusherOrgSource(Constants.ORG_SOURCE_SCHOOL);
		distribute.setPusherId(schoolId);
		distribute.setStatus(Constants.REVIEW_ACCEPT);// 学校分发给老师不需要审核

		distribute = packageResourceDistribute(resource, distribute);
		// 保存资源分发到MongoDB中
		resourceDistributeDao.insert(distribute);

		if (CollectionUtils.isNotEmpty(listTeacherUserId)) {
			for (String teacherUserId : listTeacherUserId) {
				ResourceDistributeTarget target = new ResourceDistributeTarget();
				target.setReciveType(Constants.HOLD_TYPE_TEACHER);
				target.setReciveId(teacherUserId);
				target.setStatus(Constants.REVIEW_ACCEPT);// 学校分发给老师不需要审核

				target = packageResourceDistributeTarget(resource, distribute.getId(), target);

				// 保存资源分发目标到MongoDB中
				resourceDistributeTargetDao.insert(target);
			}
		}

		// 学校分发资源，保存到资源共享中
		insertResourceHold(Constants.HOLD_TYPE_SCHOOL, schoolId, Constants.REVIEW_ACCEPT, resource);

		return distribute;
	}

	/**
	 * 机构上传的分发给下级机构或学校
	 * 
	 * @param userId
	 * @param resourceId
	 * @param orgIds
	 * @param schoolIds
	 * @return
	 */
	public boolean uploadedToSchoolAndOrg(String userId, String resourceId, List<String> orgIds, List<String> schoolIds) {
		BaseUser user = baseUserMapper.findByBaseUserId(userId);
		Resources resource = resourceMapper.findByResourceId(resourceId);
		if (user != null && resource != null) {
			String orgId = "";
			List<String> listOrgId = baseUserMapper.findOrgIdByOrgUserId(userId);
			if (CollectionUtils.isNotEmpty(listOrgId)) {
				orgId = listOrgId.get(0);
			}

			BaseOrg currentOrg = baseUserMapper.findOrgByOrgId(orgId);
			if (currentOrg != null && StringUtils.isNotEmpty(currentOrg.getId())) {
				// 分发所选的下属机构和学校
				distributeSchoolAndOrg(currentOrg, resource, orgIds, schoolIds);
				return true;
			}
		}
		return false;
	}

	/**
	 * 推送给机构的分发给下级机构或学校
	 * 
	 * @param userId
	 * @param pushId
	 * @param orgIds
	 * @param schoolIds
	 * @return
	 */
	public boolean pushedToSchoolAndOrg(String userId, String pushId, List<String> orgIds, List<String> schoolIds) {
		BaseUser baseUser = baseUserMapper.findByBaseUserId(userId);
		if (baseUser == null) {
			return false;
		}

		String orgId = "";
		List<String> listOrgId = baseUserMapper.findOrgIdByOrgUserId(userId);
		if (CollectionUtils.isNotEmpty(listOrgId)) {
			orgId = listOrgId.get(0);
		}
		if (StringUtils.isEmpty(orgId)) {
			return false;
		}

		ResourcePush resourcePush = resourcePushDao.findById(pushId);
		if (resourcePush != null && resourcePush.getReciveType().equals(Constants.HOLD_TYPE_ORG) && resourcePush.getReciveId().equals(orgId) && resourcePush.getStatus().equals(Constants.REVIEW_ACCEPT)) {
			Resources resource = resourceMapper.findByResourceId(resourcePush.getResourceId());
			if (resource != null) {
				BaseOrg currentOrg = baseUserMapper.findOrgByOrgId(orgId);

				ResourceDistribute distribute = distributeSchoolAndOrg(currentOrg, resource, orgIds, schoolIds);

				// 修改推送资源的分发Id
				resourcePushDao.updateDistributeIdByResourcePushId(resourcePush.getId(), distribute.getId());
				return true;
			}
		}
		return false;
	}

	/**
	 * 分发给机构的再分发给下级机构
	 * 
	 * @param userId
	 * @param targetId
	 * @param orgIds
	 * @param schoolIds
	 * @return
	 */
	public boolean distributedToSchoolAndOrg(String userId, String targetId, List<String> orgIds, List<String> schoolIds) {
		BaseUser baseUser = baseUserMapper.findByBaseUserId(userId);
		if (baseUser == null) {
			return false;
		}

		String orgId = "";
		List<String> listOrgId = baseUserMapper.findOrgIdByOrgUserId(userId);
		if (CollectionUtils.isNotEmpty(listOrgId)) {
			orgId = listOrgId.get(0);
		}
		if (StringUtils.isEmpty(orgId)) {
			return false;
		}

		ResourceDistributeTarget target = resourceDistributeTargetDao.findById(targetId);
		if (target != null && target.getReciveType().equals(Constants.HOLD_TYPE_ORG) && target.getReciveId().equals(orgId) && target.getStatus().equals(Constants.REVIEW_ACCEPT)) {
			Resources resource = resourceMapper.findByResourceId(target.getResourceId());
			if (resource != null) {
				BaseOrg currentOrg = baseUserMapper.findOrgByOrgId(orgId);
				// 分发下级机构和学校
				ResourceDistribute distribute = distributeSchoolAndOrg(currentOrg, resource, orgIds, schoolIds);

				// 修改资源分发的再次分发Id
				resourceDistributeTargetDao.updateNextResourceDistributeId(target.getId(), distribute.getId());
				return true;
			}
		}
		return false;
	}

	private ResourceDistribute distributeSchoolAndOrg(BaseOrg currentOrg, Resources resource, List<String> orgIds, List<String> schoolIds) {
		String status = Constants.REVIEW_NOT_REVIEW;
		if (currentOrg.getIsVerify() != null && currentOrg.getIsVerify().equals(Constants.ORG_UNVERIFY)) { // 如果当前机构是免审核的，则直接通过
			status = Constants.REVIEW_ACCEPT;
		}

		// TODO:检查资源重复分发
		ResourceDistribute distribute = new ResourceDistribute();
		distribute.setPusherType(Constants.HOLD_TYPE_ORG);
		distribute.setPusherId(currentOrg.getId());
		if (Constants.ORG_LEVEL_ONE.equals(currentOrg.getOrgLevel())) {
			distribute.setPusherOrgSource(Constants.ORG_SOURCE_ORG_LEVEL_ONE);
		} else if (Constants.ORG_LEVEL_TWO.equals(currentOrg.getOrgLevel())) {
			distribute.setPusherOrgSource(Constants.ORG_SOURCE_ORG_LEVEL_TWO);
		} else if (Constants.ORG_LEVEL_THREE.equals(currentOrg.getOrgLevel())) {
			distribute.setPusherOrgSource(Constants.ORG_SOURCE_ORG_LEVEL_THREE);
		} else {
			distribute.setPusherOrgSource(Constants.HOLD_TYPE_ORG);
		}
		distribute.setStatus(status);

		distribute = packageResourceDistribute(resource, distribute);
		// 保存资源分发到MongoDB中
		resourceDistributeDao.insert(distribute);

		// 分发下属机构
		List<BaseOrg> listOrg = baseUserMapper.findOrgByParentOrgId(currentOrg.getId());
		for (BaseOrg org : listOrg) {
			if (orgIds.contains(org.getId())) {
				ResourceDistributeTarget target = new ResourceDistributeTarget();
				target.setReciveType(Constants.HOLD_TYPE_ORG);
				target.setReciveId(org.getId());
				target.setStatus(status);

				target = packageResourceDistributeTarget(resource, distribute.getId(), target);

				// 保存资源分发目标到MongoDB中
				resourceDistributeTargetDao.insert(target);
			}
		}

		// 分发下属学校
		List<BaseSchool> listSchool = baseUserMapper.findSchoolByOrgId(currentOrg.getId());
		for (BaseSchool school : listSchool) {
			if (schoolIds.contains(school.getId())) {
				ResourceDistributeTarget target = new ResourceDistributeTarget();
				target.setReciveType(Constants.HOLD_TYPE_SCHOOL);
				target.setReciveId(school.getId());
				target.setStatus(status);

				target = packageResourceDistributeTarget(resource, distribute.getId(), target);

				// 保存资源分发目标到MongoDB中
				resourceDistributeTargetDao.insert(target);
			}
		}

		// 机构分发资源，保存到资源共享中
		insertResourceHold(Constants.HOLD_TYPE_ORG, currentOrg.getId(), status, resource);
		return distribute;
	}

	/**
	 * 保存资源共享
	 */
	private void insertResourceHold(String holdType, String holdId, String status, Resources resource) {
		// 机构分发资源，保存到资源共享中
		ResourceHold resourceHold = new ResourceHold();
		resourceHold.setHoldType(holdType);
		resourceHold.setHoldId(holdId);
		resourceHold.setStatus(status); // 机构分发需要审核,如果当前机构是免审核的，则直接通过

		// 封装资源共享实体
		resourceHold = packageResourceHold(resource, resourceHold);

		resourceHoldDao.insert(resourceHold);
	}
}
