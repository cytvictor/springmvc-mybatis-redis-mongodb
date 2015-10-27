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
						</span>
						<div class="sourceDetail xelIcon">
							<div class="sourceEdit">
								<a href="javascript:deleteResource('${resource.id}');">删除资源</a>
								<c:if test="${resource.transFlag eq 'TRANS_SUCCESS' and resource.deleteFlag eq 'DELETE_NOT_DELETE'}">
									<c:if test="${empty pushMap[resource.id] and empty distributeMap[resource.id]}">
										<a href="${ctx}/front/respub/edit/${resource.id}.html?schoolTag=${schoolTag}">编辑资源</a>
									</c:if>
									<c:if test="${empty pushMap[resource.id]}">
										<a href="javascript:schoolUploadPushOrg('${resource.id}')">推荐给上级</a>
									</c:if>
									<c:if test="${empty distributeMap[resource.id]}">
										<a href="javascript:distributeTeacher('${resource.id}')">分发给老师</a>
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
									<c:if test="${pushMap[resource.id].status eq 'REVIEW_NOT_REVIEW'}">推送给上级待审核</c:if>
									<c:if test="${pushMap[resource.id].status eq 'REVIEW_ACCEPT'}">推送给上级通过</c:if>
									<c:if test="${pushMap[resource.id].status eq 'REVIEW_REJECT'}">推送给上级被拒绝</c:if>
								</span>
							</c:if>
							<c:if test="${not empty distributeMap[resource.id]}">
								<span class="red waitCheck">已分发给老师</span>
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
						
						
						function editCatalog(id){
							Win.open({
								id:"editCatalog",
								title:"修改分类",
								mask:true,
								width:475,
								height:200,
								url:"${ctx}/front/respub/edit/catalogEdit.html?id="+id,
								beforeClose:queryResource
							});
						}
						
						function schoolUploadPushOrg(id){
							Win.confirm("确定要推荐此资源吗?", function(){
								$.post("${ctx}/front/push/schoolUploadPushOrg.html?id=" + id, {}, function(data){
									if (data.result){
										Win.alert("推荐资源成功!");
										queryResource();
									} else {
										Win.alert("推荐资源失败!");
									}
								}, 'json');
							}, function(){});
						}
						
						function distributeTeacher(id){
							Win.open({
								id:"distributeWin",
								title:"选择分发的老师",
								mask:true,
								width:460,
								height:200,
								url:"${ctx}/front/distribute/classlevelSelect.html?id=" + id + "&action=schoolUploadedDistributeTeacher",
								beforeClose:queryResource
							});
						}
					</script>