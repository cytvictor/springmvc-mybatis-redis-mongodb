package com.codyy.rrt.resource.model;

import java.util.Date;

import com.codyy.rrt.base.model.BaseModel;

public class BaseUser extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1001512759869573123L;
	private String username; // 用户名
	private String sex;// 性别 0:男 1:女
	private String accountId;// 工号、学号、家长ID
	private String birthDate;// 生日
	private String password; // 密码
	private String realName; // 真实姓名
	private String email;// 邮箱
	private String phoneNum; // 电话号码
	private String headPic = "1099e566-bb12-408c-8a6d-71589b0656a6.jpg"; // 用户头像
	private String userType; // 用户类型
	private String phpUserId;
	private String changeInitPsd = "NO";// 是否改过初始密码
	private String rAccountId;// 关联的账户
	private BaseUser createUser; // 创建人
	private Date createTime; // 创建时间
	private String state; // 用户状态
	private String roleNames; // 角色名称S
	private String resourceNum;// 上传资源数
	private boolean attentionByLonginUserId; // 是否被当前登录用户关注
	private String baseSchoolId; // 所属学校ID
	private String baseOrgId; // 所属机构ID

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPhpUserId() {
		return phpUserId;
	}

	public void setPhpUserId(String phpUserId) {
		this.phpUserId = phpUserId;
	}

	public String getChangeInitPsd() {
		return changeInitPsd;
	}

	public void setChangeInitPsd(String changeInitPsd) {
		this.changeInitPsd = changeInitPsd;
	}

	public String getrAccountId() {
		return rAccountId;
	}

	public void setrAccountId(String rAccountId) {
		this.rAccountId = rAccountId;
	}

	public BaseUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(BaseUser createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getResourceNum() {
		return resourceNum;
	}

	public void setResourceNum(String resourceNum) {
		this.resourceNum = resourceNum;
	}

	public boolean isAttentionByLonginUserId() {
		return attentionByLonginUserId;
	}

	public void setAttentionByLonginUserId(boolean attentionByLonginUserId) {
		this.attentionByLonginUserId = attentionByLonginUserId;
	}

	public String getBaseSchoolId() {
		return baseSchoolId;
	}

	public void setBaseSchoolId(String baseSchoolId) {
		this.baseSchoolId = baseSchoolId;
	}

	public String getBaseOrgId() {
		return baseOrgId;
	}

	public void setBaseOrgId(String baseOrgId) {
		this.baseOrgId = baseOrgId;
	}

}