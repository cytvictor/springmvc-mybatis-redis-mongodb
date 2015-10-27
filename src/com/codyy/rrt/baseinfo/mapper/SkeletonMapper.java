package com.codyy.rrt.baseinfo.mapper;

import java.util.List;
import java.util.Map;

import com.codyy.rrt.resource.model.BaseCatalog;
import com.codyy.rrt.resource.model.BaseClassLevel;
import com.codyy.rrt.resource.model.BaseDiscipline;
import com.codyy.rrt.resource.model.BaseKnowledge;
import com.codyy.rrt.resource.model.BaseResourceCatalog;
import com.codyy.rrt.resource.model.BaseSemester;
import com.codyy.rrt.resource.model.BaseVersion;
import com.codyy.rrt.resource.model.ResourceColumn;

public interface SkeletonMapper {

	/**
	 * 获取学段列表
	 * 
	 * @return
	 */
	public List<BaseSemester> findAllSemester();

	/**
	 * 根据Id查询学段
	 * 
	 * @param semesterId
	 * @return
	 */
	public BaseSemester findSemesterById(String semesterId);

	/**
	 * 获取年级列表
	 * 
	 * @param semesterId
	 * @return
	 */
	public List<BaseClassLevel> findClasslevelBySemesterId(String semesterId);

	/**
	 * 根据Id查询年级
	 * 
	 * @param classlevelId
	 * @return
	 */
	public BaseClassLevel findClasslevelById(String classlevelId);

	/**
	 * 根据年级获取学科列表
	 * 
	 * @param classlevelId
	 * @return
	 */
	public List<BaseDiscipline> findDisciplineByClassLevelId(String classlevelId);

	/**
	 * 根据Id查询学科
	 * 
	 * @param disciplineId
	 * @return
	 */
	public BaseDiscipline findDisciplineById(String disciplineId);

	/**
	 * 查询所有学科
	 * 
	 * @return
	 */
	public List<BaseDiscipline> findAllDiscipline();

	/**
	 * 根据年级学科Id查询版本列表
	 * 
	 * @param parMap
	 * @return
	 */
	public List<BaseVersion> findVersionByClasslevelIdAndDisciplineId(
			Map<String, Object> parMap);

	/**
	 * 根据版本年级学科Id查询分册列表
	 * 
	 * @param parMap
	 * @return
	 */
	public List<BaseCatalog> findFascicleByVCD(Map<String, Object> parMap);

	/**
	 * 根据父级Id查询章、节
	 * 
	 * @param parentId
	 * @return
	 */
	public List<BaseCatalog> findCatalogByParentId(String parentId);

	/**
	 * 根据学段Id、学科Id和parentId查询知识点列表
	 * 
	 * @param parMap
	 * @return
	 */
	public List<BaseKnowledge> findKnowledgeBySemesterIdAndDisciplineIdAndParentId(
			Map<String, Object> parMap);

	/**
	 * 根据parentId查询知识点列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<BaseKnowledge> findKnowledgeByParentId(String parentId);

	/**
	 * 根据知识点Id递归查询所有父级知识点列表，包含本身知识点
	 * 
	 * @param knowledgeId
	 * @return
	 */
	public List<BaseKnowledge> findAllParentKnowledge(String knowledgeId);

	/**
	 * 查询所有资源栏目
	 * 
	 * @return
	 */
	public List<ResourceColumn> findAllResourceColumn();

	/**
	 * 根据资源栏目Id查询资源栏目
	 * 
	 * @param resourceColumnId
	 * @return
	 */
	public ResourceColumn findResourceColumnById(String resourceColumnId);

	/**
	 * 根据资源栏目查找根资源分类列表
	 * 
	 * @param resourceColumnId
	 * @return
	 */
	public List<BaseResourceCatalog> findRootResourceCatalog(
			String resourceColumnId);

	/**
	 * 根据Id查询资源分类
	 * 
	 * @param resourceCatalogId
	 * @return
	 */
	public BaseResourceCatalog findResourceCatalogById(String resourceCatalogId);

	/**
	 * 根据资源分类父级Id查找资源分类列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<BaseResourceCatalog> findResourceCatalogByParentId(
			String parentId);
}
