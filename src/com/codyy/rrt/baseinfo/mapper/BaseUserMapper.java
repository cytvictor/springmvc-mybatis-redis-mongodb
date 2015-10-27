package com.codyy.rrt.baseinfo.mapper;

import java.util.List;
import java.util.Map;

import com.codyy.rrt.resource.model.BaseClassLevel;
import com.codyy.rrt.resource.model.BaseOrg;
import com.codyy.rrt.resource.model.BaseSchool;
import com.codyy.rrt.resource.model.BaseUser;

public interface BaseUserMapper {

	/**
	 * 根据baseUserId查询BaseUser
	 * 
	 * @param baseUserId
	 * @return
	 */
	public BaseUser findByBaseUserId(String baseUserId);

	/**
	 * 根据teacherUserId查询BaseSchoolId
	 * 
	 * @param baseTeacherId
	 * @return
	 */
	public String findSchoolIdByTeacherUserId(String teacherUserId);

	/**
	 * 根据老师的userID查询角色为班主任的班级的学生baseUserId
	 * 
	 * @param teacherUserId
	 * @return
	 */
	public List<String> findStudentUserIdByHeadTeacherUserId(
			String teacherUserId);

	/**
	 * 根据老师的userID查询角色为任课老师的班级的学生baseUserId
	 * 
	 * @param teacherUserId
	 * @return
	 */
	public List<String> findStudentUserIdByTeacherUserId(String teacherUserId);

	/**
	 * 根据学生的userID查询班主任的baseUserId
	 * 
	 * @param studentUserId
	 * @return
	 */
	public List<String> findHeadTeacherUserIdByStudentUserId(
			String studentUserId);

	/**
	 * 根据学生的userID查询学科的任课老师的baseUserId
	 * 
	 * @param parMap
	 * @return
	 */
	public List<String> findTeacherUserIdByStudentUserId(
			Map<String, Object> parMap);

	/**
	 * 根据学校用户baseUserId查找学校Id
	 * 
	 * @param baseUserId
	 * @return
	 */
	public List<String> findSchoolIdBySchoolUserId(String baseUserId);

	/**
	 * 根据机构用户baseUserId查找机构Id
	 * 
	 * @param baseUserId
	 * @return
	 */
	public List<String> findOrgIdByOrgUserId(String baseUserId);

	/**
	 * 根据机构用户baseUserId查找机构列表
	 * 
	 * @param baseUserId
	 * @return
	 */
	public List<BaseOrg> findOrgByOrgUserId(String baseUserId);

	/**
	 * 根据学校id查询所属机构id
	 * 
	 * @param schoolId
	 * @return
	 */
	public String findOrgIdBySchoolId(String schoolId);

	/**
	 * 根据机构Id查询父级机构Id
	 * 
	 * @param orgId
	 * @return
	 */
	public List<String> findParentOrgIdByOrgId(String orgId);

	/**
	 * 根据学校Id查询年级集合
	 * 
	 * @param schoolId
	 * @return
	 */
	public List<BaseClassLevel> findClassLevelBySchoolId(String schoolId);

	/**
	 * 根据学校Id和年级Id查询班主任的UserId
	 * 
	 * @param parMap
	 * @return
	 */
	public List<String> findHeadTeacherUserIdBySIdAndCLId(
			Map<String, Object> parMap);

	/**
	 * 根据学校Id、年级Id和学科Id查询学科的任课老师的UserId
	 * 
	 * @param parMap
	 * @return
	 */
	public List<String> findTeacherUserIdBySIdAndClIdAndDId(
			Map<String, Object> parMap);

	/**
	 * 根据机构Id查询下级机构集合
	 * 
	 * @param baseOrgId
	 * @return
	 */
	public List<BaseOrg> findOrgByParentOrgId(String baseOrgId);

	/**
	 * 根据机构Id查询下级学校集合
	 * 
	 * @param baseOrgId
	 * @return
	 */
	public List<BaseSchool> findSchoolByOrgId(String baseOrgId);

	/**
	 * 根据机构Id查询机构
	 * 
	 * @param baseOrgId
	 * @return
	 */
	public BaseOrg findOrgByOrgId(String baseOrgId);
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	public Map<String,String> getUserInfoByUserId(String userId);
	
	/**
	 * 根据学校id获取学校管理员信息
	 * @param schoolId
	 * @return
	 */
	public Map<String,String> getUserInfoBySchoolId(String schoolId);
}
