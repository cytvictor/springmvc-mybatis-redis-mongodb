package com.codyy.rrt.resource.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codyy.rrt.baseinfo.mapper.BaseUserMapper;
import com.codyy.rrt.resource.model.BaseClassLevel;
import com.codyy.rrt.resource.model.BaseOrg;
import com.codyy.rrt.resource.model.BaseSchool;
import com.codyy.rrt.resource.model.BaseUser;

@Service("baseUserService")
public class BaseUserService {

	@Autowired
	private BaseUserMapper	baseUserMapper;

	/**
	 * 根据用户id查询用户
	 * 
	 * @param baseUserId
	 * @return
	 */
	public BaseUser findByBaseUserId(String baseUserId) {
		BaseUser baseUser = baseUserMapper.findByBaseUserId(baseUserId);
		return baseUser;
	}

	/**
	 * 根据学校用户Id查询学校Id
	 * 
	 * @param baseUserId
	 * @return
	 */
	public String findSchoolIdBySchoolUserId(String baseUserId) {
		List<String> listSchoolId = baseUserMapper.findSchoolIdBySchoolUserId(baseUserId);
		if (CollectionUtils.isNotEmpty(listSchoolId)) {
			return listSchoolId.get(0);
		}
		return "";
	}

	/**
	 * 根据机构用户查询机构id
	 * 
	 * @param baseUserId
	 * @return
	 */
	public String findOrgIdByOrgUserId(String baseUserId) {
		List<String> listOrgId = baseUserMapper.findOrgIdByOrgUserId(baseUserId);
		if (CollectionUtils.isNotEmpty(listOrgId)) {
			return listOrgId.get(0);
		}
		return "";
	}

	/**
	 * 根据学校Id查询年级集合
	 * 
	 * @param schoolId
	 * @return
	 */
	public List<BaseClassLevel> findClassLevelBySchoolId(String schoolId) {
		return baseUserMapper.findClassLevelBySchoolId(schoolId);
	}

	/**
	 * 根据机构Id查询下级机构集合
	 * 
	 * @param baseOrgId
	 * @return
	 */
	public List<BaseOrg> findOrgByParentBaseOrgId(String baseOrgId) {
		return baseUserMapper.findOrgByParentOrgId(baseOrgId);
	}

	/**
	 * 根据机构Id查询下级学校集合
	 * 
	 * @param baseOrgId
	 * @return
	 */
	public List<BaseSchool> findSchoolByBaseOrgId(String baseOrgId) {
		return baseUserMapper.findSchoolByOrgId(baseOrgId);
	}

	/**
	 * 根据访客id和类型获取用户信息
	 * 
	 * @param userId
	 * @param userType
	 * @return
	 */
	public Map<String, String> getUserInfo(String userId, String userType) {
		if ("SCHOOL".equals(userType)) {
			return baseUserMapper.getUserInfoBySchoolId(userId);
		} else {
			return baseUserMapper.getUserInfoByUserId(userId);
		}
	}
}
