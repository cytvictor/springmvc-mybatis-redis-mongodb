<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
					<c:if test="${empty list}">
						<center><p class="searchNone">抱歉！没有搜索到您想要的信息！</p></center>
					</c:if>
					<c:forEach items="${list}" var="hold">
					<div class="sourceList2 clearfix">
						<span class="sourcePhoto">
							<a href="${ctx}/front/respub/view/${hold.resources.id}.html" target="_blank">
							<img src="${ctx}/ResourceImageServlet/${hold.resources.thumb}" height="90";>
							</a>
						</span>
						<div class="sourceDetail xelIcon">
							<ul>
								<li class="name">
									
									<span><a href="${ctx}/front/respub/view/${hold.resources.id}.html" target="_blank">${hold.resources.resourceName}</a></span>
								</li>
								<c:if test="${hold.resources.resourceColumn.baseCatalogKnowledgeFlag eq 'Y'}">
								<li>
									<label>学科：</label>
									<span>${hold.resources.baseDisciplineName}</span>
								</li>
								</c:if>
								<c:if test="${hold.resources.resourceColumn.baseCatalogKnowledgeFlag ne 'Y' && hold.resources.resourceColumn.resourceCatalogFlag eq 'Y'}">
								<li>
									<label>分类：</label>
									<span>${hold.resources.resourceCatalogFirstName}</span>
								</li>
								</c:if>
								<li>
									<label>评分：</label>
									<span class="star-rating">
										<span class="current-rating star<fmt:formatNumber value="${hold.resources.evaluateAvg}" pattern="#"/>">
										</span>
									</span>
									<span class="red">${hold.resources.evaluateAvg}</span>分
								</li>
								<li>
									<label>观看次数：</label>
									<span>${hold.resources.viewCount}次</span>
									<label>共享时间：</label>
									<span><fmt:formatDate value="${hold.createTime}" pattern="yyyy年MM月dd日 HH:mm"/></span>
								</li>
							</ul>
						</div>
					</div>
					</c:forEach>
					${pageStr}