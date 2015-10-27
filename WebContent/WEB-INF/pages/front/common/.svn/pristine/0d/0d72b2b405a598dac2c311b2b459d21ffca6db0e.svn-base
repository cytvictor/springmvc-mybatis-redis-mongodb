<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${ctx }/public/css/parent.css" />
<div class="side">
	<div class="userMsg">
		<div class="changeUserBox">
			<div class="userPhoto">
				<div><img src="${ctx}/HeadImageServlet/${sessionScope.session_login_user.headPic }" alt="" width="90" height="90" id="sideHeadPic"></div>
				<span class="userName">${sessionScope.session_login_user.realName }</span>
			</div>
			<c:if test="${not empty session_login_user.rAccountId }">
				<div class="changeIdentity">
					<a style="color: white;" href="javascript:;" onclick="changeAccountId(this);" accountId="${session_login_user.rAccountId }">切换到老师空间</a>
				</div>
			</c:if>
		</div>
		<span><a style="color: white;" href="${sessionScope.session_domain_config.basedata}/front/personalData/personalDataIndex.do">帐户管理</a></span>
		<div class="collectAndMsg collectAndMsg2">
			<!-- <span>
				<b>32</b>
				<a href="javascript:;">收藏</a>
			</span> -->
			<span>
				<b id="informationCount">0</b>
				<a href="${sessionScope.session_domain_config.information }/information/page/informationPage.do?infoType=MESSAGES&sideClick=sideClick">信息</a>
			</span>
		</div>
		<div class="childrenMsg clearfix">
			<a href="${sessionScope.session_domain_config.dynamic }/front/visitor/visitorIndex.html?id=${sessionScope.session_login_user.curChildUserId }"><img src="${ctx}/HeadImageServlet/${sessionScope.session_login_user.childHeadPic }" alt="" width="45" height="45"/></a>
			<span class="name">${sessionScope.session_login_user.childName }</span>
			<c:if test="${sessionScope.session_login_user.childCount gt 1 }">
				<a href="${sessionScope.session_domain_config.sso }/front/parent/changeChild.html" class="changeChild">切换</a>
			</c:if>
		</div>
		<div class="childrenSchoolMsg">
			<p><a href="${sessionScope.session_domain_config.dynamic }/front/visitor/visitorIndex.html?id=${sessionScope.session_login_user.schoolId }">${sessionScope.session_login_user.schoolName }</a></p>
			<p>${sessionScope.session_login_user.className }</p>
		</div>
	</div>

	<ul class="sideNav">
		<li class="schoolActiveMenu"><a href="${sessionScope.session_domain_config.dynamic }/front/dynamic/index.html">最新动态</a></li>
		<li class="trackMenu"><a href="${session_domain_config.homework }/front/studyFollow/studyFollowIndex.html">学业分析</a></li>
		<li class="myBuy"><a href="${session_domain_config.resourcesearch }/front/resourcecart/showCartPage.html">我的购买</a></li>
		<li class="newsMenu"><a href="${session_domain_config.information }/information/page/informationPage.do?infoType=NEWS">信息中心</a></li>
		<c:if test="${sessionScope.deploySysConfig.onlineClassFlag eq 'Y' }">
			<li class="onlineCourse"><a href="${sessionScope.session_domain_config.platform}/front/parent/toRealtimeView.html">在线观摩</a></li>
		</c:if>
		<c:if test="${sessionScope.deploySysConfig.parentEWMFlag eq 'Y' }">
			<li style="border-bottom:none;">
				<img width="128" height="128" src="${sessionScope.session_domain_config.publicServer}/${sessionScope.deploySysConfig.parentEWM }" class="mt20">
			</li>
		</c:if>
	</ul>
</div>
<script type="text/javascript">
/**
 * 切换身份
 */
function changeAccountId(obj){
	var rAccountId = $(obj).attr("accountId");
	if(rAccountId==""){
		Win.alert("切换身份失败！");
		return;
	}
	window.location.href = "${ctx}/common/autoLogin.do?url=http://basedata.rrt.com/rrt_basedata/front/login/index.do&autoLoginFlag=1";
	
} ;

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