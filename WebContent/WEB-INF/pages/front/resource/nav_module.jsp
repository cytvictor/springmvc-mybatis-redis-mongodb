<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<span class="navTab">
	<c:if test="${not empty schoolTag and schoolTag eq 'yes'}"><%-- 学校 --%>
		<a href="${ctx}/front/schoolresource/shared.html" <c:if test="${module eq 'shared'}">class="currTab"</c:if> index="0">共享资源</a>
		<a href="${ctx}/front/schoolresource/higherpush.html" <c:if test="${module eq 'higherpush'}">class="currTab"</c:if> index="1">上级推送</a>
		<a href="${ctx}/front/schoolresource/lowerpush.html" <c:if test="${module eq 'lowerpush'}">class="currTab"</c:if> index="2">教师上报</a>
		<a href="${ctx}/front/schoolresource/uploaded.html" <c:if test="${module eq 'uploaded'}">class="currTab"</c:if> index="3">学校上传</a>
	</c:if>
	
	<c:if test="${empty schoolTag or schoolTag eq 'no'}">
		<c:if test="${loginUser.userType eq 'TEACHER'}"><%-- 老师  --%>
			<a href="${ctx}/front/resource/schoolpush.html" <c:if test="${module eq 'schoolpush'}">class="currTab"</c:if> index="0">学校推送</a>
			<a href="${ctx}/front/resource/studentpush.html" <c:if test="${module eq 'studentpush'}">class="currTab"</c:if> index="1">学生推送</a>
			<a href="${ctx}/front/resource/uploaded.html" <c:if test="${module eq 'uploaded'}">class="currTab"</c:if> index="2">我的上传</a>
			<a href="${ctx}/front/resource/favorite.html" <c:if test="${module eq 'favorite'}">class="currTab"</c:if> index="3">我的收藏</a>
			<a href="${ctx}/front/resource/history.html" <c:if test="${module eq 'history'}">class="currTab"</c:if> index="4">我的浏览</a>
		</c:if>
		<c:if test="${loginUser.userType eq 'STUDENT'}"><%-- 学生  --%>
			<a href="${ctx}/front/resource/recommend.html" <c:if test="${module eq 'recommend'}">class="currTab"</c:if> index="0">推荐资源</a>
			<a href="${ctx}/front/resource/uploaded.html" <c:if test="${module eq 'uploaded'}">class="currTab"</c:if> index="1">我的上传</a>
			<a href="${ctx}/front/resource/favorite.html" <c:if test="${module eq 'favorite'}">class="currTab"</c:if> index="2">我的收藏</a>
			<a href="${ctx}/front/resource/history.html" <c:if test="${module eq 'history'}">class="currTab"</c:if> index="3">我的浏览</a>
		</c:if>	
		
		<c:if test="${loginUser.userType eq 'TEACH_ORG_MANAGER'}"><%-- 学校管理员 --%>
			<a href="${ctx}/front/orgresource/shared.html" class="currTab" index="0">共享资源</a>
		</c:if>
		
		<c:if test="${loginUser.userType eq 'ORG' or loginUser.userType eq 'ORG_MANAGER'}"><%-- 机构 --%>
			<a href="${ctx}/front/orgresource/shared.html" <c:if test="${module eq 'shared'}">class="currTab"</c:if> index="0">共享资源</a>
			
			<c:if test="${loginUser.orgLevel ne '1'}"><%-- 市、县 --%>
			<a href="${ctx}/front/orgresource/higherpush.html" <c:if test="${module eq 'higherpush'}">class="currTab"</c:if> index="1">上级推送</a>
			<a href="${ctx}/front/orgresource/lowerpush.html" <c:if test="${module eq 'lowerpush'}">class="currTab"</c:if> index="2">下级上报</a>
			<a href="${ctx}/front/orgresource/uploaded.html" <c:if test="${module eq 'uploaded'}">class="currTab"</c:if> index="3">本级上传</a>
			</c:if>
			
			<c:if test="${loginUser.orgLevel eq '1'}"><%-- 省 --%>
			<a href="${ctx}/front/orgresource/lowerpush.html" <c:if test="${module eq 'lowerpush'}">class="currTab"</c:if> index="1">下级上报</a>
			<a href="${ctx}/front/orgresource/uploaded.html" <c:if test="${module eq 'uploaded'}">class="currTab"</c:if> index="2">本级上传</a>
			</c:if>
		</c:if>
	</c:if>
</span>
