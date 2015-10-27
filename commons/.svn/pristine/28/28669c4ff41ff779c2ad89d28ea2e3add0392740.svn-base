package com.codyy.rrt.commons;

import javax.servlet.http.HttpServletResponse;

import com.codyy.base.web.SpringContext;
import com.codyy.rrt.commons.entity.DomainConfig;

/**
 * 公共的持久内容
 */
public class CommonsConstant {

	//用户类型
	public final static String	USER_TYPE_CLASSROOM			= "CLASSROOM";				// 班级
	public static final String	USER_TYPE_TEACHER			= "TEACHER";				//老师
	public static final String	USER_TYPE_STUDENT			= "STUDENT";				//学生
	public static final String	USER_TYPE_PARENT			= "PARENT";					//家长
	public static final String	USER_TYPE_SCHOOL			= "SCHOOL";					//学校超级管理员
	public static final String	USER_TYPE_ORG				= "ORG";					//机构用户(电教馆一般账号管理员)；省，市，县
	public static final String	USER_TYPE_ORG_MANAGER		= "ORG_MANAGER";			//机构超级管理员；电教馆超级管理员；henanadmin
	public static final String	USER_TYPE_TEACH_ORG_MANAGER	= "TEACH_ORG_MANAGER";		//教育局超级管理员；局和厅；只有查看权限；henanjyj
	public static final String	USER_TYPE_MANAGER			= "MANAGER";				//运营商用户(后台用户)

	public final static String	SESSION_LOGIN_USER			= "session_login_user";

	public final static String	SESSION_DOMAIN_CONFIG		= "session_domain_config";

	public final static String	COOKIE_TAG					= "rrt_sso_cookie_tag";

	public final static String	SYS_CONFIG					= "sys_config";

	public final static String	MD5_STR						= "teleconference_rrt";		// MD5加密附加值

	public final static String	KEYWORD						= "keyword";
	public final static String	KEYWORD_STATE				= "keyword_state";
	public final static String	IP_STATE					= "ip_state";
	public final static String	IP_TAG						= "ip_tag";
	public final static String	SECURITY_EXCEPT				= "security_except";		// url安全排查过滤
	public final static String	KEYWORD_EXCEPT				= "keyword_except";			// url安全排查过滤
	public final static String	USERID_COOKIETAG			= "userId_cookieTag";		// 用户id和cookieTag的映射关系
	public final static String	LOCKED_DEPARTMENTS			= "locked_department";		// 用户id和cookieTag的映射关系
	public final static String	APPREDIRECT					= "appredirect";			// 用户id和cookieTag的映射关系

	public static void toIndexPage(String userType, HttpServletResponse response) throws Exception {
		DomainConfig domainConfig = SpringContext.getBean(DomainConfig.class);
		if ("STUDENT".equals(userType) || "TEACHER".equals(userType) || "PARENT".equals(userType)) {
			response.sendRedirect(domainConfig.getDynamic());
			return;
		}
		if ("SCHOOL".equals(userType)) {
			response.sendRedirect(domainConfig.getPlatform() + "/front/workspace/schoolIndex.html");
			return;
		}
		if ("ORG".equals(userType) || "ORG_MANAGER".equals(userType) || "TEACH_ORG_MANAGER".equals(userType)) {
			response.sendRedirect(domainConfig.getPlatform() + "/front/workspace/departmentIndex.html");
			return;
		}
		response.sendRedirect(domainConfig.getSso() + "/index.html");
		return;
	}

}
