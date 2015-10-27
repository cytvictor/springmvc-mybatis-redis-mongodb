<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="side">
	<div class="userMsg">
		<div class="userPhoto">
			<dt><img src="${ctx}/HeadImageServlet/${session_login_user.schoolPic }" alt="" width="90" height="90" id="sideHeadPic"></dt>
		</div>
		<dl>
			<dt>${sessionScope.session_login_user.baseDistrictName}</dt>
			<dd>
				<span class="school">${sessionScope.session_login_user.schoolName}</span>
			</dd>
		</dl>
		<div class="collectAndMsg collectAndMsg2">
			<%-- <span>
				<b>32</b>
				<a href="javascript:;">收藏</a>
			</span> --%>
			<span>
				<b id="informationCount">0</b>
				<a href="${sessionScope.session_domain_config.information }/information/page/school/informationPage.do?infoType=MESSAGES&sideClick=sideClick">信息</a>
			</span>
		</div>
	</div>

	<ul class="sideNav">
		<c:if test="${sessionScope.session_login_user.userType eq 'SCHOOL' }">
			<li class="workspace"><a href="${sessionScope.session_domain_config.platform }/front/workspace/schoolIndex.html">我的工作台</a></li>
			<li class="schoolActiveMenu"><a href="${sessionScope.session_domain_config.dynamic }/front/dynamic/school/index.html">最新动态</a></li>
			<li class="newsMenu"><a href="${sessionScope.session_domain_config.information }/information/page/school/informationPage.do?infoType=NEWS">信息中心</a></li>
			<li class="resourceCenter"><a href="${session_domain_config.resource }/front/schoolresource/uploaded.html">资源中心</a></li>
			<c:if test="${sessionScope.deploySysConfig.onlineClassFlag eq 'Y' }">
				<li class="onlineCourse"><a href="${sessionScope.session_domain_config.platform}/front/school/toRealtimeView.html">在线观摩</a></li>
			</c:if>
			<li class="datastatsMenu"><a href="${sessionScope.session_domain_config.dataStatistics }/front/school/toSchoolPersonCount.html">统计分析</a></li>
			<li class="userMenu"><a href="${sessionScope.session_domain_config.basedata}/front/manageruser/userManager.do">用户管理</a></li>
			<li class="adminMenu"><a href="${sessionScope.session_domain_config.basedata}/front/school/manager/list.do">管理员管理</a></li>
			<c:if test="${sessionScope.session_login_user.hasChildSchool eq 'Y' }">
				<li class="childSchoolMenu"><a href="${sessionScope.session_domain_config.basedata}/front/childschool/manager/list.do">分校管理</a></li>
			</c:if>
		</c:if>
		
		<c:if test="${sessionScope.session_login_user.userType eq 'TEACHER' }">
			<c:if test="${fn:contains(sessionScope.session_login_user.permission, '1')}">
				<li class="schoolActiveMenu"><a href="${sessionScope.session_domain_config.dynamic }/front/dynamic/school/index.html">最新动态</a></li>
			</c:if>
			<c:if test="${fn:contains(sessionScope.session_login_user.permission, '0')}">
				<li class="newsMenu"><a href="${sessionScope.session_domain_config.information }/information/page/school/informationPage.do?infoType=NEWS">信息中心</a></li>
			</c:if>
			<c:if test="${fn:contains(sessionScope.session_login_user.permission, '2')}">
				<li class="resourceCenter"><a href="${session_domain_config.resource }/front/schoolresource/uploaded.html">资源中心</a></li>
			</c:if>
			<c:if test="${fn:contains(sessionScope.session_login_user.permission, '3')}">
				<c:if test="${sessionScope.deploySysConfig.onlineClassFlag eq 'Y' }">
					<li class="onlineWatch"><a href="${sessionScope.session_domain_config.platform}/front/school/toRealtimeView.html">在线观摩</a></li>
				</c:if>
			</c:if>
			<c:if test="${fn:contains(sessionScope.session_login_user.permission, '4')}">
				<li class="datastatsMenu"><a href="${sessionScope.session_domain_config.dataStatistics }/front/school/toSchoolPersonCount.html">统计分析</a></li>
			</c:if>
			<c:if test="${fn:contains(sessionScope.session_login_user.permission, '5')}">
				<li class="userMenu"><a href="${sessionScope.session_domain_config.basedata}/front/manageruser/userManager.do">用户管理</a></li>
			</c:if>
			<c:if test="${fn:contains(sessionScope.session_login_user.permission, '6')}">
				<li class="adminMenu"><a href="${sessionScope.session_domain_config.basedata}/front/school/manager/list.do">管理员管理</a></li>
			</c:if>
			<c:if test="${sessionScope.session_login_user.hasChildSchool eq 'Y' and fn:contains(sessionScope.session_login_user.permission, '7')}">
				<li class="childSchoolMenu"><a href="${sessionScope.session_domain_config.basedata}/front/childschool/manager/list.do">分校管理</a></li>
			</c:if>
		</c:if>
	</ul>
	
</div>
<script type="text/javascript">
	// =================== 函数调用区域 =====================
	$(function() {
		getInformationNum() ;
	}) ;
		
	function getInformationNum(){
		$.getJSON("${sessionScope.session_domain_config.information}/information/management/countInformation.do?callback=?",function(data){
				$("#informationCount").html(data);
		});
	} ;
</script>