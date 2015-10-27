<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
					<c:if test="${empty list}">
						<center><p class="searchNone">抱歉！没有搜索到您想要的信息！</p></center>
					</c:if>
					<c:forEach items="${list}" var="resource">
					<div class="sourceList2 clearfix">
						<span class="sourcePhoto">
							<a href="${ctx}/front/respub/view/${resource.resources.id}.html" target="_blank">
							<img src="${ctx}/ResourceImageServlet/${resource.resources.thumb}" height="90";>
							</a>
						</span>
						<div class="sourceDetail xelIcon">
							<div class="sourceEdit">
								<a href="javascript:onCancelFavorite('${resource.resources.id}')" data-options="">取消收藏</a>
							</div>
							<ul>
								<li class="name">
									
									<span>
									<a href="${ctx}/front/respub/view/${resource.resources.id}.html" target="_blank">
									${resource.resources.resourceName}
									</a>
									</span>
								</li>
								<c:if test="${resource.resources.resourceColumn.baseCatalogKnowledgeFlag eq 'Y'}">
								<li>
									<label>学科：</label>
									<span>${resource.resources.baseDisciplineName}</span>
								</li>
								</c:if>
								<c:if test="${resource.resources.resourceColumn.baseCatalogKnowledgeFlag ne 'Y' && resource.resources.resourceColumn.resourceCatalogFlag eq 'Y'}">
								<li>
									<label>分类：</label>
									<span>${resource.resources.resourceCatalogFirstName}</span>
								</li>
								</c:if>
								<li>
									<label>评分：</label>
									<span class="star-rating">
										<span class="current-rating star<fmt:formatNumber value="${resource.resources.evaluateAvg}" pattern="#"/>">
										</span>
									</span>
									<span class="red">${resource.resources.evaluateAvg}</span>分
								</li>
								<li>
									<label>观看次数：</label>
									<span>${resource.resources.viewCount}次</span>
									<label>收藏时间：</label>
									<span><fmt:formatDate value="${resource.createTime}" pattern="yyyy年MM月dd日 HH:mm"/></span>
								</li>
							</ul>
						</div>
					</div>
					</c:forEach>
					${pageStr}
					
					<script type="text/javascript">
						function onCancelFavorite(id){
							Win.confirm("确定要取消此资源的收藏吗?", function(){
								$.post("${ctx}/front/${resourceUrl}/${module}/cancelFavorite.html?id=" + id, {}, function(data){
									if (data.result){
										queryResource();
									} else {
										Win.alert("取消收藏资源失败!");
									}
								}, 'json');
							}, function(){});
						}
					</script>