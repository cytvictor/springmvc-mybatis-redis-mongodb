<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<%@ include file="../common/meta.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/public/easyui/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/public/easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/public/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx }/public/js/resourceUpload.js"></script>
	<style>
		body,
		.content{background-color:#fff;}
	</style>
</head>
<body>
	<div class="content">
		<div class="otContent">
			<div class="tabBox shareExam currBox">
				<ul>
					<li>
						<label>分类：</label>
						<select id='catalogRoot' class="easyui-combobox" name='teachCatalogRoot' style='width:80px;' data-options='editable:false, valueField:"id", textField:"catalogName", onChange:onCatalogChange'>
							<option value="">请选择</option>
						</select>
						<div id="catalogContainer" style="display:inline;"></div>
					</li>
				</ul>
			</div>
			<div class="submitBtn">
				<a class="okBtn" onclick="saveCatalog();">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="cancelBtn" onclick="parent.Win.wins.editCatalog.close();">取消</a>
			</div>
			
		</div>
	</div>
	<script type="text/javascript">
		var catalogType = '${resourceColumnId}';
		var ctx = '${ctx}';
		$(document).ready(function(){
			$.get("${ctx}/skeleton/getRootCatalogs.do?type=" + catalogType, function(result){
				result.unshift({"id":"", "catalogName":"请选择"});
				$("#catalogRoot").combobox("loadData", result);
			}, "json");	
		});
		function saveCatalog(){
			var id = formatCatalogId("catalogRoot", -1);
			if (id == -1){
				Win.alert("请选择资源的分类!");
				return;
			} else {
				$.post("${ctx}/front/respub/edit/catalogSave.html?id=${resourceId}&catalogId=" + id, {}, function(data){
					if (data.result){
						parent.Win.wins.editCatalog.close();
					} else {
						Win.alert("保存失败!");
					}
				}, 'json');
			}
		}
	</script>
</body>
</html>