package com.codyy.rrt.resource.common;


public class Constants {

	public static final String FUN_TYPE_VIDEO = "FUN_TYPE_VIDEO"; // 轻松一刻视频
	public static final String FUN_TYPE_PICTURE = "FUN_TYPE_PICTURE"; // 轻松一刻图片
	public static final String FUN_TYPE_TEXT = "FUN_TYPE_TEXT"; // 轻松一刻文字

	public static final String TRANS_PENDDING = "TRANS_PENDDING";// 未转换
	public static final String TRANS_TRANSING = "TRANS_TRANSING";// 转换中
	public static final String TRANS_SUCCESS = "TRANS_SUCCESS";// 转换成功
	public static final String TRANS_FAILED = "TRANS_FAILED";// 转换失败

	public static final String REVIEW_NOT_REVIEW = "REVIEW_NOT_REVIEW"; // 审核:未审核
	public static final String REVIEW_ACCEPT = "REVIEW_ACCEPT"; // 审核:通过
	public static final String REVIEW_REJECT = "REVIEW_REJECT"; // 审核:拒绝

	public static final String DELETE_NOT_DELETE = "DELETE_NOT_DELETE"; // 删除状态:未删除
	public static final String DELETE_BY_USER = "DELETE_BY_USER"; // 删除状态:用户删除
	public static final String DELETE_BY_MANAGER = "DELETE_BY_MANAGER"; // 删除状态:管理员删除

	// 资源持有，上推，下发类型
	public static final String HOLD_TYPE_STUDENT = "STUDENT";// 学生
	public static final String HOLD_TYPE_TEACHER = "TEACHER";// 老师
	public static final String HOLD_TYPE_SCHOOL = "SCHOOL";// 学校
	public static final String HOLD_TYPE_ORG = "ORG";// 机构

	// 资源的来源机构
	public static final String ORG_SOURCE_SCHOOL = "SCHOOL"; // 学校来源，包括学生、老师和学校
	public static final String ORG_SOURCE_ORG_LEVEL_ONE = "ORG_LEVEL_ONE"; // 一级机构，省级电教馆
	public static final String ORG_SOURCE_ORG_LEVEL_TWO = "ORG_LEVEL_TWO"; // 二级机构，市电教馆
	public static final String ORG_SOURCE_ORG_LEVEL_THREE = "ORG_LEVEL_THREE"; // 三级机构，县电教馆

	// 机构级别
	public static final String ORG_LEVEL_ONE = "1";
	public static final String ORG_LEVEL_TWO = "2";
	public static final String ORG_LEVEL_THREE = "3";

	public static final String TYPE_FAVORITE = "FAVORITE"; // 收藏夹
	public static final String TYPE_HISTORY = "HISTORY"; // 历史

	// 基础用户状态：开启
	public final static String USER_STATE_OPEN = "OPEN";
	// 基础用户状态：关闭
	public final static String USER_STATE_CLOSE = "CLOSE";
	// 后台登录
	public final static String USER_LOGIN_TYPE_ADMIN = "ADMIN";
	// 前台登录
	public final static String USER_LOGIN_TYPE_FRONT = "FRONT";
	// 系统初始密码
	public final static String DEFAULT_PASSWORD = "666666";
	// 是否改过初始密码
	public final static String CHANGE_INIT_PASSWORD_YES = "YES";
	public final static String CHANGE_INIT_PASSWORD_NO = "NO";
	// == 性别
	public static final String SEX_MAIL = "男";
	public static final String SEX_FEMAIL = "女";

	// 资源来源：平台
	public static final String RESOURCE_SOURCE_PLATFORM = "PLATFORM";
	// 资源来源：空间
	public static final String RESOURCE_SOURCE_SPACE = "SPACE";

	// 空间资源：转载
	public static final String RESOURCE_TRANSFER_FLAG_YES = "Y";
	// 空间资源：非转载
	public static final String RESOURCE_TRANSFER_FLAG_NO = "N";

	public static final String ROOT_NODE_ID = "-1";
	// === 省级电教馆
	public static final String LEVEL_PROVINCE = "1";
	// === 市级电教馆
	public static final String LEVEL_CITY = "2";
	// === 区县级电教馆
	public static final String LEVEL_DISTRICT = "3";
	// === 学校
	public static final String LEVEL_SCHOOL = "4";
	// === 教育局/电教馆状态 锁定
	public static final String ORG_LOCKED = "CLOSE";
	// === 教育局/电教馆状态 未锁定
	public static final String ORG_UNLOCKED = "OPEN";
	// === 下发资源免审核
	public static final String ORG_UNVERIFY = "0";
	// === 下发资源需要审核
	public static final String ORG_VERIFY = "1";

	// 资源标签分隔符
	public static final String RESOURCE_LABEL_SEPARATER = ",";
	public static final String RESOURCE_KNOWLEDGE_SEPARATER = "-->";

	// 资源图片分隔符
	public static final String RESOURCE_IMAGE_SEPARATER = ";";

	// 视频类
	public static final String RESOURCE_COLUMN_VIDEO = "VIDEO";
	// 文档类
	public static final String RESOURCE_COLUMN_DOCUMENT = "DOCUMENT";
	// 混合类
	public static final String RESOURCE_COLUMN_MIXTURE = "MIXTURE";

	// 资源栏目是否关联
	public static final String RESOURCE_COLUMN_RELATION_YES = "Y";
	public static final String RESOURCE_COLUMN_RELATION_NO = "N";

	// config配置文件路径
	public static final String PATH_CONFIG_PROPERTIES = "uploadsize.properties";
	// 配置文件中资源附件上传最大限制key
	public static final String MAX_UPLOAD_SIZE_VIDEO = "max.upload.size.video";
	public static final String MAX_UPLOAD_SIZE_DOCUMENT = "max.upload.size.document";
	public static final String MAX_UPLOAD_SIZE_PICTURE = "max.upload.size.picture";
	public static final String MAX_UPLOAD_SIZE_THUMB = "max.upload.size.thumb";

	public static final String CHAR_ESCAPE = "\\"; // 数据库查询特殊字符转义字符
	public static final String CHAR_PERCENT = "%"; // 百分号
}
