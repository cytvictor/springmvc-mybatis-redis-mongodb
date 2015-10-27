<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
					<c:if test="${empty list}">
						<center><p class="searchNone">抱歉！没有搜索到您想要的信息！</p></center>
					</c:if>
					<c:forEach items="${list}" var="push">
					<div class="sourceList2 clearfix">
						<span class="sourcePhoto">
							<a href="${ctx}/front/respub/view/${push.resources.id}.html" target="_blank">
							<img src="${ctx}/ResourceImageServlet/${push.resources.thumb}" height="90";>
							</a>
						</span>
						<div class="sourceDetail xelIcon">
							<div class="sourceEdit">
								<c:if test="${push.status eq 'REVIEW_NOT_REVIEW' or push.status eq 'REVIEW_ACCEPT'}">
									<c:if test="${push.status eq 'REVIEW_NOT_REVIEW'}">
										<a href="javascript:reviewAccept('${push.id}');" class="">审核通过</a>
										<a href="javascript:reviewReject('${push.id}');" class="">审核不通过</a>
									</c:if>
									<c:if test="${push.status eq 'REVIEW_ACCEPT' and empty push.nextResourcePushId}">
										<a href="javascript:pushedPushOrg('${push.id}');" class="">推荐给上级</a>
									</c:if>
									<c:if test="${push.status eq 'REVIEW_ACCEPT' and empty push.distributeId}">
										<a href="javascript:pushedDistributeTeacher('${push.id}');">分发给老师</a>
									</c:if>
								</c:if>
							</div>
							<ul>
								<li class="name">
									
									<span>
									<a href="${ctx}/front/respub/view/${push.resources.id}.html" target="_blank">
									${push.resources.resourceName}
									</a>
									</span>
								</li>
								<c:if test="${push.resources.resourceColumn.baseCatalogKnowledgeFlag eq 'Y'}">
								<li>
									<label>学科：</label>
									<span>${push.resources.baseDisciplineName}</span>
								</li>
								</c:if>
								<c:if test="${push.resources.resourceColumn.baseCatalogKnowledgeFlag ne 'Y' && push.resources.resourceColumn.resourceCatalogFlag eq 'Y'}">
								<li>
									<label>分类：</label>
									<span>${push.resources.resourceCatalogFirstName}</span>
								</li>
								</c:if>
								<li>
									<label>评分：</label>
									<span class="star-rating">
										<span class="current-rating star<fmt:formatNumber value="${push.resources.evaluateAvg}" pattern="#"/>">
										</span>
									</span>
									<span class="red">${push.resources.evaluateAvg }</span>分
								</li>
								<li>
									<label>观看次数：</label>
									<span>${push.resources.viewCount}次</span>
									<label>推荐时间：</label>
									<span><fmt:formatDate value="${push.createTime}" pattern="yyyy年MM月dd日 HH:mm"/></span>
								</li>
							</ul>
						</div>
						<div class="sourceState">
							<span class="red waitCheck">
								<c:if test="${push.status eq 'REVIEW_NOT_REVIEW'}">待审核</c:if>
								<c:if test="${push.status eq 'REVIEW_ACCEPT'}">审核通过</c:if>
								<c:if test="${push.status eq 'REVIEW_REJECT'}">审核不通过</c:if>
							</span>
							<c:if test="${not empty push.nextResourcePushId}">
								<c:if test="${not empty nextPushMap[push.nextResourcePushId]}">
									<span class="red waitCheck">
									<c:if test="${nextPushMap[push.nextResourcePushId].status eq 'REVIEW_NOT_REVIEW'}">推荐给上级待审核</c:if>
									<c:if test="${nextPushMap[push.nextResourcePushId].status eq 'REVIEW_ACCEPT'}">已推荐给上级</c:if>
									<c:if test="${nextPushMap[push.nextResourcePushId].status eq 'REVIEW_REJECT'}">推荐给上级不通过</c:if>
									</span>
								</c:if>
							</c:if>
							<c:if test="${not empty push.distributeId}">
								<span class="red waitCheck">已分发给老师</span>
							</c:if>
						</div>
					</div>
					</c:forEach>
					${pageStr}
					<script type="text/javascript">
						function reviewAccept(id){
							Win.confirm("确定要审核通过此资源吗?", function(){
								$.post("${ctx}/front/respub/review/accept.html?id=" + id, {}, function(data){
									if (data.result){
										queryResource();
									} else {
										Win.alert("审核资源失败!");
									}
								}, 'json');
							}, function(){});
						}
						
						function reviewReject(id){
							Win.confirm("确定要审核不通过此资源吗?", function(){
								$.post("${ctx}/front/respub/review/reject.html?id=" + id, {}, function(data){
									if (data.result){
										queryResource();
									} else {
										Win.alert("审核资源失败!");
									}
								}, 'json');
							}, function(){});
						}
						
						function pushedPushOrg(id){
							Win.confirm("确定要推荐此资源吗?", function(){
								$.post("${ctx}/front/push/pushedPushOrg.html?id=" + id, {}, function(data){
									if (data.result){
										Win.alert("推荐资源成功!");
										queryResource();
									} else {
										Win.alert("推荐资源失败!");
									}
								}, 'json');
							}, function(){});
						}
						
						function pushedDistributeTeacher(id){
							Win.open({
								id:"distributeWin",
								title:"选择分发的老师",
								mask:true,
								width:500,
								height:200,
								url:"${ctx}/front/distribute/classlevelSelect.html?id=" + id + "&action=pushedDistributeTeacher",
								beforeClose:queryResource
							});
						}
					</script>