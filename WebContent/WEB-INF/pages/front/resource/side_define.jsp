<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 判断各种角色左侧栏目树 --%>
<c:if test="${empty schoolTag or schoolTag eq 'no'}">
	<c:if test="${loginUser.userType eq 'TEACHER'}">
	<%@ include file="../common/teacher_side.jsp"%>
	</c:if>
	<c:if test="${loginUser.userType eq 'STUDENT'}">
	<%@ include file="../common/student_side.jsp"%>
	</c:if>
	<c:if test="${loginUser.userType eq 'PARENT'}">
	<%@ include file="../common/parent_side.jsp"%>
	</c:if>
	<c:if test="${loginUser.userType eq 'ORG' or loginUser.userType eq 'ORG_MANAGER' or loginUser.userType eq 'TEACH_ORG_MANAGER'}">
	<%@ include file="../common/department_side.jsp"%>
	</c:if>
</c:if>
<c:if test="${not empty schoolTag and schoolTag eq 'yes'}">
	<%@ include file="../common/school_side.jsp"%>
</c:if>
<script type="text/javascript">
	setSideNav("resourceCenter");
</script>