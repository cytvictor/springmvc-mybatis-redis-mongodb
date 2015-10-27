package com.codyy.rrt.resource.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.mapper.ResourceKnowledgeMapper;
import com.codyy.rrt.resource.mapper.ResourceLabelMapper;
import com.codyy.rrt.resource.mapper.ResourceMapper;
import com.codyy.rrt.resource.model.BaseDiscipline;
import com.codyy.rrt.resource.model.BaseKnowledge;
import com.codyy.rrt.resource.model.BaseResourceCatalog;
import com.codyy.rrt.resource.model.BaseSemester;
import com.codyy.rrt.resource.model.ResourceColumn;
import com.codyy.rrt.resource.model.ResourceKnowledge;
import com.codyy.rrt.resource.model.ResourceLabel;
import com.codyy.rrt.resource.model.Resources;

@Transactional
@Service("resourceService")
public class ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private ResourceLabelMapper resourceLabelMapper;
	@Autowired
	private ResourceKnowledgeMapper resourceKnowledgeMapper;

	@Autowired
	private SkeletonService skeletonService;

	/**
	 * 根据resourceId查询resources
	 * 
	 * @param resourceId
	 * @return
	 */
	public Resources findByResourceId(String resourceId) {
		return resourceMapper.findByResourceId(resourceId);
	}

	/**
	 * 根据资源Id查询资源标签
	 * 
	 * @param resourceId
	 * @return
	 */
	public List<ResourceLabel> findResourceLabelByResourceId(String resourceId) {
		return resourceLabelMapper.findByResourceId(resourceId);
	}

	/**
	 * 根据资源Id查询知识点集合
	 * 
	 * @param resourceId
	 * @return
	 */
	public List<ResourceKnowledge> findResourceKnowledgeByResourceId(
			String resourceId) {
		return resourceKnowledgeMapper.findByResourceId(resourceId);
	}

	/**
	 * 封装资源
	 * 
	 * @param resource
	 * @return
	 */
	public Resources packageResourceLabelAndResourceKnowledge(Resources resource) {
		// 封装资源的知识点
		List<ResourceKnowledge> listResourceKnowledge = this
				.findResourceKnowledgeByResourceId(resource.getId());
		if (CollectionUtils.isNotEmpty(listResourceKnowledge)) {
			for (ResourceKnowledge resourceKnowledge : listResourceKnowledge) {
				List<BaseKnowledge> listBaseKnowledge = skeletonService
						.findAllParentKnowledge(resourceKnowledge
								.getBaseKnowledgeId());
				if (CollectionUtils.isEmpty(listBaseKnowledge)) {
					continue;
				}

				// 知识点字符串
				String knowledges = "";
				int size = listBaseKnowledge.size();
				BaseKnowledge rootKnowledge = listBaseKnowledge.get(size - 1);
				if (rootKnowledge == null) {
					continue;
				}

				BaseSemester baseSemester = skeletonService
						.findSemesterById(rootKnowledge.getBaseSemesterId());
				if (baseSemester == null) {
					continue;
				}

				BaseDiscipline baseDiscipline = skeletonService
						.findDisciplineById(rootKnowledge.getBaseDisciplineId());
				if (baseDiscipline == null) {
					continue;
				}
				knowledges += baseSemester.getSemesterName()
						+ Constants.RESOURCE_KNOWLEDGE_SEPARATER
						+ baseDiscipline.getDisciplineName();

				for (int i = size - 1; i >= 0; i--) {
					BaseKnowledge baseKnowledge = listBaseKnowledge.get(i);
					knowledges += Constants.RESOURCE_KNOWLEDGE_SEPARATER
							+ baseKnowledge.getKnowledgeName();
				}
				resourceKnowledge.setKnowledges(knowledges);
			}
		} else {
			listResourceKnowledge = new ArrayList<ResourceKnowledge>();
		}
		resource.setListResourceKnowledge(listResourceKnowledge);

		// 封装资源的标签
		List<ResourceLabel> listResourceLabel = this
				.findResourceLabelByResourceId(resource.getId());
		if (CollectionUtils.isNotEmpty(listResourceLabel)) {
			String labels = "";
			for (ResourceLabel resourceLabel : listResourceLabel) {
				labels += resourceLabel.getResourceLabelName()
						+ Constants.RESOURCE_LABEL_SEPARATER;
			}

			if (labels.endsWith(Constants.RESOURCE_LABEL_SEPARATER)) {
				labels = labels.substring(0, labels.length() - 1);
			}
			resource.setLabels(labels);
		}
		return resource;
	}

	/**
	 * 封装资源栏目
	 * 
	 * @param colls
	 */
	public Collection<Resources> packageResourceColumn(
			Collection<Resources> coll) {
		if (CollectionUtils.isEmpty(coll)) {
			return coll;
		}

		List<ResourceColumn> allResourceColumn = skeletonService
				.findAllResourceColumn();
		for (Resources resource : coll) {
			if (CollectionUtils.isNotEmpty(allResourceColumn)) {
				for (ResourceColumn resourceColumn : allResourceColumn) {
					if (resource.getResourceColumnId().equals(
							resourceColumn.getId())) {
						// 封装资源栏目
						resource.setResourceColumn(resourceColumn);
						// 如果关联的基础分类，则显示学科名称，否则显示一级资源分类名称
						if (Constants.RESOURCE_COLUMN_RELATION_YES
								.equals(resourceColumn
										.getBaseCatalogKnowledgeFlag())) {
							if (StringUtils.isNotEmpty(resource
									.getBaseDisciplineId())) {
								BaseDiscipline discipline = skeletonService
										.findDisciplineById(resource
												.getBaseDisciplineId());
								if (discipline != null) {
									// 封装学科名称
									resource.setBaseDisciplineName(discipline
											.getDisciplineName());
								}
							}
						} else {
							if (Constants.RESOURCE_COLUMN_RELATION_YES
									.equals(resourceColumn
											.getResourceCatalogFlag())
									&& StringUtils.isNotEmpty(resource
											.getResourceCatalogFirstId())) {
								BaseResourceCatalog resourceCatalog = skeletonService
										.findResourceCatalogById(resource
												.getResourceCatalogFirstId());
								if (resourceCatalog != null) {
									// 封装一级资源分类名称
									resource.setResourceCatalogFirstName(resourceCatalog
											.getCatalogName());
								}
							}
						}
						break;
					}
				}
			}
		}

		return coll;
	}

	/**
	 * 封装资源栏目
	 * 
	 * @param colls
	 */
	public Resources packageResourceColumn(Resources resource) {
		if (resource == null) {
			return resource;
		}

		List<ResourceColumn> allResourceColumn = skeletonService
				.findAllResourceColumn();
		if (CollectionUtils.isNotEmpty(allResourceColumn)) {
			for (ResourceColumn resourceColumn : allResourceColumn) {
				if (resource.getResourceColumnId().equals(
						resourceColumn.getId())) {
					// 封装资源栏目
					resource.setResourceColumn(resourceColumn);
					// 如果关联的基础分类，则显示学科名称，否则显示一级资源分类名称
					if (Constants.RESOURCE_COLUMN_RELATION_YES
							.equals(resourceColumn
									.getBaseCatalogKnowledgeFlag())) {
						if (StringUtils.isNotEmpty(resource
								.getBaseDisciplineId())) {
							BaseDiscipline discipline = skeletonService
									.findDisciplineById(resource
											.getBaseDisciplineId());
							if (discipline != null) {
								// 封装学科名称
								resource.setBaseDisciplineName(discipline
										.getDisciplineName());
							}
						}
					} else {
						if (Constants.RESOURCE_COLUMN_RELATION_YES
								.equals(resourceColumn.getResourceCatalogFlag())
								&& StringUtils.isNotEmpty(resource
										.getResourceCatalogFirstId())) {
							BaseResourceCatalog resourceCatalog = skeletonService
									.findResourceCatalogById(resource
											.getResourceCatalogFirstId());
							if (resourceCatalog != null) {
								// 封装一级资源分类名称
								resource.setResourceCatalogFirstName(resourceCatalog
										.getCatalogName());
							}
						}
					}

					break;
				}
			}
		}

		return resource;
	}

}
