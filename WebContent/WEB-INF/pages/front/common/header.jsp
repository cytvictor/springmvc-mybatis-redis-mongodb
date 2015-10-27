<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="header" id="header">
	<div class="headerInner">

		<a href="javascript:;" class="logo">
			<c:if test="${sessionScope.deploySysConfig.orgOrSchoolBannerPicFlag eq 'Y' }">
				<img src="${sessionScope.session_domain_config.publicServer }/${sessionScope.deploySysConfig.orgOrSchoolBannerPic }" alt="${sessionScope.deploySysConfig.bannerPlatformName }" />
			</c:if>
			<c:if test="${sessionScope.deploySysConfig.bannerPlatformNameFlag eq 'Y' }">
				<c:choose>
					<c:when test="${sessionScope.session_login_user.userType eq 'ORG_MANAGER' or sessionScope.session_login_user.userType eq 'ORG' or sessionScope.session_login_user.userType eq 'TEACH_ORG_MANAGER' }">
						<span class="manageName"><c:if test="${sessionScope.deploySysConfig.bannerAreaNameFlag eq 'Y' }">${sessionScope.session_login_user.baseDistrictName}</c:if>${sessionScope.deploySysConfig.bannerPlatformName }</span>
					</c:when>
					<c:when test="${sessionScope.session_login_user.userType eq 'SCHOOL' }">
						<span class="manageName"><c:if test="${sessionScope.deploySysConfig.bannerSchoolNameFlag eq 'Y' }">${sessionScope.session_login_user.schoolName}</c:if>${sessionScope.deploySysConfig.bannerPlatformName }</span>
					</c:when>
					<c:otherwise>
						<span style="font-size: 20px; color: white; margin-left: 10px;">${sessionScope.deploySysConfig.bannerPlatformName }</span>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${sessionScope.deploySysConfig.bannerPlatformNameFlag eq 'N' }">
				<c:choose>
					<c:when test="${sessionScope.session_login_user.userType eq 'ORG_MANAGER' or sessionScope.session_login_user.userType eq 'ORG' or sessionScope.session_login_user.userType eq 'TEACH_ORG_MANAGER' }">
						<span class="manageName"><c:if test="${sessionScope.deploySysConfig.bannerAreaNameFlag eq 'Y' }">${sessionScope.session_login_user.baseDistrictName}</c:if></span>
					</c:when>
					<c:when test="${sessionScope.session_login_user.userType eq 'SCHOOL' }">
						<span class="manageName"><c:if test="${sessionScope.deploySysConfig.bannerSchoolNameFlag eq 'Y' }">${sessionScope.session_login_user.schoolName}</c:if></span>
					</c:when>
				</c:choose>
			</c:if>
		</a>

		<div class="header-right">
			<span class="searchBar clearfix placeholderWaper">
				<label for="fontSearchInput">搜索</label>
				<input type="text" class="search" id="fontSearchInput" placeholder="搜索" />
				<a href="javascript:void(0);" id="allsitesearch" onclick="allSiteSearch()" class="searchBtn"></a>
			</span>

			<ul class="loginBar">
				<%--  <li><a href="javascript:;">记录</a></li>
				<li class="news"><a href="javascript:;"><i>2</i></a></li>  --%>
				<li class="userName"><a href="javascript:;" title="${sessionScope.session_login_user.userType eq 'SCHOOL'?sessionScope.session_login_user.schoolName:sessionScope.session_login_user.realName}">${fn:substring(sessionScope.session_login_user.userType eq 'SCHOOL'?sessionScope.session_login_user.schoolName:sessionScope.session_login_user.realName, 0, 10)}</a></li>
				<li class="userPhoto">
					<a href="javascript:;">
						<img src="${sessionScope.session_domain_config.dynamic }/HeadImageServlet/${sessionScope.session_login_user.headPic }" alt="" width="45" height="45" id="headHeadPic">
					</a>
					<div class="tipBox">
						<span class="trangle">◆</span>
						<c:choose>
							<c:when test="${sessionScope.session_login_user.userType eq 'TEACHER'}">
								<div class="clearfix">
									<a href="${session_domain_config.platform}/index.html" class="tipList myHome">我的空间</a>
									<a href="${sessionScope.session_domain_config.dynamic }/front/concern/toIndex.html" class="tipList myConcern">我的关注</a>
									<a href="${sessionScope.session_domain_config.information }/information/page/informationPage.do?infoType=MESSAGES&sideClick=sideClick" class="tipList msg">信息</a>
									<a href="${sessionScope.session_domain_config.dynamic}/front/message/messageIndex.html" class="tipList privateLetter">私信(<b class="red" id="messageCount">0</b>)</a>
									<a href="${sessionScope.session_domain_config.resource }/front/resource/history.html" class="tipList viewRecord">我的浏览</a>
									<a href="${sessionScope.session_domain_config.resource }/front/resource/favorite.html" class="tipList myCollect">我的收藏</a>
								</div>
							</c:when>
							<c:when test="${sessionScope.session_login_user.userType eq 'STUDENT'}">
								<div class="clearfix">
									<a href="${session_domain_config.platform}/index.html" class="tipList myHome">我的空间</a>
									<a href="${sessionScope.session_domain_config.basedata }/front/personalData/personalDataIndex.html" class="tipList personalData">个人资料</a>
									<a href="${sessionScope.session_domain_config.information }/information/page/informationPage.do?infoType=MESSAGES&sideClick=sideClick" class="tipList msg">信息</a>
									<a href="${sessionScope.session_domain_config.dynamic}/front/message/messageIndex.html" class="tipList privateLetter">私信(<b class="red" id="messageCount">0</b>)</a>
									<a href="${sessionScope.session_domain_config.resource }/front/resource/history.html" class="tipList viewRecord">我的浏览</a>
									<a href="${sessionScope.session_domain_config.resource }/front/resource/favorite.html" class="tipList myCollect">我的收藏</a>
								</div>
							</c:when>
							<c:when test="${sessionScope.session_login_user.userType eq 'PARENT'}">
								<div class="clearfix">
									<a href="${session_domain_config.platform}/index.html" class="tipList myHome">我的空间</a>
									<a href="${sessionScope.session_domain_config.dynamic}/front/message/messageIndex.html" class="tipList privateLetter">私信(<b class="red" id="messageCount">0</b>)</a>
								</div>
							</c:when>
							<c:when test="${sessionScope.session_login_user.userType eq 'SCHOOL' || 
										sessionScope.session_login_user.userType eq 'ORG' || 
										sessionScope.session_login_user.userType eq 'ORG_MANAGER' || 
										sessionScope.session_login_user.userType eq 'TEACH_ORG_MANAGER'}">
								<div class="clearfix">
									<c:if test="${sessionScope.session_login_user.userType eq 'SCHOOL' }">
										<a href="${sessionScope.session_domain_config.platform }/front/workspace/schoolIndex.html" class="tipList workPlace">我的工作台</a>
									</c:if>
									<c:if test="${sessionScope.session_login_user.userType eq 'ORG_MANAGER' || 
												sessionScope.session_login_user.userType eq 'ORG' ||
												sessionScope.session_login_user.userType eq 'TEACH_ORG_MANAGER'}">
										<a href="${sessionScope.session_domain_config.platform }/front/workspace/departmentIndex.html" class="tipList workPlace">我的工作台</a>
									</c:if>
									<c:if test="${sessionScope.session_login_user.userType eq 'ORG' || 
												sessionScope.session_login_user.userType eq 'ORG_MANAGER' || 
												sessionScope.session_login_user.userType eq 'TEACH_ORG_MANAGER'}">
										<a href="${sessionScope.session_domain_config.information }/information/page/informationPage.do?infoType=MESSAGES" class="tipList msg">信息</a>
									</c:if>		
									<c:if test="${sessionScope.session_login_user.userType eq 'SCHOOL'}">
										<a href="${sessionScope.session_domain_config.information }/information/page/school/informationPage.do?infoType=MESSAGES" class="tipList msg">信息</a>
									</c:if>		
									<a href="${sessionScope.session_domain_config.basedata }/front/personalData/personalDataIndex.html" class="tipList personalData">个人资料</a>
									<c:if test="${sessionScope.session_login_user.userType eq 'SCHOOL'}">
										<a class="tipList uploadSource" href="${sessionScope.session_domain_config.resource }/front/resUpload/school/upload.html">上传资源</a>
									</c:if>
									<c:if test="${sessionScope.session_login_user.userType eq 'ORG_MANAGER' || 
										sessionScope.session_login_user.userType eq 'ORG' }">
										<a class="tipList uploadSource" href="${sessionScope.session_domain_config.resource }/front/resUpload/org/upload.html">上传资源</a>
									</c:if>
									<c:if test="${sessionScope.session_login_user.userType eq 'TEACH_ORG_MANAGER'  }">
										<a class="tipList uploadSource" href="${sessionScope.session_domain_config.resource }/front/orgresource/uploaded.html">资源中心</a>
									</c:if>
								</div>
							</c:when>
						</c:choose>
						<a href="${session_domain_config.sso}/logout.html" class="btn mt10 quit">退出</a>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>

<div class="nav">
	<ul class="navInner">
		<li class="index selected"><a href="${session_domain_config.platform}/index.html" class="selected">首页</a></li>
		<li class="colorBar">
			<c:choose>
				<c:when test="${sessionScope.session_login_user.orgLevel eq '1'}">
					<span class="color7"></span>
					<span class="color8"></span>
					<span class="color9"></span>
					<span class="color10"></span>
					<span class="color-btn"></span>
				</c:when>
				<c:when test="${sessionScope.session_login_user.orgLevel eq '2'}">
					<span class="color8"></span>
					<span class="color7"></span>
					<span class="color9"></span>
					<span class="color10"></span>
					<span class="color-btn"></span>
				</c:when>
				<c:when test="${sessionScope.session_login_user.orgLevel eq '3'}">
					<span class="color9"></span>
					<span class="color7"></span>
					<span class="color8"></span>
					<span class="color10"></span>
					<span class="color-btn"></span>
				</c:when>
				<c:when test="${sessionScope.session_login_user.userType eq 'SCHOOL'}">
					<span class="color10"></span>
					<span class="color7"></span>
					<span class="color8"></span>
					<span class="color9"></span>
					<span class="color-btn"></span>
				</c:when>
				<c:when test="${sessionScope.session_login_user.userType eq 'TEACHER'}">
					<span class="color5"></span>
					<span class="color0"></span>
					<span class="color1"></span>
					<span class="color2"></span>
					<span class="color3"></span>
					<span class="color4"></span>
					<span class="color6"></span>
					<span class="color-btn"></span>
				</c:when>
				<c:when test="${sessionScope.session_login_user.userType eq 'STUDENT'}">
					<span class="color4"></span>
					<span class="color0"></span>
					<span class="color1"></span>
					<span class="color2"></span>			
					<span class="color3"></span>
					<span class="color5"></span>
					<span class="color6"></span>
					<span class="color-btn"></span>
				</c:when>
				<c:when test="${sessionScope.session_login_user.userType eq 'PARENT'}">
					<span class="color6"></span>
					<span class="color0"></span>
					<span class="color1"></span>
					<span class="color2"></span>
					<span class="color3"></span>
					<span class="color4"></span>
					<span class="color5"></span>
					<span class="color-btn"></span>
				</c:when>
				<c:otherwise>
					<span class="color0"></span>
					<span class="color1"></span>
					<span class="color2"></span>
					<span class="color3"></span>
					<span class="color4"></span>
					<span class="color5"></span>
					<span class="color6"></span>
					<span class="color-btn"></span>
				</c:otherwise>
			</c:choose>
		</li>
	</ul>
</div>
<script>
//颜色风格更改 
(function(){
	var orgLevel = '${sessionScope.session_login_user.orgLevel }';
	var userType = '${sessionScope.session_login_user.userType }';

	var colorChange =  function (elm) {
		if (!elm) return;
		$('.colorBar .selected').removeClass('selected');
		$(elm).addClass('selected');
		var colorName = elm.className.match(/color\d+/)[0];
		var bodyColorName = document.body.className;
		document.body.className = bodyColorName.replace(/color\d+/g, '') + " " + colorName;
		$.post("${ctx}/common/changeBGColor.do?r=" + random(),
			{colorName:colorName},
			function(data) {
				
			}, 'json');
	}
	$('.colorBar').on('click', 'span', function () {
		colorChange(this);
	});

	var bgColor;
	var arrCookie = document.cookie.split("; ");
	for (var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split('=');
		if ('bgColor${sessionScope.session_login_user.userId}' == arr[0]) {
			bgColor = arr[1];
			break;
		}
	}
	
	if (orgLevel == 1 || orgLevel == 2 || orgLevel == 3) {
		bgColor = bgColor || ("color" + (6 + parseInt(orgLevel)));
	}  else if (userType == 'SCHOOL') {
		bgColor = bgColor || "color10";
	} else if (userType == 'TEACHER') {
		bgColor = bgColor || "color5";
	} else if (userType == 'STUDENT') {
		bgColor = bgColor || "color4";
	} else if (userType == 'PARENT') {
		bgColor = bgColor || "color6";
	} else {
		bgColor = bgColor || "color0";
	}
	
	
	colorChange(window.top.$('.colorBar .' + bgColor)[0]);
	
	// logo不同角色更换
	<%-- if (userType !== 'TEACHER' && userType !== 'STUDENT' && userType !== 'PARENT') {
		$('.logo img').attr('src', '${ctx}/public/img/index/logo2.png');
	}--%>
})();
$(function(){
	$('.color-btn').siblings().hide();
	$('.colorBar').on('mouseover', function(){
		$('.color-btn').siblings().show();
	}).on('mouseout', function(){
		$('.color-btn').siblings().hide();
	});
});

$(function(){
	<c:if test="${sessionScope.session_login_user.userType eq 'TEACHER' or sessionScope.session_login_user.userType eq 'PARENT' or sessionScope.session_login_user.userType eq 'STUDENT'}">
		getNotReadMessage();
	</c:if>
});
function setSideNav(className){
	$(".sideNav a").removeClass("selected");
	$("." + className +" a").addClass("selected");
}
//个人资料点击页签切换
function switchTO(i) {
	$(".currTab").removeClass("currTab");
	$(".navTab a").eq(i).addClass("currTab");
	$(".currBox").removeClass("currBox");
	$(".tabBox").eq(i).addClass("currBox");
}
function getNotReadMessage(){
	$.getJSON("${sessionScope.session_domain_config.dynamic}/front/message/getNotReadNum.do?callback=?", function(data){ 
		$("#messageCount").html(data);
	}); 
}

//页面加载完成调用
$(document).ready(function(){
	createHomeTopTabs();
});

//全局变量, 判断tabs是否创建完成
var isTabCreated = false;

//动态创建头部导航栏
function createHomeTopTabs(){
	var defineHtml = "";
	<c:if test="${not empty sessionScope.deploySysConfig.column1Name }">
		defineHtml += '<li class="bestCourse resource_column_item"><a href="${sessionScope.deploySysConfig.column1Address }" style="background:none; padding:0px;">${sessionScope.deploySysConfig.column1Name }</a></li>';
	</c:if>
	<c:if test="${not empty sessionScope.deploySysConfig.column2Name }">
		defineHtml += '<li class="bestCourse resource_column_item"><a href="${sessionScope.deploySysConfig.column2Address }" style="background:none; padding:0px;">${sessionScope.deploySysConfig.column2Name }</a></li>';
	</c:if>
	<c:if test="${not empty sessionScope.deploySysConfig.column3Name }">
		defineHtml += '<li class="bestCourse resource_column_item"><a href="${sessionScope.deploySysConfig.column3Address }" style="background:none; padding:0px;">${sessionScope.deploySysConfig.column3Name }</a></li>';
	</c:if>
	$.getJSON("${session_domain_config.resourcesearch }/front/homeTop/createHomeTopTabs.do?callback=?", function(data){ 
		   data = eval(data);
			$(".resource_column_item").remove();
			var html = '';
			$.each(data, function(index, json){
				if(json.boundBasicInfo != 'Y') {
					html += '<li class="talentShow resource_column_item '+json.columnId+'" ><a href="${session_domain_config.resourcesearch}/front/homeTop/mixture/mixtureindex.html?columnId='+json.columnId+'" style="background:none; padding:0px;">'+json.columnName+'</a></li>';
				} else if (json.columnType == 'VIDEO') {
					html += '<li class="bestCourse resource_column_item '+json.columnId+'" ><a href="${session_domain_config.resourcesearch}/front/homeTop/video/videoindex.html?columnId='+json.columnId+'" style="background:none; padding:0px;">'+json.columnName+'</a></li>';
				} else {
					html += '<li class="teachSource resource_column_item '+json.columnId+'" ><a href="${session_domain_config.resourcesearch}/front/homeTop/document/documentindex.html?columnId='+json.columnId+'" style="background:none; padding:0px;">'+json.columnName+'</a></li>';
				}
			});
			html +=defineHtml;
			$(".index").after(html);
			
			isTabCreated = true;
		});  
}

//全站搜索
function allSiteSearch() {
	var searchKey = $("#fontSearchInput").val();
	if($.trim(searchKey) == "") {
		Win.alert("搜索内容不能为空");
		return false;
	}
	
	searchKey = $.trim(searchKey);
	
	searchKey = escape(searchKey);//对字符串编码,主要针对特殊字符
	
	searchKey = encodeURI(searchKey);//对字符串编码,主要针对汉字
	
	searchKey = searchKey.replace(/\+/g, '%2B');//+不会被上面的编码函数编码,然而+在get请求的时候会被当作空格处理,因此手动将+编码

	var url = "${session_domain_config.resourcesearch}/front/homeTop/allSiteSearch.html?searchKey="+searchKey;
	
	openWin(url);
}

//打开新窗口(代替window.open,防止浏览器拦截)
function openWin(url) {
	var a = document.createElement("a");
	a.setAttribute("href", url);
	a.setAttribute("target", "_blank");
	a.setAttribute("id", "openwin");
	document.body.appendChild(a);
	a.click();
}


//键盘事件,当点击回车时自动进行搜索
/* document.addEventListener('keypress', function(event){
	 var e = null;
	 if(!event) {
		 e = window.event;  
	 } else {
		 e = event;
	 }
	 
	 if(13 == e.keyCode && "fontSearchInput" == document.activeElement.id) {
		 $("#allsitesearch").click();
	 }
}); */
//键盘事件,当点击回车时自动进行搜索
$(document).on('keypress', function(event){
	 if(13 == event.which && "fontSearchInput" == document.activeElement.id) {
		 $("#allsitesearch").click();
	 }
})

//根据id设置头部导航栏选中样式
function setTopNav(tabId){
	$(".navInner li").removeClass("selected");
	$("." + tabId).addClass("selected");
	$(".navInner a").removeClass("selected");
	$("." + tabId +" a").addClass("selected");
}
//检查是否是初始密码
suggestChangePSD();
function suggestChangePSD(){
	var needChange = "${sessionScope.session_login_user.changeInitPSD}";
	if(needChange=='NO'){
		$.getJSON("${session_domain_config.basedata }/front/personalData/checkNeedChangeInitPSD.do?callback=?",function(data){
			data = eval(data);
			if(data.result){
				Win.confirm({
					html:'您使用的是初始密码，建议您修改！',
					beforeClose:function(){
						$.getJSON("${session_domain_config.basedata }/front/personalData/updateSessionAndRedisChangeInitPSD.do?callback=?",{"changeInitPSD":'YES'},function(){
							data = eval(data);
						});
					}
				},function(){
					 window.location.href="${session_domain_config.basedata}/front/personalData/personalDataIndex.html?changePsdTab=true";
				},function(){});
			}
		});
	}
}
</script>