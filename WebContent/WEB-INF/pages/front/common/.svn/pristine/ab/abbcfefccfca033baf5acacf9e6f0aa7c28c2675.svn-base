<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta charset="UTF-8" />
<meta name="renderer" content="webkit">
<title>${sessionScope.deploySysConfig.bannerPlatformName }</title>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("/"))
		request.getSession().setAttribute("ctx", "");
	else
		request.getSession().setAttribute("ctx", contextPath);
	request.setAttribute("mailReg", "^([a-zA-Z0-9]+[_|\\-|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\-|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
	request.setAttribute("userRealNameReg", "^[\\u4E00-\\u9FA5]+$");
	request.setAttribute("telephoneReg", "^(0[1-9]\\d{1,2}\\-)?[1-9]\\d{6,7}$");
	request.setAttribute("mobileReg", "^1[3|4|5|8][0-9]\\d{8}$");
	request.setAttribute("userIdReg", "^[0-9a-zA-Z]{6,20}$");
	request.setAttribute("passwordReg", "^.{6,18}$");
	String str = request.getHeader("User-Agent");
	if(str.indexOf("Android")>0 || str.indexOf("iPad")>0 || str.indexOf("Macintosh")>0){
		request.setAttribute("systype", "pad");
	}else{
		request.setAttribute("systype", "pc");
	}
	if(str.indexOf("Android")>0){
		request.setAttribute("phoneType", "Android");
	}else if(str.indexOf("iPad")>0 || str.indexOf("Macintosh")>0){
		request.setAttribute("phoneType", "ios");
	}else{
		request.setAttribute("phoneType", "all");
	}
%>
<link media="all" type="text/css" rel="stylesheet" href="${ctx }/public/css/reset.css"/>
<link media="all" type="text/css" rel="stylesheet" href="${ctx }/public/css/common.css"/>
<link media="all" type="text/css" rel="stylesheet" href="${ctx }/public/css/index.css"/>

<% //1：省；2：市；3：县；SCHOOL：学校  主要控制head导航栏 %>
<c:if test="${sessionScope.session_login_user.orgLevel eq '1' or sessionScope.session_login_user.orgLevel eq '2' or sessionScope.session_login_user.orgLevel eq '3' or sessionScope.session_login_user.userType eq 'SCHOOL'}">
	<link media="all" type="text/css" rel="stylesheet" href="${ctx }/public/css/department.css"/>
</c:if>
<script>
var KD_RRT = { root: '${ctx }'};
var KD_APPREDIRECT = "${sessionScope.appredirect}";//登录页面地址
</script>

<script src="${ctx }/public/js/jquery.js" type="text/javascript"></script>
<script src="${ctx }/public/js/extend.js" type="text/javascript"></script>
<script src="${ctx }/public/js/litewin.js" type="text/javascript"></script>
<script src="${ctx }/public/js/basiccheck.js" type="text/javascript"></script>
<script src="${ctx }/public/js/common.js" type="text/javascript"></script>
<script src="${ctx}/public/js/public.js" type="text/javascript"></script>
<script src="${ctx}/public/js/webuploader.withoutimage.min.js" type="text/javascript"></script>
<script src="${ctx}/public/js/splitpage.js" type="text/javascript"></script>
