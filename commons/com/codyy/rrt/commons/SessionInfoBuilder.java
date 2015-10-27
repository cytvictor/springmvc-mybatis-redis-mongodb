package com.codyy.rrt.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.codyy.rrt.commons.entity.LoginUser;

/**
 * 用于将用户信息(LoginUser)转换为Redis中存储的字符串,以及反向解析
 * 
 * @author haocongping
 *
 */
public class SessionInfoBuilder {

	private static SessionInfoBuilder instance = new SessionInfoBuilder();

	private SessionInfoBuilder() {
	};

	public static SessionInfoBuilder getInstance() {
		return instance;
	}

	/**
	 * @param user
	 * @return
	 */
	public Map<String, String> buildSessionInfo(LoginUser user) {
		// String code = CodeUtils.encodeBase64(user.getUsername().getBytes());
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", transfer(user.getUsername()));
		map.put("userId", transfer(user.getUserId()));
		map.put("realName", transfer(user.getRealName()));
		map.put("accountId", transfer(user.getAccountId()));
		map.put("userType", transfer(user.getUserType()));
		map.put("headPic", transfer(user.getHeadPic()));
		map.put("rAccountId", transfer(user.getrAccountId()));
		map.put("refreshTag", UUID.randomUUID().toString());
		map.put("xianOrgIdDJG", transfer(user.getXianOrgIdDJG()));
		map.put("shiOrgIdDJG", transfer(user.getShiOrgIdDJG()));
		map.put("shengOrgIdDJG", transfer(user.getShengOrgIdDJG()));
		map.put("appDomain", transfer(user.getAppDomain()));
		map.put("changeInitPSD", transfer(user.getChangeInitPSD()));
		// 初始化电教馆、教育局信息
		String userType = user.getUserType();
		if ("ORG".equals(userType) || "ORG_MANAGER".equals(userType) || "TEACH_ORG_MANAGER".equals(userType)) {
			map.put("baseOrgId", transfer(user.getBaseOrgId()));
			map.put("baseOrgName", transfer(user.getBaseOrgName()));
			map.put("baseDistrictId", transfer(user.getBaseDistrictId()));
			map.put("baseDistrictName", transfer(user.getBaseDistrictName()));
			map.put("orgLevel", transfer(user.getOrgLevel()));
			map.put("orgType", transfer(user.getOrgType()));
			map.put("permission", transfer(user.getPermission()));
			map.put("jyjOrgId", transfer(user.getJyjOrgId()));
			return map;
		}
		if ("SCHOOL".equals(userType) || "TEACHER".equals(userType)) {// 初始化学校登录信息
			map.put("baseOrgId", transfer(user.getBaseOrgId()));
			map.put("baseOrgName", transfer(user.getBaseOrgName()));
			map.put("baseDistrictId", transfer(user.getBaseDistrictId()));
			map.put("baseDistrictName", transfer(user.getBaseDistrictName()));
			map.put("schoolId", transfer(user.getSchoolId()));
			map.put("schoolName", transfer(user.getSchoolName()));
			map.put("permission", transfer(user.getPermission()));
			map.put("schoolPic", transfer(user.getSchoolPic()));
			map.put("hasChildSchool", transfer(user.getHasChildSchool()));
			return map;
		}
		if ("PARENT".equals(userType)) {
			map.put("schoolId", transfer(user.getSchoolId()));
			map.put("schoolName", transfer(user.getSchoolName()));
			map.put("curChildUserId", transfer(user.getCurChildUserId()));
			map.put("childHeadPic", transfer(user.getChildHeadPic()));
			map.put("childName", transfer(user.getChildName()));
			map.put("childCount", transfer(user.getChildCount() + ""));
			map.put("className", transfer(user.getClassName()));
			return map;
		}
		if ("STUDENT".equals(userType)) {
			map.put("schoolId", transfer(user.getSchoolId()));
			map.put("schoolName", transfer(user.getSchoolName()));
			return map;
		}
		return map;
	}

	private String transfer(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 与 buildSessionInfo过程相反
	 * 
	 * @see buildSessionInfo(LoginUser user)
	 * @param code
	 * @return
	 */
	/**
	 * @param map
	 * @return
	 */
	public LoginUser parseLoginUser(Map<String, String> map) {
		// byte[] bytes = CodeUtils.decodeBase64(code);
		// String username = new String(bytes);s
		LoginUser user = new LoginUser();
		user.setUsername(map.get("username"));
		user.setUserId(map.get("userId"));
		user.setRealName(map.get("realName"));
		user.setAccountId(map.get("accountId"));
		user.setUserType(map.get("userType"));
		user.setHeadPic(map.get("headPic"));
		user.setrAccountId(map.get("rAccountId"));
		user.setRefreshTag(map.get("refreshTag"));
		user.setXianOrgIdDJG(map.get("xianOrgIdDJG"));
		user.setShiOrgIdDJG(map.get("shiOrgIdDJG"));
		user.setShengOrgIdDJG(map.get("shengOrgIdDJG"));
		// 初始化电教馆、教育局信息
		String userType = map.get("userType");
		if ("ORG".equals(userType) || "ORG_MANAGER".equals(userType) || "TEACH_ORG_MANAGER".equals(userType)) {
			user.setBaseOrgId(map.get("baseOrgId"));
			user.setBaseOrgName(map.get("baseOrgName"));
			user.setBaseDistrictId(map.get("baseDistrictId"));
			user.setBaseDistrictName(map.get("baseDistrictName"));
			user.setOrgLevel(map.get("orgLevel"));
			user.setOrgType(map.get("orgType"));
			user.setPermission(map.get("permission"));
			user.setJyjOrgId(map.get("jyjOrgId"));
		} else if ("SCHOOL".equals(userType) || "TEACHER".equals(userType)) {
			user.setBaseOrgId(map.get("baseOrgId"));
			user.setBaseOrgName(map.get("baseOrgName"));
			user.setBaseDistrictId(map.get("baseDistrictId"));
			user.setBaseDistrictName(map.get("baseDistrictName"));
			user.setSchoolId(map.get("schoolId"));
			user.setSchoolName(map.get("schoolName"));
			user.setPermission(map.get("permission"));
			user.setSchoolPic(map.get("schoolPic"));
			user.setHasChildSchool(map.get("hasChildSchool"));
		} else if ("PARENT".equals(userType)) {
			user.setSchoolId(map.get("schoolId"));
			user.setSchoolName(map.get("schoolName"));
			user.setCurChildUserId(map.get("curChildUserId"));
			user.setChildHeadPic(map.get("childHeadPic"));
			user.setChildName(map.get("childName"));
			String childCount = map.get("childCount");
			if (StringUtils.isNotBlank(childCount)) {
				user.setChildCount(Integer.parseInt(childCount));
			}
			user.setClassName(map.get("className"));
		} else if ("STUDENT".equals(userType)) {
			user.setSchoolId(map.get("schoolId"));
			user.setSchoolName(map.get("schoolName"));
		}
		user.setAppDomain(map.get("appDomain"));
		user.setChangeInitPSD(map.get("changeInitPSD"));
		return user;
	}

	public String getLoginUserRefreshTag(Map<String, String> map) {
		return map.get("refreshTag");
	}

}
