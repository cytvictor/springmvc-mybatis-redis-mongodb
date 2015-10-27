package com.codyy.rrt.resource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.model.BaseCatalog;
import com.codyy.rrt.resource.model.BaseClassLevel;
import com.codyy.rrt.resource.model.BaseDiscipline;
import com.codyy.rrt.resource.model.BaseKnowledge;
import com.codyy.rrt.resource.model.BaseResourceCatalog;
import com.codyy.rrt.resource.model.BaseSemester;
import com.codyy.rrt.resource.model.BaseVersion;
import com.codyy.rrt.resource.service.SkeletonService;

/**
 * 基础分类、知识点、关联资源分类等基础数据
 * 
 * @author kongchuifang
 * @date 2014-11-18
 */
@Controller
@RequestMapping(value = "/skeleton")
public class SkeletonController {

	@Autowired
	private SkeletonService	skeletonService;

	/**
	 * 获取学段列表
	 * 
	 * @return 学段列表
	 */
	@RequestMapping("/getAllSemester")
	@ResponseBody
	public List<BaseSemester> getAllSemesters() {
		List<BaseSemester> semesters = skeletonService.findAllSemester();
		return semesters;
	}

	/**
	 * 获取年级列表
	 * 
	 * @param semesterId
	 *            学段id
	 * @return 年级列表
	 */
	@RequestMapping("/getClasslevels")
	@ResponseBody
	public List<BaseClassLevel> getClasslevels(String semesterId) {
		List<BaseClassLevel> classlevels = skeletonService.findClasslevelBySemester(semesterId);
		return classlevels;
	}

	/**
	 * 获取全部学科
	 * 
	 * @return
	 */
	@RequestMapping("/getAllDiscipline")
	@ResponseBody
	public List<BaseDiscipline> getAllDiscipline() {
		return skeletonService.findAllDiscipline();
	}

	/**
	 * 根据年级获取学科列表
	 * 
	 * @param classlevelId
	 *            年级id
	 * @return 学科列表
	 */
	@RequestMapping("/getDisciplines")
	@ResponseBody
	public List<BaseDiscipline> getDisciplines(String classlevelId) {
		List<BaseDiscipline> disciplines = skeletonService.findDisciplineByClassLevelId(classlevelId);
		return disciplines;
	}

	/**
	 * 根据年级学科Id查询版本列表
	 * 
	 * @param classlevelId
	 * @param disciplineId
	 * @return
	 */
	@RequestMapping("/getVersions")
	@ResponseBody
	public List<BaseVersion> getVersions(String classlevelId, String disciplineId) {
		List<BaseVersion> disciplines = skeletonService.findVersionByClasslevelIdAndDisciplineId(classlevelId, disciplineId);
		return disciplines;
	}

	/**
	 * 根据版本年级学科Id查询分册列表
	 * 
	 * @param classlevelId
	 * @param disciplineId
	 * @param versionId
	 * @return
	 */
	@RequestMapping("/getFascicles")
	@ResponseBody
	public List<BaseCatalog> getFascicles(String classlevelId, String disciplineId, String versionId) {
		List<BaseCatalog> disciplines = skeletonService.findFascicleByVCD(classlevelId, disciplineId, versionId);
		return disciplines;
	}

	/**
	 * 根据父级Id查询章、节
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/getCatalogs")
	@ResponseBody
	public List<BaseCatalog> getCatalogs(String parentId) {
		List<BaseCatalog> disciplines = skeletonService.findCatalogByParentId(parentId);
		return disciplines;
	}

	/**
	 * 根据学段Id、学科Id查询一级知识点列表
	 * 
	 * @param semesterId
	 * @param disciplineId
	 * @return
	 */
	@RequestMapping("/getRootKnowledges")
	@ResponseBody
	public List<BaseKnowledge> getRootKnowledges(String semesterId, String disciplineId) {
		List<BaseKnowledge> knowledges = skeletonService.findKnowledgeBySemesterIdAndDisciplineIdAndParentId(semesterId, disciplineId, Constants.ROOT_NODE_ID);
		return knowledges;
	}

	/**
	 * 根据parentId查询知识点列表
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/getKnowledges")
	@ResponseBody
	public List<BaseKnowledge> getKnowledges(String parentId) {
		List<BaseKnowledge> knowledges = skeletonService.findKnowledgeByParentId(parentId);
		return knowledges;
	}

	/**
	 * 根据资源栏目Id查询根资源分类
	 * 
	 * @param resourceColumnId
	 * @return
	 */
	@RequestMapping("/getRootResourceCatalogs")
	@ResponseBody
	public List<BaseResourceCatalog> getRootResourceCatalogs(String resourceColumnId) {
		return skeletonService.findRootResourceCatalog(resourceColumnId);
	}

	/**
	 * 根据资源分类父Id查询资源分类
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/getResourceCatalogs")
	@ResponseBody
	public List<BaseResourceCatalog> getResourceCatalogs(String parentId) {
		return skeletonService.findResourceCatalogByParentId(parentId);
	}

}
