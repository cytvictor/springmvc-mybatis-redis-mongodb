package com.codyy.rrt.base;

/**
 * 常量类
 */
public class Constant {

	public static final String WEBROOT = "/rrt/";

	public static final String KNOWLEGE_PARENT_ROOT = "ROOT";

	public static final String CATALOG_PARENT_ROOT = "ROOT";

	public static final String TYPE_RESOURCE_FUN = "TYPE_RESOURCE_FUN"; // 轻松一刻

	// public static final String TYPE_VIDEO_TOP =
	// BaseTeachCatalog.TYPE_RESOURCE_TOP;;// 精品课程
	//
	// public static final String TYPE_VIDEO_MICRO = "TYPE_VIDEO_MICRO";// 微课程
	//
	// public static final String TYPE_STUDY_RESOURCE = "TYPE_STUDY_RESOURCE";//
	// 教学资源
	//
	// public static final String TYPE_TEST_PAPER = "TYPE_TEST_PAPER";// 试卷

	public static final String SESSION_LOGIN_USER = "SESSION_LOGIN_USER"; // session记录用户信息的KEY

	public static final String SESSION_LOGIN_USER_ID = "SESSION_LOGIN_USER_ID"; // session记录用户信息的KEY

	public static final String SESSION_LOGIN_USER_TYPE = "SESSION_LOGIN_USER_TYPE";

	public static final String SESSION_LOGIN_USER_PHP = "SESSION_LOGIN_USER_PHP";

	// 习题类型
	public static int QUESTION_TYPE_SINGLE = 1; // 单选
	public static int QUESTION_TYPE_MULTI = 2; // 多选
	public static int QUESTION_TYPE_SUBJ = 3; // 问答

	// 获取ID序列名称
	public static String ACCOUNT_ID_SEQUENCE_NAME = "USER_ACCOUNT_ID_SEQUENCE";
	// 用户State
	public static String USER_STATE_OPEN = "OPEN";
	public static String USER_STATE_CLOSE = "CLOSE";

	// 用户类型User_type
	public static String USER_TYPE_TEACHER = "TEACHER";// 老师
	public static String USER_TYPE_STUDENT = "STUDENT";// 学生
	public static String USER_TYPE_PARENT = "PARENT";// 家长

	// 习题难度等级
	/**
	 * 容易
	 */
	public static int QUESTION_EASY = 1; // 容易

	/**
	 * 一般
	 */
	public static int QUESTION_NORMAL = 2; // 一般

	/**
	 * 困难
	 */
	public static int QUESTION_HARD = 3; // 困难

	/**
	 * 一天毫秒数
	 */
	public static long ONE_DAY = 86399999;
	// ==================================课程课时状态===开始========================================================
	/**
	 * 未开始
	 */
	public static final String COURSE_LESSION_STATUS_NOTSTART = "1";
	/**
	 * 进行中
	 */
	public static final String COURSE_LESSION_STATUS_UNDERWAY = "2";
	/**
	 * 已结束
	 */
	public static final String COURSE_LESSION_STATUS_OVER = "3";

	// ==================================课程课时状态===结束========================================================

	// ==================================附件类型===开始============================================================
	/**
	 * 附件类型-网络作业
	 */
	public static final String FILE_ATTACHETYPE_HOMEWORK = "HOMEWORK";
	/**
	 * 附件类型-网络作业答案
	 */
	public static final String FILE_ATTACHETYPE_HOMEWORKRECEIVER = "HOMEWORKRECEIVER";
	/**
	 * 附件类型-资源轻松一刻图片
	 */
	public static final String FILE_ATTACHETYPE_RESOURNCE_FUN_PICTURE = "RESOURCE_FUN_PICTURE";

	// ==================================附件类型===结束============================================================

	// ==================================老师身份===开始============================================================
	public static final String TEACHER_TYPE_HEAD = "1";// 班主任
	public static final String TEACHER_TYPE_RENKE = "2";// 任课老师
	public static final String TEACHER_TYPE_HEAD_RENKE = "3";// 既是班主任也是任课老师
	public static final String TEACHER_TYPE_OTHER = "4";// 其他
	// ==================================老师身份===结束============================================================

	// ================================== 数据统计的页面Table切换常量 开始
	// =========================================
	public static final String PAGE_TABLE_ZERO = "0"; // === 0页签
	public static final String PAGE_TABLE_FIRST = "1"; // === 1页签
	public static final String PAGE_TABLE_SECOND = "2"; // === 2页签
	public static final String PAGE_TABLE_THIRD = "3"; // === 3页签
	public static final String PAGE_TABLE_FORTH = "4"; // === 4页签
	// ================================== 数据统计的页面Table切换常量 结束
	// =========================================

	// ================================== 数据统计的页面统计区间切换常量 开始
	// ========================================
	public static final String COUNT_BY_DAYS = "days"; // === 按天统计
	public static final String COUNT_BY_WEEKS = "weeks"; // === 按周统计
	public static final String COUNT_BY_MONTHS = "months"; // === 按月统计
	public static final String COUNT_BY_QUARTERS = "quarters"; // === 按季度统计
	public static final String COUNT_BY_TERM_YEAR = "term_year"; // === 按学年统计
	public static final String COUNT_BY_YEAR = "year"; // === 按年统计
	public static final String QUARTERS_FIRST_DATE = "一"; // === 第一季度
	public static final String QUARTERS_SECOND_DATE = "二"; // === 第二季度
	public static final String QUARTERS_THIRD_DATE = "三"; // === 第三季度
	public static final String QUARTERS_FIRTH_DATE = "四"; // === 第四季度
	// ================================== 数据统计的页面统计区间切换常量 结束
	// ========================================

	// =========================================== 常用分割符号 开始
	// ==============================================
	public static final String SPLIT_CENTER_LINE = "-"; // === 横线分割
	public static final String SPLIT_BOTTOM_LINE = "_"; // === 下划线分割
	public static final String SPLIT_SMALL_SYMBOL = "&"; // === &小分割
	public static final String SPLIT_BIGGER_SYMBOL = "&&"; // === &&大分割
	public static final String SPLIT_INCLINED = "/"; // === 斜线分割
	public static final String SPLIT_PAUSE = "、"; // === 顿号分割（中文）
	public static final String SPLIT_COMMA = ","; // === 逗号分割（英文）
	// =========================================== 常用分割符号 结束
	// ==============================================
	
	//======================================学习资源类型及文档类型
	public static final String WORD = "WORD"; // === WORD
	public static final String EXCEL = "EXCEL"; // === EXCEL
	public static final String PPT = "PPT"; // === PPT
	public static final String PPTX = "PPTX"; // === PPT
	public static final String PDF = "PDF"; // === PDF
	public static final String XLSX = "xlsx"; //=== XLSX
	public static final String DOC = "doc"; // === doc
	public static final String DOCX = "docx"; // === docx
	
	//资源操作
	public static final String RES_ACTION_DOWNLOAD = "下载";
	public static final String RES_ACTION_UPLOAD = "上传";
	public static final String RES_ACTION_RECOMMEND = "推荐";
}