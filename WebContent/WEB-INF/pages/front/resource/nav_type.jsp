<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<span class="contentInNav">
	<%--  
	<c:if test="${type eq 'top'}">
		<a href="javascript:;" class="selected" index="0">精品课程</a>
	</c:if>
	<c:if test="${type ne 'top'}">
		<a href="../top/list.html" index="0">精品课程</a>
	</c:if>
	<c:if test="${type eq 'micro'}">
		<a href="javascript:;" class="selected" index="1">微课程</a>
	</c:if>
	<c:if test="${type ne 'micro'}">
		<a href="../micro/list.html" index="1">微课程</a>
	</c:if>
	<c:if test="${type eq 'resource'}">
		<a href="javascript:;" class="selected" index="2">教学资源</a>
	</c:if>
	<c:if test="${type ne 'resource'}">
		<a href="../resource/list.html" index="2">教学资源</a>
	</c:if>
	<c:if test="${type eq 'fun'}">
		<a href="javascript:;" class="selected" index="3">轻松一刻</a>
	</c:if>
	<c:if test="${type ne 'fun'}">
		<a href="../fun/list.html" index="3">轻松一刻</a>
	</c:if>
	 --%>
	<c:forEach items="${listResourceColumn }" var="resourceColumn">
		<c:if test="${resourceColumn.id == resourceColumnId}">
			<a href="javascript:;" class="selected" index="${resourceColumn.sort }" >${resourceColumn.columnName }</a>
		</c:if>
		<c:if test="${resourceColumn.id != resourceColumnId}">
			<a href="${ctx}/front/${resourceUrl }/${module }/${resourceColumn.id }.html?visitedUserType=${requestScope.visitedUserType }" index="${resourceColumn.sort }" >${resourceColumn.columnName }</a>
		</c:if>
	</c:forEach>
</span>
