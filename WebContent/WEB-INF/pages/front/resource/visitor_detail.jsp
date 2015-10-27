<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
					<c:if test="${empty list}">
						<center><p class="searchNone">抱歉！没有搜索到您想要的信息！</p></center>
					</c:if>
					<c:forEach items="${list}" var="resource">
					<div class="sourceList2 clearfix">
						<span class="sourcePhoto">
							<a href="${ctx}/front/respub/view/${resource.id}.html" target="_blank">
							<img src="${ctx}/ResourceImageServlet/${resource.thumb}" height="90";>
							</a>
						</span>
						<div class="sourceDetail xelIcon">
							<div class="sourceEdit">
							</div>
							<ul>
								<li class="name">
									<span><a href="${ctx}/front/respub/view/${resource.id}.html" target="_blank">${resource.resourceName}</a></span>
								</li>
								<c:if test="${resource.resourceColumn.baseCatalogKnowledgeFlag eq 'Y'}">
								<li>
									<label>学科：</label>
									<span>${resource.baseDisciplineName}</span>
								</li>
								</c:if>
								<c:if test="${resource.resourceColumn.baseCatalogKnowledgeFlag ne 'Y' && resource.resourceColumn.resourceCatalogFlag eq 'Y'}">
								<li>
									<label>分类：</label>
									<span>${resource.resourceCatalogFirstName}</span>
								</li>
								</c:if>
								<li>
									<label>评分：</label>
									<span class="star-rating">
										<span class="current-rating star<fmt:formatNumber value="${resource.evaluateAvg}" pattern="#"/>">
										</span>
									</span>
									<span class="red">${resource.evaluateAvg }</span>分
								</li>
								<li>
									<label>观看次数：</label>
									<span>${resource.viewCount}次</span>
									<label>上传时间：</label>
									<span><fmt:formatDate value="${resource.createTime}" pattern="yyyy年MM月dd日 HH:mm"/></span>
								</li>
							</ul>
						</div>
						<div class="sourceState">
							
						</div>
					</div>
					</c:forEach>
					${pageStr}