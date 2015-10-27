<%@ page contentType="text/html; charset=UTF-8"%>
<div class="side">
	<div class="userMsg">
		<div class="userPhoto">
			<div>
				<dt><img id="headerPic" src="${ctx }/HeadImageServlet/${requestScope.visitedHeadPic }" alt="" width="90" height="90"></dt>
			</div>
			<dd class="userName" id="visitedName">${requestScope.visitedName }</dd>
		</div>
		<dl>
			<dt id="visitedSchoolName"></dt>
		</dl>
		<a class="focus" href="javascript:;" id="showConcern">+关注</a>
	</div>

	<ul class="sideNav">
		<li class="schoolActiveMenu"><a href="${session_domain_config.dynamic }/front/visitor/visitorIndex.html?id=${requestScope.ids}">最新动态</a></li>
		<c:if test="${requestScope.visitedUserType eq 'SCHOOL' }">
			<li class="newsMenu"><a href="${session_domain_config.information }/information/page/forward2VisitorPage/${requestScope.ids}.do?infoType=NEWS&visitedUserType=${requestScope.visitedUserType}">信息中心</a></li>
		</c:if>
		<c:if test="${requestScope.visitedUserType ne 'PARENT' }">
			<c:if test="${requestScope.visitedUserType eq 'SCHOOL' }">
				<li class="resourceCenter"><a href="${session_domain_config.resource }/front/resource/visitor/${requestScope.ids}/school.html?visitedUserType=${requestScope.visitedUserType }">资源中心</a></li>
			</c:if>
			<c:if test="${requestScope.visitedUserType ne 'SCHOOL' }">
				<li class="resourceCenter"><a href="${session_domain_config.resource }/front/resource/visitor/${requestScope.ids}/uploaded.html?visitedUserType=${requestScope.visitedUserType }">资源中心</a></li>
			</c:if>
		</c:if>
	</ul>
</div>

<script type="text/javascript">
var userType = '${sessionScope.session_login_user.userType}';
var visitedUserType = '${requestScope.visitedUserType}';
	/* <c:if test="${requestScope.visitedUserType ne 'PARENT' }">
		<c:if test="${requestScope.visitedUserType eq 'SCHOOL' }">
			$.getJSON("${session_domain_config.information }/information/management/headerPic.do?callback=?",{"schoolId":'${requestScope.ids}', "roleType":'ADMIN'}, function(data){
				$("#headerPic").attr("src", '${ctx }/HeadImageServlet/'+data[0].PIC) ;
				$("dd[class=userName]").html(data[0].SCHOOLNAME) ;
			}) ;
		</c:if>
	</c:if> */
	$.getJSON("${session_domain_config.dynamic }/front/visitor/getVisitorInfo.do?callback=?",{"userId":'${requestScope.ids}', "userType":'${requestScope.visitedUserType}'}, function(data){
		data = data || "";
		var visitedInfo = eval(data);
		$("#headerPic").attr("src", '${ctx }/HeadImageServlet/'+visitedInfo.headPic);
		$("#visitedName").html(visitedInfo.userName);
		if("TEACHER" == visitedUserType || "STUDENT" == visitedUserType){
			$("#visitedSchoolName").html('<a href="${sessionScope.session_domain_config.dynamic }/front/visitor/visitorIndex.html?id='+visitedInfo.schoolId+'">'+visitedInfo.schoolName+'</a>');
		}
		if("TEACHER" == userType || "STUDENT" == userType || "PARENT" == userType){
			if("SCHOOL" != visitedUserType){
				$("#showConcern").on("click", function(){
					addConcern('${requestScope.ids}');
				});
				if(visitedInfo.concernFlag){
					if('1' == visitedInfo.canCancel){
						$("#showConcern").html("取消关注").css("color", "#666");
					}else{
						$("#showConcern").off("click");
						$("#showConcern").html("&radic;&nbsp;已关注").css("color", "#666");
					}
				}
			}else{
				$("#showConcern").hide();
			}
		}else{
			$("#showConcern").hide();
		}
	});
	
	function addConcern(visitedUserId){
		if("SCHOOL" == userType || "SCHOOL" == visitedUserType){
			Win.alert('关注失败！');
		}else{
			var op = $("#showConcern").html();
			var flag = "1";
			if(op != '+关注'){
				flag = "0";
			}
			$.getJSON("${session_domain_config.dynamic }/front/concern/addVisitedConcern.do?callback=?",{"visitedUserId":'${requestScope.ids}',"flag":flag},function(data){
				if("1" == data){
					if(flag == "1"){
						$("#showConcern").html("取消关注").css("color", "#666");
						Win.alert('关注成功');
					}else{
						$("#showConcern").html("+关注").addClass("focused").css("color", "#219ee0");
						Win.alert('取消成功');
					}
				}else{
					Win.alert('操作失败！');
				}
			});
		}
	}
</script>