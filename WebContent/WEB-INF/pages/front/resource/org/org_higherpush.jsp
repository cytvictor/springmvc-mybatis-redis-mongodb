<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
					<c:if test="${empty list}">
						<center><p class="searchNone">抱歉！没有搜索到您想要的信息！</p></center>
					</c:if>
					<c:forEach items="${list}" var="target">
					<div class="sourceList2 clearfix">
						<span class="sourcePhoto">
							<a href="${ctx}/front/respub/view/${target.resources.id}.html" target="_blank">
							<img src="${ctx}/ResourceImageServlet/${target.resources.thumb}" height="90";>
							</a>
						</span>
						<div class="sourceDetail xelIcon">
							<div class="sourceEdit">
								<c:if test="${empty target.nextResourceDistributeId}">
								<a href="javascript:distributedToSchoolAndOrg('${target.id}');">分发给下级机构</a>
								</c:if>
							</div>
							<ul>
								<li class="name">
									
									<span>
									<a href="${ctx}/front/respub/view/${target.resources.id}.html" target="_blank">
									${target.resources.resourceName}
									</a>
									</span>
								</li>
								<c:if test="${target.resources.resourceColumn.baseCatalogKnowledgeFlag eq 'Y'}">
								<li>
									<label>学科：</label>
									<span>${target.resources.baseDisciplineName}</span>
								</li>
								</c:if>
								<c:if test="${target.resources.resourceColumn.baseCatalogKnowledgeFlag ne 'Y' && target.resources.resourceColumn.resourceCatalogFlag eq 'Y'}">
								<li>
									<label>分类：</label>
									<span>${target.resources.resourceCatalogFirstName}</span>
								</li>
								</c:if>
								<li>
									<label>评分：</label>
									<span class="star-rating">
										<span class="current-rating star<fmt:formatNumber value="${target.resources.evaluateAvg}" pattern="#"/>">
										</span>
									</span>
									<span class="red">${target.resources.evaluateAvg}</span>分
								</li>
								<li>
									<label>观看次数：</label>
									<span>${target.resources.viewCount}次</span>
									<label>推荐时间：</label>
									<span><fmt:formatDate value="${target.createTime}" pattern="yyyy年MM月dd日 HH:mm"/></span>
								</li>
							</ul>
						</div>
						<div class="sourceState">
							<c:if test="${not empty target.nextResourceDistributeId}">
							<c:if test="${not empty nextDistributeMap[target.nextResourceDistributeId]}">
								<span class="red waitCheck">
								<c:if test="${nextDistributeMap[target.nextResourceDistributeId].status eq 'REVIEW_NOT_REVIEW'}">分发给下级待审核</c:if>
								<c:if test="${nextDistributeMap[target.nextResourceDistributeId].status eq 'REVIEW_ACCEPT'}">已分发给下级</c:if>
								<c:if test="${nextDistributeMap[target.nextResourceDistributeId].status eq 'REVIEW_REJECT'}">分发给下级审核不通过</c:if>
								</span>
							</c:if>
							</c:if>
						</div>
					</div>
					</c:forEach>
					${pageStr}
					
					<script type="text/javascript">
						function distributedToSchoolAndOrg(id){
							Win.open({
								id:"distributeWin",
								title:"选择分发机构",
								mask:true,
								width:500,
								height:200,
								url:"${ctx}/front/distribute/orgAndSchoolSelect.html?id=" + id + "&action=distributedToSchoolAndOrg",
								beforeClose:queryResource
							});
						}
					</script>