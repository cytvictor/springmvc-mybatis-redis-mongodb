<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<%@ include file="../common/meta.jsp"%>
</head>
<body>
	<%@ include file="../common/header.jsp" %>
	<div class="container">
		<%@ include file="side_define.jsp"%>
		<div class="submitBtn">
			<c:if test="${empty error}">
				<h2 style="font-size:16px;margin: 0 10px 20px 10px">编辑资源成功 </h2>
			</c:if>
			<c:if test="${not empty error}">
				<h2 style="font-size:16px;margin: 0 10px 20px 10px">编辑资源错误:${error} </h2>
			</c:if>
			<c:if test="${not empty schoolTag and schoolTag eq 'yes'}">
				<input type="button" value="返回我的资源" class="btn ml10" onclick="window.location='${ctx}/front/schoolresource/uploaded.html'"/>
			</c:if>
			<c:if test="${empty schoolTag or schoolTag eq 'no'}">
				<c:if test="${loginUser.userType eq 'TEACHER' or loginUser.userType eq 'STUDENT'}">
					<input type="button" value="返回我的资源" class="btn" onclick="window.location='${ctx}/front/resource/uploaded.html'"/>
				</c:if>
				<c:if test="${loginUser.userType eq 'ORG' or loginUser.userType eq 'ORG_MANAGER'}">
					<input type="button" value="返回我的资源" class="btn" onclick="window.location='${ctx}/front/orgresource/uploaded.html'"/>
				</c:if>
			</c:if>
		</div>
	</div>
	<%@ include file="../common/footer.jsp" %>
</body>
</html>