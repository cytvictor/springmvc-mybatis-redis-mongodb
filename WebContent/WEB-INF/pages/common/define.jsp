<%
	String contextPath = request.getContextPath();
	request.getSession().setAttribute("ctx",contextPath);
	
	String SPATH = request.getServletPath();
	
	//\u7528\u4e8e\u5224\u65ad\u9009\u62e9\u7684\u662f\u54ea\u4e2a\u680f\u76ee\uff0c\u6837\u5f0f\u4e0a\u6709\u6548\u679c
	String index_type = null;
	if(SPATH.indexOf("/index/index/") >= 0) {
    	index_type = "index";
    }else if(SPATH.indexOf("/index/onlineCourse/") >= 0) {
    	index_type = "onlineCourse";
    }else if(SPATH.indexOf("/index/syncCourse/") >= 0) {
    	index_type = "syncCourse";
    }
    request.setAttribute("index_type", index_type);
%>