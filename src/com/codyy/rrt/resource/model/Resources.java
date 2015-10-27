package com.codyy.rrt.resource.model;

import java.util.Date;
import java.util.List;

import com.codyy.rrt.base.model.BaseModel;

public class Resources extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4111570472595864139L;
	private String resourceSource; // 资源来源：平台、空间
	private Double resourcePrice; // 资源价格
	private String transferFlag; // 转载状态
	private String baseUserId; // 创建者
	private String baseUserName; // 创建者名称
	private String deleteBaseUserId; // 删除者
	private String baseSemesterId; // 学段Id
	private String baseSemesterName; // 学段名称
	private String baseClasslevelId; // 年级Id
	private String baseClassLevelName; // 年级名称
	private String baseDisciplineId; // 学科Id
	private String baseDisciplineName; // 学科名称
	private String baseVersionId; // 版本Id
	private String baseVersionName; // 版本名称
	private String baseFascicleId; // 分册Id
	private String baseFascicleName; // 分册名称
	private String baseChapterId; // 章Id
	private String baseChapterName; // 章名称
	private String basePartId; // 节Id
	private String basePartName; // 节名称
	private String resourceCatalogFirstId; // 资源分类一级分类Id
	private String resourceCatalogFirstName; // 资源分类一级分类名称
	private String resourceCatalogSecondId; // 资源分类二级分类Id
	private String resourceCatalogSecondName; // 资源分类二级分类名称
	private String resourceColumnId; // 资源栏目Id
	private String resourceColumnType; // 资源栏目类型, 视频类、文档类和混合类
	private String resourceName; // 资源名称
	private String description; // 资源描述
	private String thumb = "1f9d2ab6-752f-4841-833b-85ce2f1dbfa1.png"; // 缩略图
	private String highDefine; // 高清附件
	private String normalDefine; // 普清附件
	private Integer duration; // 时长
	private String studyResource; // 文档附件
	private Long studyResourceSize; // 文档大小
	private String studyResourceType; // 文档类型
	private String commentClosed;
	private Date createTime; // 创建时间
	private String deleteFlag; // 删除状态
	private Date deleteTime; // 删除时间
	private String funType; // 轻松一刻类型
	private Double evaluate; // 评分
	private Double evaluateAvg; // 平均评分
	private Double evaluateCount; // 评分数量
	private String reviewFlag; //
	private String transFlag; // 文档转换状态
	private Integer transPageCount;
	private String funContent; // 轻松一刻文本内容
	private String holdType; // 资源上传所属机构类型
	private String holdId; // 如果是学生或老师，存放对应的BaseUser.id，否则存放BaseSchool或BaseOrg对象的id
	private String orgSource; // 来源机构:学校，省级电教馆，市级电教馆，县级电教馆
	private Integer commentCount; // 评论次数
	private Integer downloadCount; // 下载次数
	private Integer viewCount; // 观看次数
	private Integer favoriteCount; // 收藏次数
	private String author; // 作者
	private String labels; // 标签
	
	//增加额外四个属性：学校,县电教馆和市电教馆以及省电教馆方便跨数据库统计
	private String schoolId;
	private String districtOrgId;
	private String cityOrgId;
	private String provinceOrgId;

	private ResourceColumn resourceColumn; // 资源栏目，显示使用
	private List<ResourceKnowledge> listResourceKnowledge; // 资源的知识点列表，显示使用

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getDistrictOrgId() {
		return districtOrgId;
	}

	public void setDistrictOrgId(String districtOrgId) {
		this.districtOrgId = districtOrgId;
	}

	public String getCityOrgId() {
		return cityOrgId;
	}

	public void setCityOrgId(String cityOrgId) {
		this.cityOrgId = cityOrgId;
	}


	public String getProvinceOrgId() {
		return provinceOrgId;
	}

	public void setProvinceOrgId(String provinceOrgId) {
		this.provinceOrgId = provinceOrgId;
	}

	public String getBaseUserId() {
		return baseUserId;
	}

	public void setBaseUserId(String baseUserId) {
		this.baseUserId = baseUserId;
	}

	public String getBaseUserName() {
		return baseUserName;
	}

	public void setBaseUserName(String baseUserName) {
		this.baseUserName = baseUserName;
	}

	public String getDeleteBaseUserId() {
		return deleteBaseUserId;
	}

	public void setDeleteBaseUserId(String deleteBaseUserId) {
		this.deleteBaseUserId = deleteBaseUserId;
	}

	public String getBaseSemesterId() {
		return baseSemesterId;
	}

	public void setBaseSemesterId(String baseSemesterId) {
		this.baseSemesterId = baseSemesterId;
	}

	public String getBaseClasslevelId() {
		return baseClasslevelId;
	}

	public void setBaseClasslevelId(String baseClasslevelId) {
		this.baseClasslevelId = baseClasslevelId;
	}

	public String getBaseDisciplineId() {
		return baseDisciplineId;
	}

	public void setBaseDisciplineId(String baseDisciplineId) {
		this.baseDisciplineId = baseDisciplineId;
	}

	public String getResourceColumnId() {
		return resourceColumnId;
	}

	public void setResourceColumnId(String resourceColumnId) {
		this.resourceColumnId = resourceColumnId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getHighDefine() {
		return highDefine;
	}

	public void setHighDefine(String highDefine) {
		this.highDefine = highDefine;
	}

	public String getNormalDefine() {
		return normalDefine;
	}

	public void setNormalDefine(String normalDefine) {
		this.normalDefine = normalDefine;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getStudyResource() {
		return studyResource;
	}

	public void setStudyResource(String studyResource) {
		this.studyResource = studyResource;
	}

	public Long getStudyResourceSize() {
		return studyResourceSize;
	}

	public void setStudyResourceSize(Long studyResourceSize) {
		this.studyResourceSize = studyResourceSize;
	}

	public String getStudyResourceType() {
		return studyResourceType;
	}

	public void setStudyResourceType(String studyResourceType) {
		this.studyResourceType = studyResourceType;
	}

	public String getCommentClosed() {
		return commentClosed;
	}

	public void setCommentClosed(String commentClosed) {
		this.commentClosed = commentClosed;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getFunType() {
		return funType;
	}

	public void setFunType(String funType) {
		this.funType = funType;
	}

	public Double getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(Double evaluate) {
		this.evaluate = evaluate;
	}

	public Double getEvaluateAvg() {
		return evaluateAvg;
	}

	public void setEvaluateAvg(Double evaluateAvg) {
		this.evaluateAvg = evaluateAvg;
	}

	public Double getEvaluateCount() {
		return evaluateCount;
	}

	public void setEvaluateCount(Double evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	public String getTransFlag() {
		return transFlag;
	}

	public void setTransFlag(String transFlag) {
		this.transFlag = transFlag;
	}

	public Integer getTransPageCount() {
		return transPageCount;
	}

	public void setTransPageCount(Integer transPageCount) {
		this.transPageCount = transPageCount;
	}

	public String getFunContent() {
		return funContent;
	}

	public void setFunContent(String funContent) {
		this.funContent = funContent;
	}

	public String getHoldType() {
		return holdType;
	}

	public void setHoldType(String holdType) {
		this.holdType = holdType;
	}

	public String getHoldId() {
		return holdId;
	}

	public void setHoldId(String holdId) {
		this.holdId = holdId;
	}

	public String getResourceSource() {
		return resourceSource;
	}

	public void setResourceSource(String resourceSource) {
		this.resourceSource = resourceSource;
	}

	public Double getResourcePrice() {
		return resourcePrice;
	}

	public void setResourcePrice(Double resourcePrice) {
		this.resourcePrice = resourcePrice;
	}

	public String getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	public String getBaseVersionId() {
		return baseVersionId;
	}

	public void setBaseVersionId(String baseVersionId) {
		this.baseVersionId = baseVersionId;
	}

	public String getBaseFascicleId() {
		return baseFascicleId;
	}

	public void setBaseFascicleId(String baseFascicleId) {
		this.baseFascicleId = baseFascicleId;
	}

	public String getBaseChapterId() {
		return baseChapterId;
	}

	public void setBaseChapterId(String baseChapterId) {
		this.baseChapterId = baseChapterId;
	}

	public String getBasePartId() {
		return basePartId;
	}

	public void setBasePartId(String basePartId) {
		this.basePartId = basePartId;
	}

	public String getResourceCatalogFirstId() {
		return resourceCatalogFirstId;
	}

	public void setResourceCatalogFirstId(String resourceCatalogFirstId) {
		this.resourceCatalogFirstId = resourceCatalogFirstId;
	}

	public String getResourceCatalogSecondId() {
		return resourceCatalogSecondId;
	}

	public void setResourceCatalogSecondId(String resourceCatalogSecondId) {
		this.resourceCatalogSecondId = resourceCatalogSecondId;
	}

	public String getResourceColumnType() {
		return resourceColumnType;
	}

	public void setResourceColumnType(String resourceColumnType) {
		this.resourceColumnType = resourceColumnType;
	}

	public String getReviewFlag() {
		return reviewFlag;
	}

	public void setReviewFlag(String reviewFlag) {
		this.reviewFlag = reviewFlag;
	}

	public String getBaseSemesterName() {
		return baseSemesterName;
	}

	public void setBaseSemesterName(String baseSemesterName) {
		this.baseSemesterName = baseSemesterName;
	}

	public String getBaseClassLevelName() {
		return baseClassLevelName;
	}

	public void setBaseClassLevelName(String baseClassLevelName) {
		this.baseClassLevelName = baseClassLevelName;
	}

	public String getBaseDisciplineName() {
		return baseDisciplineName;
	}

	public void setBaseDisciplineName(String baseDisciplineName) {
		this.baseDisciplineName = baseDisciplineName;
	}

	public String getBaseFascicleName() {
		return baseFascicleName;
	}

	public void setBaseFascicleName(String baseFascicleName) {
		this.baseFascicleName = baseFascicleName;
	}

	public String getBaseChapterName() {
		return baseChapterName;
	}

	public void setBaseChapterName(String baseChapterName) {
		this.baseChapterName = baseChapterName;
	}

	public String getBasePartName() {
		return basePartName;
	}

	public void setBasePartName(String basePartName) {
		this.basePartName = basePartName;
	}

	public String getResourceCatalogFirstName() {
		return resourceCatalogFirstName;
	}

	public void setResourceCatalogFirstName(String resourceCatalogFirstName) {
		this.resourceCatalogFirstName = resourceCatalogFirstName;
	}

	public String getResourceCatalogSecondName() {
		return resourceCatalogSecondName;
	}

	public void setResourceCatalogSecondName(String resourceCatalogSecondName) {
		this.resourceCatalogSecondName = resourceCatalogSecondName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public ResourceColumn getResourceColumn() {
		return resourceColumn;
	}

	public void setResourceColumn(ResourceColumn resourceColumn) {
		this.resourceColumn = resourceColumn;
	}

	public List<ResourceKnowledge> getListResourceKnowledge() {
		return listResourceKnowledge;
	}

	public void setListResourceKnowledge(
			List<ResourceKnowledge> listResourceKnowledge) {
		this.listResourceKnowledge = listResourceKnowledge;
	}

	public String getOrgSource() {
		return orgSource;
	}

	public void setOrgSource(String orgSource) {
		this.orgSource = orgSource;
	}

	public String getBaseVersionName() {
		return baseVersionName;
	}

	public void setBaseVersionName(String baseVersionName) {
		this.baseVersionName = baseVersionName;
	}

}
