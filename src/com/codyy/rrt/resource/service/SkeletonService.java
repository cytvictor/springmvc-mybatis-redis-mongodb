package com.codyy.rrt.resource.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codyy.rrt.baseinfo.mapper.SkeletonMapper;
import com.codyy.rrt.resource.model.BaseCatalog;
import com.codyy.rrt.resource.model.BaseClassLevel;
import com.codyy.rrt.resource.model.BaseDiscipline;
import com.codyy.rrt.resource.model.BaseKnowledge;
import com.codyy.rrt.resource.model.BaseResourceCatalog;
import com.codyy.rrt.resource.model.BaseSemester;
import com.codyy.rrt.resource.model.BaseVersion;
import com.codyy.rrt.resource.model.ResourceColumn;

/**
 * 基础数据Service
 * 
 * @author kongchuifang
 * @date 2014-11-18
 */
@Service("skeletonService")
public class SkeletonService {

	@Autowired
	private SkeletonMapper	skeletonMapper;

	/**
	 * 获取学段列表
	 * 
	 * @return
	 */
	public List<BaseSemester> findAllSemester() {
		return skeletonMapper.findAllSemester();
	}

	/**
	 * 根据Id查询学段
	 * 
	 * @param semesterId
	 * @return
	 */
	public BaseSemester findSemesterById(String semesterId) {
		return skeletonMapper.findSemesterById(semesterId);
	}

	/**
	 * 获取年级列表
	 * 
	 * @param semesterId
	 * @return
	 */
	public List<BaseClassLevel> findClasslevelBySemester(String semesterId) {
		return skeletonMapper.findClasslevelBySemesterId(semesterId);
	}

	/**
	 * 根据Id查询年级
	 * 
	 * @param classlevelId
	 * @return
	 */
	public BaseClassLevel findClasslevelById(String classlevelId) {
		return skeletonMapper.findClasslevelById(classlevelId);
	}

	/**
	 * 获取所有学科
	 * 
	 * @return
	 */
	public List<BaseDiscipline> findAllDiscipline() {
		return skeletonMapper.findAllDiscipline();
	}

	/**
	 * 根据年级获取学科列表
	 * 
	 * @param classlevelId
	 * @return
	 */
	public List<BaseDiscipline> findDisciplineByClassLevelId(String classlevelId) {
		return skeletonMapper.findDisciplineByClassLevelId(classlevelId);
	}

	/**
	 * 根据Id查询学科
	 * 
	 * @param disciplineId
	 * @return
	 */
	public BaseDiscipline findDisciplineById(String disciplineId) {
		return skeletonMapper.findDisciplineById(disciplineId);
	}

	/**
	 * 根据年级学科Id查询版本列表
	 * 
	 * @param classlevelId
	 * @param disciplineId
	 * 
	 * @return
	 */
	public List<BaseVersion> findVersionByClasslevelIdAndDisciplineId(String classlevelId, String disciplineId) {
		Map<String, Object> parMap = new HashMap<String, Object>();
		parMap.put("classlevelId", classlevelId);
		parMap.put("disciplineId", disciplineId);
		return skeletonMapper.findVersionByClasslevelIdAndDisciplineId(parMap);
	}

	/**
	 * 根据版本年级学科Id查询分册列表
	 * 
	 * @param classlevelId
	 * @param disciplineId
	 * @param versionId
	 * @return
	 */
	public List<BaseCatalog> findFascicleByVCD(String classlevelId, String disciplineId, String versionId) {
		Map<String, Object> parMap = new HashMap<String, Object>();
		parMap.put("classlevelId", classlevelId);
		parMap.put("disciplineId", disciplineId);
		parMap.put("versionId", versionId);
		return skeletonMapper.findFascicleByVCD(parMap);
	}

	/**
	 * 根据父级Id查询章、节
	 * 
	 * @param parentId
	 * @return
	 */
	public List<BaseCatalog> findCatalogByParentId(String parentId) {
		return skeletonMapper.findCatalogByParentId(parentId);
	}

	/**
	 * 根据学段Id、学科Id和parentId查询知识点列表
	 * 
	 * @param parMap
	 * @return
	 */
	public List<BaseKnowledge> findKnowledgeBySemesterIdAndDisciplineIdAndParentId(String semesterId, String disciplineId, String parentId) {
		Map<String, Object> parMap = new HashMap<String, Object>();
		parMap.put("semesterId", semesterId);
		parMap.put("disciplineId", disciplineId);
		parMap.put("parentId", parentId);
		return skeletonMapper.findKnowledgeBySemesterIdAndDisciplineIdAndParentId(parMap);
	}

	/**
	 * 根据parentId查询知识点列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<BaseKnowledge> findKnowledgeByParentId(String parentId) {
		return skeletonMapper.findKnowledgeByParentId(parentId);
	}

	/**
	 * 根据知识点Id递归查询所有父级知识点列表，包含本身知识点
	 * 
	 * @param knowledgeId
	 * @return
	 */
	public List<BaseKnowledge> findAllParentKnowledge(String knowledgeId) {
		return skeletonMapper.findAllParentKnowledge(knowledgeId);
	}

	/**
	 * 查询所有资源栏目类型
	 * 
	 * @return
	 */
	public List<ResourceColumn> findAllResourceColumn() {
		List<ResourceColumn> listResourceColumn = skeletonMapper.findAllResourceColumn();
		if (CollectionUtils.isNotEmpty(listResourceColumn)) {
			for (ResourceColumn resourceColumn : listResourceColumn) {
				resourceColumn.setColumnTypeLower(resourceColumn.getColumnType().toLowerCase());
			}
		}
		return listResourceColumn;
	}

	/**
	 * 根据Id查询资源栏目
	 * 
	 * @param resourceColumnId
	 * @return
	 */
	public ResourceColumn findResourceColumnById(String resourceColumnId) {
		return skeletonMapper.findResourceColumnById(resourceColumnId);
	}

	/**
	 * 根据资源栏目查找根资源分类列表
	 * 
	 * @param resourceColumnId
	 * @return
	 */
	public List<BaseResourceCatalog> findRootResourceCatalog(String resourceColumnId) {
		return skeletonMapper.findRootResourceCatalog(resourceColumnId);
	}

	/**
	 * 根据资源分类父级Id查找资源分类列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<BaseResourceCatalog> findResourceCatalogByParentId(String parentId) {
		return skeletonMapper.findResourceCatalogByParentId(parentId);
	}

	/**
	 * 根据Id查询资源分类
	 * 
	 * @param resourceCatalogId
	 * @return
	 */
	public BaseResourceCatalog findResourceCatalogById(String resourceCatalogId) {
		return skeletonMapper.findResourceCatalogById(resourceCatalogId);
	}
}
