package com.codyy.rrt.resource.service;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codyy.base.web.SpringContext;
import com.codyy.rrt.base.Page;
import com.codyy.rrt.base.Pageable;
import com.codyy.rrt.base.SimplePageable;
import com.codyy.rrt.base.util.VideoUtil;
import com.codyy.rrt.baseinfo.mapper.BaseUserMapper;
import com.codyy.rrt.commons.utils.OracleKeyWordUtils;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.dao.ResourceDistributeDao;
import com.codyy.rrt.resource.dao.ResourceDistributeTargetDao;
import com.codyy.rrt.resource.dao.ResourceFavoriteDao;
import com.codyy.rrt.resource.dao.ResourceHistoryDao;
import com.codyy.rrt.resource.dao.ResourceHoldDao;
import com.codyy.rrt.resource.dao.ResourcePushDao;
import com.codyy.rrt.resource.mapper.ResourceImageMapper;
import com.codyy.rrt.resource.mapper.ResourceKnowledgeMapper;
import com.codyy.rrt.resource.mapper.ResourceLabelMapper;
import com.codyy.rrt.resource.mapper.ResourceMapper;
import com.codyy.rrt.resource.model.BaseAttacheFile;
import com.codyy.rrt.resource.model.BaseClassLevel;
import com.codyy.rrt.resource.model.BaseDiscipline;
import com.codyy.rrt.resource.model.BaseKnowledge;
import com.codyy.rrt.resource.model.BaseOrg;
import com.codyy.rrt.resource.model.BaseSemester;
import com.codyy.rrt.resource.model.BaseUser;
import com.codyy.rrt.resource.model.ResourceColumn;
import com.codyy.rrt.resource.model.ResourceFavorite;
import com.codyy.rrt.resource.model.ResourceHistory;
import com.codyy.rrt.resource.model.ResourceHold;
import com.codyy.rrt.resource.model.ResourceImage;
import com.codyy.rrt.resource.model.ResourceKnowledge;
import com.codyy.rrt.resource.model.ResourceLabel;
import com.codyy.rrt.resource.model.Resources;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;

@Transactional
@Service("resourceUserService")
public class ResourceUserService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private ResourceLabelMapper resourceLabelMapper;
    @Autowired
    private ResourceKnowledgeMapper resourceKnowledgeMapper;
    @Autowired
    private ResourceImageMapper resourceImageMapper;
    @Autowired
    private BaseUserMapper baseUserMapper;

    @Autowired
    private ResourceHistoryDao resourceHistoryDao;
    @Autowired
    private ResourceHoldDao resourceHoldDao;
    @Autowired
    private ResourceFavoriteDao resourceFavoriteDao;
    @Autowired
    private ResourcePushDao resourcePushDao;
    @Autowired
    private ResourceDistributeTargetDao resourceDistributeTargetDao;
    @Autowired
    private ResourceDistributeDao resourceDistributeDao;

    @Autowired
    private SkeletonService skeletonService;
    @Autowired
    private ResourceService resourceService;

    /**
     * 查询用户上传的资源记录
     * 
     * @param semesterId
     * @param classlevelId
     * @param disciplineId
     * @param resourceCatalogFirstId
     * @param resourceCatalogSecondId
     * @param resourceName
     * @param resourceColumnId
     * @param startTime
     * @param endTime
     * @param holdType
     * @param holdId
     * @param page
     * @return
     */
    public Pageable<Resources> findUploadedResource(String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String resourceName, String resourceColumnId, Date startTime, Date endTime, String holdType, String holdId, Page page) {
	Map<String, Object> parMap = new HashMap<String, Object>();
	parMap.put("baseSemesterId", semesterId);
	parMap.put("baseClasslevelId", classlevelId);
	parMap.put("baseDisciplineId", disciplineId);
	parMap.put("resourceCatalogFirstId", resourceCatalogFirstId);
	parMap.put("resourceCatalogSecondId", resourceCatalogSecondId);
	if (StringUtils.isNotEmpty(resourceName)) { // 使用\为转义字符查询特殊字符的资源名称
	   /* resourceName = resourceName.replace(Constants.CHAR_ESCAPE, Constants.CHAR_ESCAPE + Constants.CHAR_ESCAPE);
	    resourceName = resourceName.replace(Constants.CHAR_PERCENT, Constants.CHAR_ESCAPE + Constants.CHAR_PERCENT);*/
		resourceName = OracleKeyWordUtils.oracleKeyWordReplace(resourceName);
		parMap.put("charEscape", OracleKeyWordUtils.FILTER_CHAR);
	}
	parMap.put("resourceName", resourceName);
	parMap.put("resourceColumnId", resourceColumnId);
	parMap.put("startTime", startTime);
	parMap.put("endTime", endTime);
	parMap.put("holdType", holdType);
	parMap.put("holdId", holdId);
	parMap.put("startNum", page.getPageCountData() * (page.getCurrentPage() - 1));
	parMap.put("endNum", page.getPageCountData() * page.getCurrentPage());

	Pageable<Resources> pageable = new SimplePageable<Resources>();
	// 总的数量
	pageable.setTotalRecords(resourceMapper.countUploadedResource(parMap));
	// 当前页
	pageable.setCurrentPage(page.getCurrentPage());
	// 每页的数量
	pageable.setPageCountData(page.getPageCountData());
	// 总页数
	pageable.setTotalPages(pageable.calTotalPages());
	// 分页数据
	pageable.addData(resourceService.packageResourceColumn(resourceMapper.findUploadedResource(parMap)));
	return pageable;
    }

    /**
     * 查询用户上传的资源记录，但不包含用户删除的或管理员删除的，且转换状态是转换成功的，用于访客页面的显示
     * 
     * @param semesterId
     * @param classlevelId
     * @param disciplineId
     * @param resourceCatalogFirstId
     * @param resourceCatalogSecondId
     * @param resourceName
     * @param resourceColumnId
     * @param startTime
     * @param endTime
     * @param holdType
     * @param holdId
     * @param page
     * @return
     */
    public Pageable<Resources> findUploadedResourceExcludeDelete(String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String resourceName, String resourceColumnId, Date startTime, Date endTime, String holdType, String holdId, Page page) {
	Map<String, Object> parMap = new HashMap<String, Object>();
	parMap.put("baseSemesterId", semesterId);
	parMap.put("baseClasslevelId", classlevelId);
	parMap.put("baseDisciplineId", disciplineId);
	parMap.put("resourceCatalogFirstId", resourceCatalogFirstId);
	parMap.put("resourceCatalogSecondId", resourceCatalogSecondId);
	parMap.put("resourceName", resourceName);
	parMap.put("resourceColumnId", resourceColumnId);
	parMap.put("startTime", startTime);
	parMap.put("endTime", endTime);
	parMap.put("holdType", holdType);
	parMap.put("holdId", holdId);
	parMap.put("startNum", page.getPageCountData() * (page.getCurrentPage() - 1));
	parMap.put("endNum", page.getPageCountData() * page.getCurrentPage());

	Pageable<Resources> pageable = new SimplePageable<Resources>();
	// 总的数量
	pageable.setTotalRecords(resourceMapper.countUploadedResourceExcludeDelete(parMap));
	// 当前页
	pageable.setCurrentPage(page.getCurrentPage());
	// 每页的数量
	pageable.setPageCountData(page.getPageCountData());
	// 总页数
	pageable.setTotalPages(pageable.calTotalPages());
	// 分页数据
	pageable.addData(resourceService.packageResourceColumn(resourceMapper.findUploadedResourceExcludeDelete(parMap)));
	return pageable;
    }

    /**
     * 查询用户收藏的资源
     * 
     * @param semesterId
     * @param classlevelId
     * @param disciplineId
     * @param resourceCatalogFirstId
     * @param resourceName
     * @param resourceColumnId
     * @param startTime
     * @param endTime
     * @param userId
     * @param page
     * @return
     */
    public Pageable<ResourceFavorite> findFavoriteResource(String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String resourceName, String resourceColumnId, Date startTime, Date endTime, String userId, Page page) {
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
	if (StringUtils.isNotEmpty(userId)) {
	    eqMap.put("baseUserId", userId);
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

	// 资源收藏记录
	List<ResourceFavorite> listResourceFavorite = resourceFavoriteDao.findListByPage(resourceFavoriteDao.createCriteria(eqMap, lteMap, gteMap, regexMap), listOrder, pageIndex, pageSize);

	// 封装资源实体
	packageListResourceFavorite(listResourceFavorite);

	// 封装分页
	Pageable<ResourceFavorite> pageable = new SimplePageable<ResourceFavorite>();
	pageable.addData(listResourceFavorite);
	pageable.setTotalRecords(resourceFavoriteDao.findCount(resourceFavoriteDao.createCriteria(eqMap, lteMap, gteMap, regexMap)));
	// 当前页
	pageable.setCurrentPage(page.getCurrentPage());
	// 每页的数量
	pageable.setPageCountData(page.getPageCountData());
	// 总页数
	pageable.setTotalPages(pageable.calTotalPages());

	return pageable;
    }

    /**
     * 封装资源实体
     * 
     * @param listResourceFavorite
     */
    private void packageListResourceFavorite(List<ResourceFavorite> listResourceFavorite) {
	if (CollectionUtils.isNotEmpty(listResourceFavorite)) {
	    for (ResourceFavorite resourceFavorite : listResourceFavorite) {
		resourceFavorite.setResources(resourceService.packageResourceColumn(resourceMapper.findByResourceId(resourceFavorite.getResourceId())));
	    }
	}
    }

    /**
     * 查询用户资源观看记录
     * 
     * @param semesterId
     * @param classlevelId
     * @param disciplineId
     * @param resourceCatalogFirstId
     * @param resourceName
     * @param resourceColumnId
     * @param startTime
     * @param endTime
     * @param userId
     * @param page
     * @return
     */
    public Pageable<ResourceHistory> findHistoryResource(String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String resourceName, String resourceColumnId, Date startTime, Date endTime, String userId, Page page) {
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
	if (StringUtils.isNotEmpty(userId)) {
	    eqMap.put("baseUserId", userId);
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

	// 资源浏览记录
	List<ResourceHistory> listResourceHistory = resourceHistoryDao.findListByPage(resourceHistoryDao.createCriteria(eqMap, lteMap, gteMap, regexMap), listOrder, pageIndex, pageSize);

	// 封装资源实体
	packageListResourceHistory(listResourceHistory);

	// 封装分页
	Pageable<ResourceHistory> pageable = new SimplePageable<ResourceHistory>();
	pageable.addData(listResourceHistory);
	pageable.setTotalRecords(resourceHistoryDao.findCount(resourceHistoryDao.createCriteria(eqMap, lteMap, gteMap, regexMap)));
	// 当前页
	pageable.setCurrentPage(page.getCurrentPage());
	// 每页的数量
	pageable.setPageCountData(page.getPageCountData());
	// 总页数
	pageable.setTotalPages(pageable.calTotalPages());

	return pageable;
    }

    /**
     * 封装资源实体
     * 
     * @param listResourceHistory
     */
    private void packageListResourceHistory(List<ResourceHistory> listResourceHistory) {
	if (CollectionUtils.isNotEmpty(listResourceHistory)) {
	    for (ResourceHistory resourceHistory : listResourceHistory) {
		resourceHistory.setResources(resourceService.packageResourceColumn(resourceMapper.findByResourceId(resourceHistory.getResourceId())));
	    }
	}
    }

    /**
     * 增加用户上传的资源
     * 
     * @param resource
     * @param resourceCatalogFirstId
     * @param knowledgeIds
     * @param images
     * @param userId
     * @param labels
     * @param resourceImages
     * @return
     * @throws NoSuchAlgorithmException
     */
    public boolean addResource(Resources resource, List<String> knowledgeIds, List<BaseAttacheFile> images, String userId, String holdType, String labels, String resourceImages) throws NoSuchAlgorithmException {
	// 设置资源ID
	resource.setId(UUID.randomUUID().toString());
	resource.setResourceSource(Constants.RESOURCE_SOURCE_SPACE); // 空间资源
	resource.setTransferFlag(Constants.RESOURCE_TRANSFER_FLAG_NO);
	resource.setCommentCount(0);
	resource.setCreateTime(new Date());
	resource.setDownloadCount(0);
	resource.setEvaluate(0.0); // 评分
	resource.setEvaluateCount(0.0);
	resource.setEvaluateAvg(0.0);
	resource.setViewCount(0);
	resource.setFavoriteCount(0);

	// 资源栏目
	ResourceColumn resourceColumn = skeletonService.findResourceColumnById(resource.getResourceColumnId());
	if (resourceColumn.getColumnType().equals(Constants.RESOURCE_COLUMN_MIXTURE) && !resource.getFunType().equals(Constants.FUN_TYPE_VIDEO)) {
	    resource.setTransFlag(Constants.TRANS_SUCCESS);
	} else {
	    resource.setTransFlag(Constants.TRANS_PENDDING);
	}
	String filename = resource.getHighDefine();
	if (StringUtils.isBlank(filename))
	    filename = resource.getNormalDefine();
	if (StringUtils.isNotBlank(filename)) {
	    String fullFilename = VideoUtil.buildPath(filename);
	    File source = new File(fullFilename);
	    Encoder encoder = new Encoder();
	    try {
		MultimediaInfo m = encoder.getInfo(source);
		int ls = (int) (m.getDuration() / 1000);
		resource.setDuration(ls);
	    } catch (Exception e) {
		resource.setDuration(-1);
		e.printStackTrace();
	    }
	}
	resource.setBaseUserId(userId);
	resource.setDeleteFlag(Constants.DELETE_NOT_DELETE);

	String holdId = userId;
	if (holdType.equals(Constants.HOLD_TYPE_SCHOOL)) {
	    List<String> listSchoolId = baseUserMapper.findSchoolIdBySchoolUserId(userId);
	    if (CollectionUtils.isNotEmpty(listSchoolId)) {
		holdId = listSchoolId.get(0);
	    }
	    resource.setOrgSource(Constants.ORG_SOURCE_SCHOOL);
	} else if (holdType.equals(Constants.HOLD_TYPE_ORG)) {
	    List<BaseOrg> listOrg = baseUserMapper.findOrgByOrgUserId(userId);
	    if (CollectionUtils.isNotEmpty(listOrg)) {
		BaseOrg baseOrg = listOrg.get(0);
		holdId = baseOrg.getId();
		if (Constants.ORG_LEVEL_ONE.equals(baseOrg.getOrgLevel())) {
		    resource.setOrgSource(Constants.ORG_SOURCE_ORG_LEVEL_ONE);
		} else if (Constants.ORG_LEVEL_TWO.equals(baseOrg.getOrgLevel())) {
		    resource.setOrgSource(Constants.ORG_SOURCE_ORG_LEVEL_TWO);
		} else if (Constants.ORG_LEVEL_THREE.equals(baseOrg.getOrgLevel())) {
		    resource.setOrgSource(Constants.ORG_SOURCE_ORG_LEVEL_THREE);
		}
	    }
	} else {
	    resource.setOrgSource(Constants.ORG_SOURCE_SCHOOL);
	}

	resource.setHoldType(holdType);
	resource.setHoldId(holdId);
	// 保存资源
	resourceMapper.save(resource);

	// 保存资源的知识点
	if (CollectionUtils.isNotEmpty(knowledgeIds)) {
	    saveResourceKnowledge(resource.getId(), knowledgeIds);
	}

	// 保存资源标签
	if (StringUtils.isNotEmpty(labels)) {
	    saveResourceLabel(resource.getId(), labels);
	}

	// 保存资源图片
	if (StringUtils.isNotEmpty(resourceImages)) {
	    saveResourceImage(resource.getId(), resourceImages);
	}

	return true;
    }

    /**
     * 修改资源
     * 
     * @param resource
     * @param knowledgeIds
     * @return
     */
    public boolean editResource(Resources resource, Resources oldResource, List<String> knowledgeIds) {
	// 学段不能修改
	if (StringUtils.isNotEmpty(oldResource.getBaseSemesterId()) && !oldResource.getBaseSemesterId().equals(resource.getBaseSemesterId())) {
	    return false;
	}

	// 年级不能修改
	if (StringUtils.isNotEmpty(oldResource.getBaseClasslevelId()) && !oldResource.getBaseClasslevelId().equals(resource.getBaseClasslevelId())) {
	    return false;
	}

	// 学科不能修改
	if (StringUtils.isNotEmpty(oldResource.getBaseDisciplineId()) && !oldResource.getBaseDisciplineId().equals(resource.getBaseDisciplineId())) {
	    return false;
	}

	// 如果缩略图被修改
	if (StringUtils.isNotEmpty(oldResource.getThumb()) && !oldResource.getThumb().equals(resource.getThumb())) {
	    MongoTemplate template = SpringContext.getBean(MongoTemplate.class);
	    DB db = template.getDb();
	    GridFS gfs = new GridFS(db, "resourceImages");
	    gfs.remove(oldResource.getThumb());
	}

	// 保存资源
	resourceMapper.updateResource(resource);

	// 删除资源相关知识点
	resourceKnowledgeMapper.deleteByResourceId(resource.getId());
	if (CollectionUtils.isNotEmpty(knowledgeIds)) {
	    // 保存资源的知识点
	    saveResourceKnowledge(resource.getId(), knowledgeIds);
	}

	String labels = resource.getLabels();
	// 删除资源相关标签
	resourceLabelMapper.deleteRLRByResourceId(resource.getId());
	if (StringUtils.isNotEmpty(labels)) {
	    // 保存资源标签
	    saveResourceLabel(resource.getId(), labels);
	}

	return true;
    }

    /**
     * 保存资源的知识点
     */
    private void saveResourceKnowledge(String resourceId, List<String> knowledgeIds) {
	for (String knowledgeId : knowledgeIds) {
	    ResourceKnowledge resourceKnowledge = new ResourceKnowledge();
	    resourceKnowledge.setId(UUID.randomUUID().toString());
	    resourceKnowledge.setResourceId(resourceId);
	    resourceKnowledge.setBaseKnowledgeId(knowledgeId);

	    List<BaseKnowledge> listParentKnowledge = skeletonService.findAllParentKnowledge(knowledgeId);
	    if (CollectionUtils.isEmpty(listParentKnowledge)) {
		continue;
	    }

	    int size = listParentKnowledge.size();
	    for (int i = size - 1; i >= 0; i--) {
		BaseKnowledge baseKnowledge = listParentKnowledge.get(i);
		String baseKnowledgeId = baseKnowledge.getId();
		if (i == (size - 1)) {
		    resourceKnowledge.setFirstBaseKnowledgeId(baseKnowledgeId);
		    resourceKnowledge.setBaseSemesterId(baseKnowledge.getBaseSemesterId());
		    resourceKnowledge.setBaseDisciplineId(baseKnowledge.getBaseDisciplineId());
		} else if (i == (size - 2)) {
		    resourceKnowledge.setSecondBaseKnowledgeId(baseKnowledgeId);
		} else if (i == (size - 3)) {
		    resourceKnowledge.setThirdBaseKnowledgeId(baseKnowledgeId);
		} else if (i == (size - 4)) {
		    resourceKnowledge.setFourthBaseKnowledgeId(baseKnowledgeId);
		} else if (i == (size - 5)) {
		    resourceKnowledge.setFifthBaseKnowledgeId(baseKnowledgeId);
		} else if (i == (size - 6)) {
		    resourceKnowledge.setSixthBaseKnowledgeId(baseKnowledgeId);
		} else {
		    break;
		}
	    }

	    // 保存资源的知识点
	    resourceKnowledgeMapper.saveResourceKnowledge(resourceKnowledge);
	}
    }

    /**
     * 保存资源标签
     * 
     * @param resourceId
     * @param labels
     */
    private void saveResourceLabel(String resourceId, String labels) {
	String[] labelArray = labels.split(Constants.RESOURCE_LABEL_SEPARATER);
	for (String label : labelArray) {
	    ResourceLabel resourceLabel = resourceLabelMapper.findByLabelName(label);
	    if (resourceLabel == null || StringUtils.isEmpty(resourceLabel.getId())) {
		resourceLabel = new ResourceLabel();
		resourceLabel.setId(UUID.randomUUID().toString());
		resourceLabel.setResourceLabelName(label);

		// 保存标签
		resourceLabelMapper.saveResourceLabel(resourceLabel);
	    }

	    Map<String, Object> parMap = new HashMap<String, Object>();
	    parMap.put("id", UUID.randomUUID().toString());
	    parMap.put("resourceId", resourceId);
	    parMap.put("resourceLabelId", resourceLabel.getId());

	    // 保存资源与标签关系
	    resourceLabelMapper.saveResourceLabelResources(parMap);
	}
    }

    /**
     * 添加资源图片
     * 
     * @param resourceId
     * @param resourceImages
     */
    private void saveResourceImage(String resourceId, String resourceImages) {
	String[] imageNames = resourceImages.split(Constants.RESOURCE_IMAGE_SEPARATER);

	for (String imageName : imageNames) {
	    if (StringUtils.isEmpty(imageName)) {
		continue;
	    }
	    ResourceImage resourceImage = new ResourceImage();
	    resourceImage.setId(UUID.randomUUID().toString());
	    resourceImage.setImageName(imageName);
	    resourceImage.setResourceId(resourceId);

	    resourceImageMapper.saveResourceImage(resourceImage);
	}
    }

    /**
     * 用户删除资源，资源的状态设置为Resource.DELETE_BY_USER
     * 
     * @param resourceId
     * @param userId
     * @return
     */
    public boolean deleteResource(String resourceId, String userId) {
	Resources resource = resourceMapper.findByResourceId(resourceId);
	BaseUser user = baseUserMapper.findByBaseUserId(userId);
	if (resource != null) {
	    resource.setDeleteFlag(Constants.DELETE_BY_USER);
	    resource.setDeleteBaseUserId(user.getId());
	    resource.setDeleteTime(new Date());
	    resourceMapper.deleteResource(resource);

	    // 删除Mongodb中相关数据
	    resourceHistoryDao.deleteByResourceId(resourceId);
	    resourceHoldDao.deleteByResourceId(resourceId);
	    resourceFavoriteDao.deleteByResourceId(resourceId);
	    resourcePushDao.deleteByResourceId(resourceId);
	    resourceDistributeTargetDao.deleteByResourceId(resourceId);
	    resourceDistributeDao.deleteByResourceId(resourceId);

	    return true;
	}
	return false;
    }

    /**
     * 删除浏览历史记录
     * 
     * @param resourceId
     * @param userId
     * @return
     */
    public boolean deleteHistory(String resourceId, String userId) {
	if (StringUtils.isNotEmpty(resourceId) && StringUtils.isNotEmpty(userId)) {
	    resourceHistoryDao.deleteByResourceIdAndUserId(resourceId, userId);
	}

	return true;
    }

    /**
     * 取消资源的收藏
     * 
     * @param id
     *            Resource的id
     * @param userId
     *            当前用户id
     * @return
     */
    public boolean cancelFavorite(String resourceId, String userId) {
	if (StringUtils.isNotEmpty(resourceId) && StringUtils.isNotEmpty(userId)) {
	    resourceFavoriteDao.deleteByResourceIdAndUserId(resourceId, userId);

	    Resources resource = resourceMapper.findByResourceId(resourceId);
	    Map<String, Object> parMap = new HashMap<String, Object>();
	    parMap.put("id", resourceId);
	    parMap.put("favoriteCount", resource.getFavoriteCount() - 1);
	    resourceMapper.updateResourceCount(parMap);
	}

	return true;
    }

    /**
     * 查询学校或机构的共享资源
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
    public Pageable<ResourceHold> findHoldResource(String semesterId, String classlevelId, String disciplineId, String resourceCatalogFirstId, String resourceCatalogSecondId, String resourceName, String resourceColumnId, Date startTime, Date endTime, String holdType, String holdId, Page page) {
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
	if (StringUtils.isNotEmpty(holdId)) {
	    eqMap.put("holdId", holdId);
	}
	if (StringUtils.isNotEmpty(holdType)) {
	    eqMap.put("holdType", holdType);
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

	// 资源共享记录
	List<ResourceHold> listResourceHold = resourceHoldDao.findListByPage(resourceHoldDao.createCriteria(eqMap, lteMap, gteMap, regexMap), listOrder, pageIndex, pageSize);

	// 封装资源共享
	packageListResourceHold(listResourceHold);

	// 封装分页
	Pageable<ResourceHold> pageable = new SimplePageable<ResourceHold>();
	pageable.addData(listResourceHold);
	pageable.setTotalRecords(resourceHoldDao.findCount(resourceHoldDao.createCriteria(eqMap, lteMap, gteMap, regexMap)));
	// 当前页
	pageable.setCurrentPage(page.getCurrentPage());
	// 每页的数量
	pageable.setPageCountData(page.getPageCountData());
	// 总页数
	pageable.setTotalPages(pageable.calTotalPages());

	return pageable;
    }

    /**
     * 封装资源共享列表
     * 
     * @param listResourceHold
     */
    private void packageListResourceHold(List<ResourceHold> listResourceHold) {
	if (CollectionUtils.isNotEmpty(listResourceHold)) {
	    for (ResourceHold resourceHold : listResourceHold) {
		resourceHold.setResources(resourceService.packageResourceColumn(resourceMapper.findByResourceId(resourceHold.getResourceId())));
	    }
	}
    }

    /**
     * 增加资源的下载次数
     * 
     * @param resourceId
     * @param userId
     * @return
     */
    public Resources addResourceDownloadCount(String resourceId) {
	Resources resource = resourceMapper.findByResourceId(resourceId);
	if (resource != null) {
	    Map<String, Object> parMap = new HashMap<String, Object>();
	    parMap.put("id", resourceId);
	    parMap.put("downloadCount", resource.getDownloadCount() + 1);

	    resourceMapper.updateResourceCount(parMap);
	}
	return resource;
    }

    /**
     * 根据资源ID查找轻松一刻图片列表
     * 
     * @param resourceId
     * @return
     */
    public List<ResourceImage> findResourceImageByResourceId(String resourceId) {
	return resourceImageMapper.findByResourceId(resourceId);
    }

    /**
     * 收藏资源，返回0表示已经收藏
     * 
     * @param resourceId
     * @param userId
     * @return
     */
    public int favoriteResource(String resourceId, String userId) {
	ResourceFavorite favorite = resourceFavoriteDao.findByResourceIdAndUserId(resourceId, userId);

	if (favorite != null) {
	    return 0;
	}

	Resources resource = resourceMapper.findByResourceId(resourceId);
	if (resource != null) {
	    // 保存资源收藏记录
	    ResourceFavorite resourceFavorite = createResourceFavorite(resource, userId);
	    resourceFavoriteDao.insert(resourceFavorite);

	    // 修改资源收藏次数
	    Map<String, Object> parMap = new HashMap<String, Object>();
	    parMap.put("id", resourceId);
	    parMap.put("favoriteCount", resource.getFavoriteCount() + 1);
	    resourceMapper.updateResourceCount(parMap);
	    return 1;
	}
	return -1;
    }

    /**
     * 生成资源收藏实体
     * 
     * @param resource
     * @param userId
     * @return
     */
    private ResourceFavorite createResourceFavorite(Resources resource, String userId) {
	ResourceFavorite resourceFavorite = new ResourceFavorite();
	resourceFavorite.setBaseClasslevelId(resource.getBaseClasslevelId());
	resourceFavorite.setBaseDisciplineId(resource.getBaseDisciplineId());
	resourceFavorite.setBaseSemesterId(resource.getBaseSemesterId());
	resourceFavorite.setBaseUserId(userId);
	resourceFavorite.setCreateTime(resource.getCreateTime());
	resourceFavorite.setId(UUID.randomUUID().toString());

	resourceFavorite.setResourceCatalogFirstId(resource.getResourceCatalogFirstId());
	resourceFavorite.setResourceCatalogSecondId(resource.getResourceCatalogSecondId());
	resourceFavorite.setResourceCreateTime(resource.getCreateTime());
	resourceFavorite.setResourceId(resource.getId());
	resourceFavorite.setResourceName(resource.getResourceName());
	resourceFavorite.setResourceColumnId(resource.getResourceColumnId());

	return resourceFavorite;
    }

    /**
     * 增加资源的观看记录，并返回对象
     * 
     * @param resourceId
     * @param userId
     * @return
     */
    public Resources addResourceViewCount(String resourceId, String userId) {
	Resources resource = resourceMapper.findByResourceId(resourceId);
	if (resource != null) {
	    Map<String, Object> parMap = new HashMap<String, Object>();
	    parMap.put("id", resourceId);
	    parMap.put("viewCount", resource.getViewCount() + 1);
	    resourceMapper.updateResourceCount(parMap);

	    if (userId != null) {
		// 如果已经看过此资源, 则更新时间, 否则创建记录
		ResourceHistory history = resourceHistoryDao.findByResourceIdAndUserId(resourceId, userId);
		if (history != null) {
		    history.setCreateTime(new Date());
		    resourceHistoryDao.updateCreateTime(history.getId(), new Date());
		} else {
		    // 保存到mongodb中
		    ResourceHistory resourceHistory = createResourceHistory(resource, userId);

		    resourceHistoryDao.insert(resourceHistory);
		}
	    }

	    // 用户
	    BaseUser baseUser = baseUserMapper.findByBaseUserId(resource.getBaseUserId());
	    resource.setBaseUserName(baseUser.getUsername());

	    // 学段
	    if (StringUtils.isNotEmpty(resource.getBaseSemesterId())) {
		BaseSemester baseSemester = skeletonService.findSemesterById(resource.getBaseSemesterId());
		resource.setBaseSemesterName(baseSemester.getSemesterName());
	    }

	    // 年级
	    if (StringUtils.isNotEmpty(resource.getBaseClasslevelId())) {
		BaseClassLevel baseClassLevel = skeletonService.findClasslevelById(resource.getBaseClasslevelId());
		resource.setBaseClassLevelName(baseClassLevel.getClassLevelName());
	    }

	    // 学科
	    if (StringUtils.isNotEmpty(resource.getBaseDisciplineId())) {
		BaseDiscipline baseDiscipline = skeletonService.findDisciplineById(resource.getBaseDisciplineId());
		resource.setBaseDisciplineName(baseDiscipline.getDisciplineName());
	    }

	}
	return resource;
    }

    private ResourceHistory createResourceHistory(Resources resource, String userId) {
	ResourceHistory resourceHistory = new ResourceHistory();
	resourceHistory.setBaseClasslevelId(resource.getBaseClasslevelId());
	resourceHistory.setBaseDisciplineId(resource.getBaseDisciplineId());
	resourceHistory.setBaseSemesterId(resource.getBaseSemesterId());
	resourceHistory.setBaseUserId(userId);
	resourceHistory.setCreateTime(resource.getCreateTime());
	resourceHistory.setId(UUID.randomUUID().toString());

	resourceHistory.setResourceCatalogFirstId(resource.getResourceCatalogFirstId());
	resourceHistory.setResourceCatalogSecondId(resource.getResourceCatalogSecondId());
	resourceHistory.setResourceCreateTime(resource.getCreateTime());
	resourceHistory.setResourceId(resource.getId());
	resourceHistory.setResourceName(resource.getResourceName());
	resourceHistory.setResourceColumnId(resource.getResourceColumnId());
	return resourceHistory;
    }

    public List<ResourceFavorite> listResourceFavorite(String baseUserId) {
	return resourceFavoriteDao.listResourceFavorite(baseUserId);
    }
}
