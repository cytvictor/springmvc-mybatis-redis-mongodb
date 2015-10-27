<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<%@ include file="../common/meta.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/public/easyui/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/public/easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/public/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/public/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/public/js/resourceUpload.js"></script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="container">
		<%@ include file="side_define.jsp"%>
		<div class="content">
			<h4 class="contentNav resourceContentNav">
				<c:if test="${not empty schoolTag and schoolTag eq 'yes'}">
					<a class="btn testZj bgBlue" href="${ctx}/front/resUpload/school/upload.html">上传资源</a>
				</c:if>
				<c:if test="${empty schoolTag or schoolTag eq 'no'}">
					<c:if test="${loginUser.userType eq 'TEACHER'}">
						<a class="btn testZj bgBlue" href="${ctx}/front/resUpload/teacher/upload.html">上传资源</a>
					</c:if>
					<c:if test="${loginUser.userType eq 'STUDENT'}">
						<a class="btn testZj bgGreen" href="${ctx}/front/resUpload/student/upload.html">上传资源</a>
					</c:if>
					<c:if test="${loginUser.userType eq 'ORG' or loginUser.userType eq 'ORG_MANAGER'}">
						<a class="btn testZj bgBlue" href="${ctx}/front/resUpload/org/upload.html">上传资源</a>
					</c:if>
				</c:if>
				资源中心
				<%@ include file="nav_module.jsp"%>
			</h4>

			<div class="otContent">
				<div class="tabBox shareExam currBox">
					<%@ include file="nav_type.jsp"%>
					<ul class="commonSelect">
						<li>
							<label>分类：</label>
							<select id='catalogRoot' class="easyui-combobox" name='teachCatalogRoot' style='width:80px;' data-options='editable:false, valueField:"id", textField:"catalogName", onChange:onCatalogChange'>
								<option value="">请选择</option>
							</select>
							<div id="catalogContainer" style="display:inline;"></div>
						</li>
						<li>
							<label>时间：</label>
							<input class="easyui-datetimebox" data-options="editable:false,showSeconds:false" id="startTime" style="width:125px">
							至：
							<input class="easyui-datetimebox" data-options="editable:false,showSeconds:false" id="endTime" style="width:125px">
							<label class="ml20">名称：</label>
							<input type="text" id="resourceName" name="resourceName"  title="请输入您要搜索的资源名称" placeholder="请输入您要搜索的资源名称" class="searchInput" />
							<a href="javascript:;" class="btn bgBlue ml20" onclick="javascript:queryResource();">搜索</a>
						</li>
					</ul>
					<script type="text/javascript">
						var catalogType = '${resourceColumnId}';
						var ctx = '${ctx}';
						$(document).ready(function(){
							$.get("${ctx}/skeleton/getRootCatalogs.do?type=" + catalogType, function(result){
								result.unshift({"id":"", "catalogName":"请选择"});
								$("#catalogRoot").combobox("loadData", result);
							}, "json");	
							queryResource();
						});

						function queryResource(page){
							var catalogId = formatCatalogId("catalogRoot", "");
							var startTime = $("#startTime").datetimebox("getValue");
							var endTime = $("#endTime").datetimebox("getValue");
							var resourceName = $("#resourceName").val();
							var param = {
									catalogId:catalogId,
									startTime:startTime,
									endTime:endTime,
									resourceName:resourceName
							}
							var url = "content.html";
							if(page){
								url = url + '?page='+page;
							}
							$.post(url,param, function(result){
								if (result.length>0){
									$("#contentContainer").html(result);
									$(".easyui-menubutton").each(function(){
										$(this).menubutton({});
									});
									window.scroll(0,0);
								}
							});
						}
					</script>
					<div id="contentContainer" class="sourceList2">
					
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>