package com.codyy.rrt.base;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.codyy.rrt.base.util.FileUtils;
import com.codyy.rrt.base.util.ObjectUtil;

/**
 * 获取项目路径
 */
public class GlobleValue {
	public static final String pathSeparator = File.separator;
	/**
	 * 项目root
	 */
	public static final String CDROOT;

	/**
	 * 文件存放root
	 */
	public static final String ROOT;

	/**
	 * 上传跟目录
	 */
	public static final String UPLOADDIR;

	/**
	 * 精品课程路径
	 */
	public static final String BOUTIQUE;
	/**
	 * 微课程
	 */
	public static final String TINY;
	/**
	 * 轻松一刻
	 */
	public static final String ART;
	/**
	 * 课程封面
	 */
	public static final String COURSE;
	/**
	 * 教学资源
	 */
	public static final String ORDINARY;
	/**
	 * 习题图片
	 */
	public static final String EXERCISES;
	/**
	 * 用户excel数据导入路径
	 */
	public static final String EXCEL_USER;

	/**
	 * 到\tomcat7\bin目录
	 */
	public static final String ROOTBIN;
	/**
	 * 上传临时文件夹
	 */
	public static final String TEMP;
	/**
	 * 允许上传图片类型列表 字母小写
	 */
	public static final List<String> IMG_TYPE_LIST;
	/**
	 * 允许上传广告视频类型
	 */
	public static final List<String> AD_VIDEO_TYPE_LIST;

	static {
		GlobleValue gv = new GlobleValue();
		CDROOT = gv.getCDRoot();
		ROOT = "" + gv.getRoot();
		ROOTBIN = System.getProperty("user.dir");
		UPLOADDIR = ROOT + "/uploaddir/";
		BOUTIQUE = ROOT + "/uploaddir/boutique/";
		TINY = ROOT + "/uploaddir/tiny/";
		ART = ROOT + "/uploaddir/art/";
		COURSE = ROOT + "/uploaddir/course/";
		ORDINARY = ROOT + "/uploaddir/ordinary/";
		EXERCISES = ROOT + "/uploaddir/exercises/";
		EXCEL_USER = ROOT + "/uploaddir/excel_user/";
		TEMP = ROOT + "/temp/";

		IMG_TYPE_LIST = new ArrayList<String>();
		IMG_TYPE_LIST.add("bmp");
		IMG_TYPE_LIST.add("jpg");
		IMG_TYPE_LIST.add("jpeg");
		IMG_TYPE_LIST.add("png");
		IMG_TYPE_LIST.add("gif");

		AD_VIDEO_TYPE_LIST = new ArrayList<String>();
		AD_VIDEO_TYPE_LIST.add("swf");
	}

	/**
	 * 到项目根路径 /rrt_henan/WebContent/WEB-INF/classes
	 * 
	 * @return
	 */
	private String getCDRoot() {
		URL url = getClass().getResource("GlobleValue.class");
		String stxxHome = FileUtils.toFile(url).toString();
		int mark = stxxHome.lastIndexOf("WEB-INF");
		stxxHome = stxxHome.substring(0, mark).replaceAll("\\\\", "/");
		return stxxHome;
	}

	/**
	 * 到文件存放路径
	 * 
	 * @return
	 */
	private String getRoot() {
		return ObjectUtil.getAppConfig("upload.folder") + "/";
	}

	/**
	 * 获取文件路径
	 * 
	 * @return
	 */
	public static String filePath(String filepath) {
		String str = "";
		if ("boutique".equals(filepath)) {
			str = GlobleValue.BOUTIQUE;
		} else if ("tiny".equals(filepath)) {
			str = GlobleValue.TINY;
		} else if ("art".equals(filepath)) {
			str = GlobleValue.ART;
		} else if ("course".equals(filepath)) {
			str = GlobleValue.COURSE;
		} else if ("ordinary".equals(filepath)) {
			str = GlobleValue.ORDINARY;
		} else if ("exercises".equals(filepath)) {
			str = GlobleValue.EXERCISES;
		} else if ("excel_user".equals(filepath)) {
			str = GlobleValue.EXCEL_USER;
		} else if ("temp".equals(filepath)) {
			str = GlobleValue.TEMP;
		} else {
			str = GlobleValue.UPLOADDIR;
		}
		return str;
	}

	public static void main(String args[]) {
	}
}
