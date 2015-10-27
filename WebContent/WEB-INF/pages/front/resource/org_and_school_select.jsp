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
		html{height: 100%;background-color:#fff;}
		body,
		.content{background-color:#fff;min-height:0;}
	</style>
</head>
<body>
	<div class="content">
		<div class="otContent">
			<div class="tabBox shareExam currBox">
				<c:if test="${sessionScope.session_login_user.orgLevel ne '3'}">
				<span>
					<div style="margin-left:10px;margin-bottom:10px;">下级机构列表</div>
					<input type="checkbox" name="checkIdAll" id="checkIdAll" value="全选" onclick="checkAll(this);">全选&nbsp;&nbsp;
					<c:forEach items="${orgList}" var="org" >
					<div style="margin-left:10px;"><input type="checkbox" name="orgIds" value="${org.id}">${org.orgName}</div>
					</c:forEach>
				</span>
				</c:if>
				
				<c:if test="${sessionScope.session_login_user.orgLevel ne '1'}">
				<span>
					<div style="margin-left:10px;margin-bottom:10px;margin-top:20px;">下级学校列表</div>
					<input type="checkbox" name="checkIdAll" id="checkIdAll" value="全选" onclick="checkAllc(this);">全选&nbsp;&nbsp;
					<c:forEach items="${schoolList}" var="school" >
					<div style="margin-left:10px;"><input type="checkbox" name="schoolIds" value="${school.id}">${school.schoolName}</div>
					</c:forEach>
				</span>
				</c:if>
			</div>
			<div class="submitBtn">
				<a class="okBtn" onclick="onOk();">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="cancelBtn" onclick="parent.Win.wins.distributeWin.close();">取消</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function checkAll(src){
			if(src.checked){
				$("[name$='orgIds']").prop('checked',true);
			} else {
				$("[name$='orgIds']").prop('checked',false);
			}
		}
		
		function checkAllc(src){
			if(src.checked){
				$("[name$='schoolIds']").prop('checked',true);
			} else {
				$("[name$='schoolIds']").prop('checked',false);
			}
		}
	
		function onOk(){
			var orgIdStr = "";
			var schoolIdStr = "";
			$("input[type=checkbox][name='orgIds']:checked").each(function() {
				if (orgIdStr.length > 0)
					orgIdStr = orgIdStr + ",";
				orgIdStr = orgIdStr + $(this).val();
			});
			$("input[type=checkbox][name='schoolIds']:checked").each(function() {
				if (schoolIdStr.length > 0)
					schoolIdStr = schoolIdStr + ",";
				schoolIdStr = schoolIdStr + $(this).val();
			});
			if (orgIdStr.length == 0 && schoolIdStr.length == 0){
				Win.alert("请选择下发的对象!");
				return;
			}
			$.post("${ctx}/front/distribute/${action}.html?id=${id}&orgIds=" + orgIdStr + "&schoolIds=" + schoolIdStr, {}, function(data){
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