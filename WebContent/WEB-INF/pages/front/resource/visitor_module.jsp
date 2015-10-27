<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
				<span class="navTab">
					<a href="${ctx}/front/${resourceUrl}/uploaded.html?visitedUserType=${requestScope.visitedUserType }" <c:if test="${module eq 'uploaded'}">class="currTab"</c:if> index="3">上传的资源</a>
					<a href="${ctx}/front/${resourceUrl}/favorite.html?visitedUserType=${requestScope.visitedUserType }" <c:if test="${module eq 'favorite'}">class="currTab"</c:if> index="4">收藏的资源</a>
				</span>
