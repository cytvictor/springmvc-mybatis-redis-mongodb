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
	<script type="text/javascript" src="${ctx}/public/js/easyuiComboboxOnSelect.js"></script>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="container">
		<c:if test="${module ne 'school'}">
		<%@ include file="../common/side_visitor.jsp"%>
		</c:if>
		<c:if test="${module eq 'school'}">
		<%@ include file="../common/school_side.jsp"%>
		</c:if>
		<script type="text/javascript">
			setSideNav("resourceCenter");
		</script>
		<div class="content">
			<h4 class="contentNav resourceContentNav">
				资源中心
				<c:if test="${module ne 'school'}">
				<%@ include file="visitor_module.jsp"%>
				</c:if>
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
							<input type="text" id="resourceName" name="resourceName" title="请输入您要搜索的资源名称" placeholder="请输入您要搜索的资源名称" class="searchInput" />
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
							var param ={
									catalogId:formatCatalogId("catalogRoot", ""),
									startTime:$("#startTime").datetimebox("getValue"),
									endTime:$("#endTime").datetimebox("getValue"),
									resourceName:$("#resourceName").val()
							};
							/* var str = "catalogId=" + formatCatalogId("catalogRoot", "");
							str = str + "&startTime=" + $("#startTime").datetimebox("getValue");
							str = str + "&endTime=" + $("#endTime").datetimebox("getValue");
							str = str + "&resourceName=" + $("#resourceName").val(); */
							var url = "content.html";
							if (page){
								url  = url + "?page=" + page;
							}
							$.post(url, param, function(result){
								if (result.length>0){
									$("#contentContainer").html(result);
									$(".easyui-menubutton").each(function(){
										$(this).menubutton({});
									});
								}
							});
						}
					</script>
					<div id="contentContainer">
					
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>