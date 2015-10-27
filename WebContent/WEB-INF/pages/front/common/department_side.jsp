<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="side">
	<div class="userMsg">
		<div class="userPhoto">
			<div><a href="${sessionScope.session_domain_config.basedata }/front/personalData/personalDataIndex.html"><img src="${ctx}/HeadImageServlet/${session_login_user.headPic }" alt="" width="90" height="90" id="sideHeadPic"></a></div>
			<span class="userName">${session_login_user.realName }</span>
		</div>
		<dl>
			<dt class="mb10">${session_login_user.baseDistrictName }</dt>
			<dd>
				<span class="school" style="padding-right:0;margin-left:0;">
					<a href="${sessionScope.session_domain_config.basedata }/front/personalData/personalDataIndex.html">${session_login_user.baseOrgName }</a>
				</span>
			</dd>
		</dl>
		<div class="collectAndMsg collectAndMsg2">
			<span>
				<a href="${sessionScope.session_domain_config.information }/information/page/informationPage.do?infoType=MESSAGES&sideClick=sideClick"><b id="informationCount">0</b>
				信息</a>
			</span>
		</div>
	</div>


	<ul class="sideNav">
		<li class="workspace"><a href="${sessionScope.session_domain_config.platform }/front/workspace/departmentIndex.html?sideClick=sideClick">我的工作台</a></li>
		<c:if test="${session_login_user.userType eq 'ORG_MANAGER' or session_login_user.userType eq 'TEACH_ORG_MANAGER' or fn:contains(session_login_user.permission, '0') }">
		<li class="newsMenu"><a href="${sessionScope.session_domain_config.information }/information/page/informationPage.do?infoType=NEWS&sideClick=sideClick">信息中心</a></li>
		</c:if>
		<c:if test="${session_login_user.userType eq 'ORG_MANAGER' or session_login_user.userType eq 'TEACH_ORG_MANAGER' or fn:contains(session_login_user.permission, '3') }">
			<li class="netHomework"><a href="${sessionScope.session_domain_config.basedata}/front/org/manager/list.do?sideClick=sideClick">机构管理</a></li>
		</c:if>
		<c:if test="${session_login_user.userType eq 'ORG_MANAGER' or fn:contains(session_login_user.permission, '1')}">
			<li class="userMenu"><a href="${sessionScope.session_domain_config.basedata}/front/org/orgusermanager/list.do?sideClick=sideClick">帐号管理</a></li>
		</c:if>
		<c:if test="${session_login_user.userType eq 'ORG_MANAGER' or session_login_user.userType eq 'TEACH_ORG_MANAGER' or fn:contains(session_login_user.permission, '2') }">
			<li class=resourceCenter><a href="${session_domain_config.resource }/front/orgresource/uploaded.html">资源中心</a></li>
		</c:if>
		<c:if test="${session_login_user.userType eq 'ORG_MANAGER' or session_login_user.userType eq 'TEACH_ORG_MANAGER' or fn:contains(session_login_user.permission, '4') }">
		<li class="datastatsMenu"><a href="${sessionScope.session_domain_config.dataStatistics}/front/department/toDepartmentPersonCount.html">统计分析</a></li>
		</c:if>
		<c:if test="${session_login_user.userType eq 'ORG_MANAGER' or session_login_user.userType eq 'TEACH_ORG_MANAGER' or fn:contains(session_login_user.permission, '5') }">
			<c:if test="${sessionScope.deploySysConfig.onlineClassFlag eq 'Y' }">
				<li class="onlineCourse"><a href="${sessionScope.session_domain_config.platform}/front/department/toRealtimeView.html">在线观摩</a></li>
			</c:if>
		</c:if>
		<c:if test="${sessionScope.session_login_user.orgLevel eq '1' and sessionScope.session_login_user.orgType eq 'DJG' and fn:contains(session_login_user.permission, '6')}">
			<li class="systemSetup"><a href="${sessionScope.session_domain_config.dynamic}/front/sysConfig/index.html?sideClick=sideClick">系统设置</a></li>
		</c:if>
		<li class="resourceLog"><a href="${session_domain_config.resource }/resourceLog/resourceLogIndex.html">资源日志</a></li>
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