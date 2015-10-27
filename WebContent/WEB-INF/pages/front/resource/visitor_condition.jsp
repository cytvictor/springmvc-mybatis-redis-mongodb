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
		<%@ include file="../common/side_visitor.jsp"%>
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
			<input type="hidden" name="resourceCatalogFlag" id="resourceCatalogFlag" value="${resourceColumn.resourceCatalogFlag }"/>
				<div class="tabBox shareExam currBox">
					<%@ include file="nav_type.jsp"%>
					<ul class="commonSelect">						
						<li>
						    <c:if test="${resourceColumn.baseCatalogKnowledgeFlag == 'Y' }">
							<label>学段：</label>
							<select id='semesterList' class="easyui-combobox" name='semesterList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"semesterName", onChange:onChangeSemester'>
							</select>
							<label class="ml20">年级：</label>
							<select id='classlevelList' class="easyui-combobox" name='classlevelList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"classLevelName", onChange:onChangeClassLevel'>
								<option value="">请选择</option>
							</select>
							<label class="ml20">学科：</label>
							<select id='disciplineList' class="easyui-combobox" name='disciplineList' style='width:80px;' data-options='editable:false, valueField:"id", textField:"disciplineName", onChange:onChangeDiscipline'>
								<option value="">请选择</option>
							</select>
							</c:if>
							<c:if test="${resourceColumn.resourceCatalogFlag == 'Y' }">
								<label>资源分类：</label>
								<select id='resourceCatalogRoot' class="easyui-combobox" name='resourceCatalogRoot' style='width:80px;' data-options='editable:false, valueField:"id", textField:"catalogName", onChange:onResourceCatalogChange'>
									<option value="">请选择</option>
								</select>
								<div id="resourceCatalogContainer" style="display:inline;"></div>
						    </c:if>
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
						var resourceColumnId = '${resourceColumnId}';
						var ctx = '${ctx}';
						$(document).ready(function(){
							var baseCatalogKnowledgeFlag = "${resourceColumn.baseCatalogKnowledgeFlag}";
							if(baseCatalogKnowledgeFlag == "Y"){
								$.get("${ctx}/skeleton/getAllSemester.do", function(result){
									result.unshift({"id":"", "semesterName":"请选择"});
									$("#semesterList").combobox("loadData", result);
								}, "json");	
							}
							
							// 是否关联资源分类
							var resourceCatalogFlag = $("#resourceCatalogFlag").val();
							if (resourceCatalogFlag == 'Y') {
								$.get("${ctx}/skeleton/getRootResourceCatalogs.do?resourceColumnId=" + resourceColumnId, function(result){
									result.unshift({"id":"", "catalogName":"请选择"});
									$("#resourceCatalogRoot").combobox("loadData", result);
								}, "json");	
							}
							
							queryResource();
						});

						function queryResource(page){
							var param ={
									resourceColumnId:resourceColumnId,
									startTime:$("#startTime").datetimebox("getValue"),
									endTime:$("#endTime").datetimebox("getValue"),
									resourceName:$("#resourceName").val()//.replace("%","%25")
							};
							if ($("#semesterList").length > 0) {
								param.semesterId = $("#semesterList").combobox("getValue");
							}
							if ($("#classlevelList").length > 0) {
								param.classlevelId = $("#classlevelList").combobox("getValue");
							}
							if ($("#disciplineList").length > 0) {
								param.disciplineId = $("#disciplineList").combobox("getValue");
							}
							if ($("#resourceCatalogRoot").length > 0) {
								param.resourceCatalogFirstId = $("#resourceCatalogRoot").combobox("getValue");;
							}
							if ($(".resourceCatalogSecond").length>0) {
								param.resourceCatalogSecondId = $(".resourceCatalogSecond").combobox("getValue");
							}
							
							var url = "${ctx}/front/${resourceUrl}/${module}/content.html";
							if(page){
								url = url + '?page='+page;
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