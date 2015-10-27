<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
					<c:if test="${empty list}">
						<center><p class="searchNone">抱歉！没有搜索到您想要的信息！</p></center>
					</c:if>
					<c:forEach items="${list}" var="resource">
					<div class="sourceList2 clearfix">
						<span class="sourcePhoto">
						 	<c:if test="${resource.deleteFlag eq 'DELETE_NOT_DELETE'}">
								<a href="${ctx}/front/respub/view/${resource.id}.html" target="_blank">
									<img src="${ctx}/ResourceImageServlet/${resource.thumb}" height="90" />
								</a>
							</c:if>							
							<%-- <c:if test="${resource.transFlag eq 'TRANS_SUCCESS' and resource.deleteFlag eq 'DELETE_NOT_DELETE'}">
								<a href="${ctx}/front/respub/view/${resource.id}.html" target="_blank">
									<img src="${ctx}/ResourceImageServlet/${resource.thumb}" height="90";>
								</a>
							</c:if>
							<c:if test="${resource.transFlag ne 'TRANS_SUCCESS' or resource.deleteFlag ne 'DELETE_NOT_DELETE'}">
								<img src="${ctx}/ResourceImageServlet/${resource.thumb}" width="150" height="90";>
							</c:if> --%>
							
						</span>
						<div class="sourceDetail xelIcon">
							<div class="sourceEdit">
									<a href="javascript:deleteResource('${resource.id}');">删除资源</a>
									<c:if test="${resource.transFlag eq 'TRANS_SUCCESS' and resource.deleteFlag eq 'DELETE_NOT_DELETE'}">
										<c:if test="${empty pushMap[resource.id] and empty distributeMap[resource.id]}">
											<a href="${ctx}/front/respub/edit/${resource.id}.html">编辑资源</a>
										</c:if>
										<c:if test="${loginUser.userType eq 'TEACHER' or loginUser.userType eq 'SCHOOL'}">
											<c:if test="${empty pushMap[resource.id]}">
												<a href="javascript:pushSchool('${resource.id}');">推荐给学校</a>
											</c:if>
											<c:if test="${empty distributeMap[resource.id]}">
												<a href="javascript:distributeStudent('${resource.id}');">分发给学生</a>
											</c:if>
										</c:if>
										
										<c:if test="${loginUser.userType eq 'STUDENT' and empty pushMap[resource.id]}">
											<a href="javascript:pushTeacher('${resource.id}');">推荐给老师</a>
										</c:if>
									</c:if>
							</div>
							<ul>
								<li class="name">
									<c:if test="${resource.deleteFlag eq 'DELETE_NOT_DELETE'}">
									<span><a href="${ctx}/front/respub/view/${resource.id}.html" target="_blank">${resource.resourceName}</a></span>
									</c:if>
									<c:if test="${resource.deleteFlag ne 'DELETE_NOT_DELETE'}"><span>${resource.resourceName}</span></c:if>
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
							<c:if test="${resource.deleteFlag eq 'DELETE_BY_MANAGER'}">
								<span class="red waitCheck">被管理员删除</span>
							</c:if>
							<span class="red waitCheck">
								<c:if test="${resource.transFlag eq 'TRANS_FAILED'}">转换失败</c:if>
								<c:if test="${resource.transFlag eq 'TRANS_TRANSING'}">转换中</c:if>
								<c:if test="${resource.transFlag eq 'TRANS_PENDDING'}">等待转换</c:if>
							</span>
							<c:if test="${not empty pushMap[resource.id]}">
								<span class="red waitCheck">
								<c:if test="${loginUser.userType eq 'TEACHER' or loginUser.userType eq 'SCHOOL'}">
									<c:if test="${pushMap[resource.id].status eq 'REVIEW_NOT_REVIEW'}">推送给学校待审核</c:if>
									<c:if test="${pushMap[resource.id].status eq 'REVIEW_ACCEPT'}">推送给学校通过</c:if>
									<c:if test="${pushMap[resource.id].status eq 'REVIEW_REJECT'}">推送给学校被拒绝</c:if>
								</c:if>
								<c:if test="${loginUser.userType eq 'STUDENT'}">
									<c:if test="${pushMap[resource.id].status eq 'REVIEW_NOT_REVIEW'}">推送给老师待审核</c:if>
									<c:if test="${pushMap[resource.id].status eq 'REVIEW_ACCEPT'}">推送给老师通过</c:if>
									<c:if test="${pushMap[resource.id].status eq 'REVIEW_REJECT'}">推送给老师被拒绝</c:if>
								</c:if>
								</span>
							</c:if>
							
							<c:if test="${not empty distributeMap[resource.id]}">
								<span class="red waitCheck">已分发给学生</span>
							</c:if>
						</div>
					</div>
					</c:forEach>
					${pageStr}
					
					<script type="text/javascript">
						function deleteResource(id){
							Win.confirm("确定要删除此资源吗?", function(){
								$.post("${ctx}/front/respub/edit/delete.html?id=" + id, {}, function(data){
									if (data.result){
										queryResource();
									} else {
										Win.alert("删除资源失败!");
									}
								}, 'json');
							}, function(){});
						}
						
						function pushTeacher(id){
							Win.confirm("确定要推荐此资源吗?", function(){
								$.post("${ctx}/front/push/pushTeacher.html?id=" + id, {}, function(data){
									if (data.result){
										Win.alert("推荐资源成功!");
										queryResource();
									} else {
										Win.alert("找不到对应老师，推荐资源失败!");
									}
								}, 'json');
							}, function(){});
						}
						
						function pushSchool(id){
							Win.confirm("确定要推荐此资源吗?", function(){
								$.post("${ctx}/front/push/uploadedPushSchool.html?id=" + id, {}, function(data){
									if (data.result){
										Win.alert("推荐资源成功!");
										queryResource();
									} else {
										Win.alert("推荐资源失败!");
									}
								}, 'json');
							}, function(){});
						}
						
						function distributeStudent(id){
							Win.confirm("确定要分发此资源吗?", function(){
								$.post("${ctx}/front/distribute/uploadedToStudent.html?id=" + id, {}, function(data){
									if (data.result){
										Win.alert("分发资源成功!");
										queryResource();
									} else {
										Win.alert("分发资源失败!");
									}
								}, 'json');
							}, function(){});
						}
					</script>