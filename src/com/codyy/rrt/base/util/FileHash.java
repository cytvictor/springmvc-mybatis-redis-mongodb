package com.codyy.rrt.base.util;

import com.codyy.rrt.base.GlobleValue;

public class FileHash {
	/**
	 * 获取用户头像
	 * 
	 * @param id
	 * @return
	 */
	public static String getUserHeadImage(String id) {
		String idmd5 = ObjectUtil.md5(id).substring(0, 3);
		StringBuilder sb = new StringBuilder(GlobleValue.ROOT);
		sb.append("/uploaddir/userHeadImage").append('/')
				.append(idmd5.substring(0, 1)).append('/')
				.append(idmd5.substring(1, 2)).append('/')
				.append(idmd5.substring(2, 3)).append('/').append(id)
				.append(".jpg");
		return sb.toString();
	}

	/**
	 * 获取用户头像文件夹位置
	 * 
	 * @param id
	 * @return
	 */
	public static String getUserHeadPath(String id) {
		String idmd5 = ObjectUtil.md5(id).substring(0, 3);
		StringBuilder sb = new StringBuilder(GlobleValue.ROOT);
		sb.append("/uploaddir/userHeadImage").append('/')
				.append(idmd5.substring(0, 1)).append('/')
				.append(idmd5.substring(1, 2)).append('/')
				.append(idmd5.substring(2, 3));
		return sb.toString();
	}

	/**
	 * 社会化资源的图片
	 * 
	 * @param id
	 * @return
	 */
	public static String getSocietyImage(String id) {
		String idmd5 = ObjectUtil.md5(id).substring(0, 3);
		String dir = GlobleValue.ROOT + "/uploaddir/societyImage";
		String dir2 = dir + "/" + idmd5.substring(0, 1) + "/"
				+ idmd5.substring(1, 2) + "/" + idmd5.substring(2, 3) + "/"
				+ id + ".jpg";
		return dir2;
	}

	/**
	 * 课程管理，在线课程图片
	 * 
	 * @param id
	 * @return
	 */
	public static String getCourseImage(String id) {
		String idmd5 = ObjectUtil.md5(id).substring(0, 3);
		String dir = GlobleValue.ROOT + "/uploaddir/courseImage";
		String dir2 = dir + "/" + idmd5.substring(0, 1) + "/"
				+ idmd5.substring(1, 2) + "/" + idmd5.substring(2, 3) + "/"
				+ id + ".jpg";
		return dir2;
	}

	/**
	 * 动态图片
	 * 
	 * @param id
	 * @return
	 */
	public static String getDynamicImage(String id) {
		String idmd5 = ObjectUtil.md5(id).substring(0, 3);
		String dir = GlobleValue.ROOT + "/uploaddir/dynamicImage";
		String dir2 = dir + "/" + idmd5.substring(0, 1) + "/"
				+ idmd5.substring(1, 2) + "/" + idmd5.substring(2, 3) + "/"
				+ id + ".jpg";
		return dir2;
	}

	/**
	 * 广告图片
	 * 
	 * @param id
	 * @return
	 */
	public static String getAdvertiseImage(String id) {
		String idmd5 = ObjectUtil.md5(id).substring(0, 3);
		String dir = GlobleValue.ROOT + "/uploaddir/advertiseImage";
		String dir2 = dir + "/" + idmd5.substring(0, 1) + "/"
				+ idmd5.substring(1, 2) + "/" + idmd5.substring(2, 3) + "/"
				+ id + ".jpg";
		return dir2;
	}

	/**
	 * 广告视频
	 * 
	 * @param id
	 * @return
	 */
	public static String getAdvertiseVideo(String id) {
		String dir = GlobleValue.ROOT + "/uploaddir/advertiseVideo" + "/" + id
				+ ".swf";
		return dir;
	}

	/**
	 * 前台，老师角色，创建试题，上传图片
	 * 
	 * @param id
	 * @return
	 */
	public static String getQuestionImage(String id) {
		String idmd5 = ObjectUtil.md5(id).substring(0, 3);
		String dir = GlobleValue.ROOT + "/uploaddir/questionImage";
		String dir2 = dir + "/" + idmd5.substring(0, 1) + "/"
				+ idmd5.substring(1, 2) + "/" + idmd5.substring(2, 3) + "/"
				+ id + ".jpg";
		return dir2;
	}

	/**
	 * 私信图片HSAP目录
	 * 
	 * @param id
	 * @return
	 */
	public static String getPrivateLetterImage(String id) {
		String idmd5 = ObjectUtil.md5(id).substring(0, 3);
		String dir = GlobleValue.ROOT + "/uploaddir/privateLetterImage";
		String dir2 = dir + '/' + idmd5.substring(0, 1) + '/'
				+ idmd5.substring(1, 2) + '/' + idmd5.substring(2, 3) + '/'
				+ id + ".jpg";
		return dir2;
	}
}