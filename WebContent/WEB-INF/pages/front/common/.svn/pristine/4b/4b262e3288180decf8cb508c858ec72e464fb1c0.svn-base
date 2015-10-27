<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="side">
	<div class="userMsg">
		<div class="userPhoto">
			<dt><img src="${sessionScope.session_domain_config.basedata}/HeadImageServlet/${sessionScope.session_login_user.headPic }" alt="" width="90" height="90" id="sideHeadPic"></dt>
			<dd class="userName">${sessionScope.session_login_user.realName }</dd>
		</div>
		<dl>
			<dd>
				<a href="${sessionScope.session_domain_config.dynamic }/front/visitor/visitorIndex.html?id=${sessionScope.session_login_user.schoolId }"><span class="school">${sessionScope.session_login_user.schoolName }</span></a>
			</dd>
		</dl>
		<div class="collectAndMsg">
			<span>
				<b id="resourceFavoriteCnt">0</b>
				<a href="${sessionScope.session_domain_config.resource }/front/resource/favorite.html">收藏</a>
			</span>
			<span>
				<b id="informationCount">0</b>
				<a href="${sessionScope.session_domain_config.information }/information/page/informationPage.do?infoType=MESSAGES&sideClick=sideClick">信息</a>
			</span>
		</div>
	</div>

	<ul class="sideNav">
		<li class="schoolActiveMenu"><a href="${sessionScope.session_domain_config.dynamic }/front/dynamic/index.html">最新动态</a></li>
		<li class="onlineTest"><a href="${session_domain_config.onlinetest }/student/onlineTest/index.html">测试中心</a></li>
		<li class="netHomework"><a href="${session_domain_config.homework }/front/studentHomework/studentHomeworkList.html?sideClick=sideClick">作业中心</a></li>
		<li class="error-text"><a href="${session_domain_config.onlinetest }/student/wrongQstBook/toWrongQstBook.html">错题本</a></li>
		<c:if test="${sessionScope.deploySysConfig.onlineAnswerFlag eq 'Y' }">
			<li class="onlineTutor"><a href="${session_domain_config.onlineanswer }/front/onlineTutor/student/showOnlineTutor.do">在线辅导</a></li>
		</c:if>
		<c:if test="${sessionScope.deploySysConfig.onlineClassFlag eq 'Y' }">
			<li class="onlineCourse"><a href="${sessionScope.session_domain_config.platform}/front/student/toRealtimeView.html">在线课堂</a></li>
		</c:if>
		<li class="onlineAnswerQs"><a href="${session_domain_config.onlineanswer }/front/onlineAnswer/student/onlineAnswerPage.do?userType=MYSELF&sideClick=sideClick">答疑中心</a></li>
		<li class="resourceCenter"><a href="${session_domain_config.resource }/front/resource/uploaded.html">资源中心</a></li>
		<li class="myBuy"><a href="${session_domain_config.resourcesearch }/front/resourcecart/showCartPage.html">我的购买</a></li>
		<li class="newsMenu"><a href="${session_domain_config.information }/information/page/informationPage.do?infoType=NEWS&sideClick=sideClick">信息中心</a></li>
		<li class="myClass"><a href="${session_domain_config.basedata }/front/student/myclass/members.do?sideClick=sideClick">我的班级</a></li>
	</ul>
</div>
<script type="text/javascript">
	// =================== 函数调用区域 =====================
	$(function() {
		getInformationNum() ;
		getResourceFavoriteCnt();
	}) ;
		
	function getInformationNum(){
		$.getJSON("${sessionScope.session_domain_config.information}/information/management/countInformation.do?callback=?",function(data){
				$("#informationCount").html(data);
		});
	} ;
	
	function getResourceFavoriteCnt(){
		$.getJSON("${sessionScope.session_domain_config.resource}/front/resource/favorite/countResouce.do?callback=?", function(data){ 
			$("#resourceFavoriteCnt").html(data);
		});
	}
</script>