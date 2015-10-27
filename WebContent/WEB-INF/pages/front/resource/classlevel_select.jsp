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
		.content{background-color:#fff !important;min-height: 0;}
	</style>
</head>
<body>
	<div class="content">
		<div class="otContent">
			<div class="tabBox shareExam currBox">
				<div>
				<c:forEach items="${list}" var="item" varStatus="status" >
					<c:if test="${status.index % 6 == 0}"></div><div></c:if>
					<span style="margin-left:10px;"><input type="checkbox" name="classlevel" value="${item.id}">${item.classLevelName}</span>
				</c:forEach>
				</div>
			</div>
			<div class="submitBtn">
				<a class="okBtn" onclick="onOk();">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="cancelBtn" onclick="parent.Win.wins.distributeWin.close();">取消</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function onOk(){
			var classLevelIds = "";
			$('input:checkbox:checked').each(function() {
				if (classLevelIds.length > 0)
					classLevelIds = classLevelIds + ",";
				classLevelIds = classLevelIds + $(this).val();
			});
			if (classLevelIds.length == 0){
				Win.alert("请选择下发的对象!");
				return;
			}
			$.post("${ctx}/front/distribute/${action}.html?id=${id}&classlevelIds=" + classLevelIds, {}, function(data){
				if (data.result){
					parent.Win.wins.distributeWin.close();
				} else {
					Win.alert("失败!");
				}
			}, 'json');
		}
	</script>
</body>
</html>