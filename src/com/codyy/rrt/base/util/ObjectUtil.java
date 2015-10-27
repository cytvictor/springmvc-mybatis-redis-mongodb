package com.codyy.rrt.base.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

import com.codyy.rrt.base.GlobleValue;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ObjectUtil {

	/**
	 * 判断数组对象是不是为空
	 * 
	 * @param o
	 *            数组对象
	 * @return true:为空，false:不为空。
	 */
	public static boolean isBlank(Object[][] o) {
		return ((null == o) || (o.length < 1));
	}

	/**
	 * 判断对象是不是为空
	 * 
	 * @param o
	 *            数组对象
	 * @return true:为空，false:不为空。
	 */
	public static boolean isBlank(Object o) {
		return (o == null) || (o.toString().trim().length() < 1);
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param <T>
	 * @param list
	 *            集合对象
	 * @return true:为空，false:不为空
	 */
	public static <T> boolean isBlank(Collection<T> collection) {

		return (collection == null) || (collection.size() <= 0);
	}

	/**
	 * 判断并清除Null
	 * 
	 * @param o
	 *            对象
	 * @return String 字符串
	 */
	public static String clearNull(Object o) {
		String s = "";
		if (!isBlank(o)) {
			s = o.toString();
		}
		return s;
	}

	/**
	 * 
	 * @description 判断并将Null转为"未知"
	 * @param o
	 *            对象
	 * @return String "未知"
	 * @author wp
	 */
	public static String changeNull(Object o) {
		String s = "未知";
		if (!isBlank(o)) {
			s = o.toString();
		}
		return s;
	}

	public static String fillNull(Object o) {
		String s = "null";
		if (!isBlank(o)) {
			s = o.toString();
		}
		return s;
	}

	// 将对象转化为int类型 wkp
	public static int parseInt(Object o) {
		if (!isBlank(o)) {
			return Integer.parseInt(o.toString());
		} else {
			return 0;
		}
	}

	/**
	 * 数组合并
	 * 
	 * @param <T>
	 * @param objs
	 * @param delimiter
	 * @return
	 */
	public static <T> String join(final Collection<T> objs,
			final String delimiter) {
		if (objs == null || objs.isEmpty())
			return "";
		Iterator<T> iter = objs.iterator();
		StringBuffer buffer = new StringBuffer(iter.next().toString());
		while (iter.hasNext())
			buffer.append(delimiter).append(iter.next().toString());
		return buffer.toString();
	}

	/**
	 * 数组合并
	 * 
	 * @param <T>
	 * @param objs
	 * @param delimiter
	 * @return
	 */
	public static <T> String join(final Object[] objs, final String delimiter) {
		if (ObjectUtil.isBlank(objs))
			return "";
		StringBuffer buffer = new StringBuffer(objs[0].toString());
		for (int i = 1; i < objs.length; i++) {
			buffer.append(delimiter).append(objs[i].toString());
		}
		return buffer.toString();
	}

	/**
	 * 将传入的JAVA对象转换成JSON字符串
	 * 
	 * @param Object
	 *            oo
	 * @return String
	 */
	public static String obj2Json(Object oo) {
		Gson gson = new Gson();
		String ss = gson.toJson(oo);
		return ss;
	}

	/**
	 * 将JSON字符串转为Class<T>类型对象
	 * 
	 * @param String
	 *            JSON格式的字符串
	 * @param Class
	 *            <T> 类的类型
	 * @return <T>
	 */
	public static <T> T json2Obj(String jsonStr, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, clazz);
	}

	/**
	 * 将base64编码的字符串进行解码
	 * 
	 * @param String
	 *            要被解码的字符串
	 * @return String
	 * @see 修改JDK编码为Apache编码 ydq 2014年3月13日10:20:18 Apache base64编码比jdk编码安全
	 */
	public static String base64Decode(String s) {
		if (s == null)
			return null;
		/*
		 * try { byte[] b = (new sun.misc.BASE64Decoder()).decodeBuffer(s);
		 * return new String(b); } catch (Exception e) { return null; }
		 */
		try {
			byte[] bd = Base64.decodeBase64(s);
			return (new String(bd));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取MD5后的参数的值
	 * 
	 * @param String
	 * @return String
	 */
	public static String md5(String strtomd5) {
		byte[] source = strtomd5.getBytes();
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				// 进制字符的转换
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);// 换后的结果转换为字符串
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 对键值存储的Key进行编码
	 * 
	 * @param <T>
	 * @param key
	 * @return
	 */
	public static <T> String deKey(Object key) {
		if (isBlank(key)) {
			key = "";
		}
		key = new String(base64Decode(key.toString()));
		return key.toString();
	}

	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private static SimpleDateFormat formatterWithSecond = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat formatterWithoutTime = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static Gson sGsonA = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation().serializeNulls()
			.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
			.setPrettyPrinting().setVersion(1.0).create();

	private static Gson sGson = new Gson();

	/**
	 * format long time to yyyy-MM-dd HH:mm
	 * 
	 * @param t
	 * @return
	 */
	public static final String format(long t) {
		Date date = new Date(t);
		return formatter.format(date);
	}

	/**
	 * format long time to yyyy-MM-dd
	 * 
	 * @param t
	 * @return
	 */
	public static final String formatToDate(long t) {
		Date date = new Date(t);
		return formatterWithoutTime.format(date);
	}

	public static final long dateStrTolong(String dateStr)
			throws ParseException {
		Date date = formatterWithSecond.parse(dateStr);
		return date.getTime();
	}

	/**
	 * 年月日格式 yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return long
	 * @throws ParseException
	 */
	public static final long dateYearMonthDateStrTolong(String dateStr)
			throws ParseException {
		Date date = formatterWithoutTime.parse(dateStr);
		return date.getTime();
	}

	/**
	 * 转换含注解的对象
	 * 
	 * @param src
	 * @return
	 */
	public static final String toJsonWithAnnotation(Object src) {
		return sGsonA.toJson(src);
	}

	public static final String toJson(Object src) {
		return sGson.toJson(src);
	}

	/**
	 * 精确到小数点后n位
	 * 
	 * @param in
	 * @return
	 */
	public static float keepNDecimalPlace(float in, int n) {
		BigDecimal b = new BigDecimal(in);
		in = b.setScale(n, BigDecimal.ROUND_HALF_UP).floatValue();
		return in;
	}

	// 获取配置文件
	@SuppressWarnings("rawtypes")
	public static String getAppConfig(String key) {
		Map m = PropertyHelp.loadMap("app");
		String info = m.get(key).toString().trim();
		return info;
	}

	/**
	 * url解码，默认：utf-8
	 * 
	 * @param decode
	 * @return
	 */
	public static String urlDecoder(String decode) {
		return urlDecoder(decode, "utf-8");
	}

	/**
	 * url解码
	 * 
	 * @param decode
	 * @param enc
	 *            编码
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String urlDecoder(String decode, String enc) {
		if (decode.equals(""))
			return "";
		try {
			decode = java.net.URLDecoder.decode(decode, enc);
		} catch (UnsupportedEncodingException e) {
			decode = java.net.URLDecoder.decode(decode);
		}
		return decode;
	}

	/**
	 * url编码，默认：utf-8
	 * 
	 * @param encode
	 * @return
	 */
	public static String urlEncoder(String encode) {
		return urlEncoder(encode, "utf-8");
	}

	/**
	 * url编码
	 * 
	 * @param encode
	 * @param enc
	 *            编码
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String urlEncoder(String encode, String enc) {
		if (encode.equals(""))
			return "";
		try {
			encode = java.net.URLEncoder.encode(encode, enc);
		} catch (UnsupportedEncodingException e) {
			encode = java.net.URLEncoder.encode(encode);
		}
		return encode;
	}

	/**
	 * 获取哈希文件的存储地址
	 * 
	 * @param String
	 *            type 文件类型 例："userHead"->用户头像
	 * @param String
	 *            id 类型对应的ID
	 * @return String 文件路径 filename为空时，返回目录地址
	 */
	public static String getFileUrl(String type, String id) {
		String url = "";
		String cdRoot = GlobleValue.CDROOT;
		// TODO 根据type与id哈希出文件目录
		if (type.equals("society")) { // 首页 社会化资源类型
			url = FileHash.getSocietyImage(id);
			File f = new File(url);
			if (!f.isFile() || !f.exists()) {
				url = cdRoot + "/public/img/index/res_default.png";
			}
		} else if (type.equals("userHead")) {// 用户头像
			url = FileHash.getUserHeadImage(id);
			File f = new File(url);
			if (!f.isFile() || !f.exists()) {
				url = cdRoot + "public/img/common/userHead.jpg";
			}
		} else if (type.equals("course")) { // 课程封面
			url = FileHash.getCourseImage(id);
			File f = new File(url);
			if (!f.isFile() || !f.exists()) {
				url = cdRoot + "/public/img/index/baseCourse.jpg";
			}
		} else if (type.equals("question")) { // 前台，老师角色，创建试题，上传图片
			url = FileHash.getQuestionImage(id);
		} else if (type.equals("resource")) {
			// ppt、word、excel、pdf、png、gif、jpg、 mov、fla
			id = id.replace(".", "");
			if (id.equals("gif") || id.equals("jpg") || id.equals("png")) {
				url = cdRoot + "/public/img/index/pic.png";
			} else if (id.equals("doc") || id.equals("docx")) {
				url = cdRoot + "/public/img/index/word.gif";
			} else if (id.equals("xls") || id.equals("xlsx")) {
				url = cdRoot + "/public/img/index/excel.gif";
			} else if (id.equals("ppt") || id.equals("pptx")) {
				url = cdRoot + "/public/img/index/ppt.png";
			} else if (id.equals("pdf")) {
				url = cdRoot + "/public/img/index/pdf.png";
			} else if (id.equals("fla")) {
				url = cdRoot + "/public/img/index/fla.gif";
			} else if (id.equals("mp4")) {
				url = cdRoot + "/public/img/index/mov.gif";
			} else {
				url = cdRoot + "/public/img/index/def.png";
			}
		} else if (type.equals("dynamic")) {// 空间动态图片
			url = FileHash.getDynamicImage(id);
		} else if (type.equals("privateLetter")) { // 私信图
			url = FileHash.getPrivateLetterImage(id);
		} else if (type.equals("advertise")) { // 广告图
			url = FileHash.getAdvertiseImage(id);
		} else if (type.equals("advertiseVideo")) { // 广告视频
			url = FileHash.getAdvertiseVideo(id);
		}
		return url;
	}

	/**
	 * 随机生成6位数字
	 * 
	 * @Date:2014年8月4日 下午3:37:37
	 * @Author:wangqiqi
	 * @return
	 * @throws
	 */
	public static String getRandomPwd() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		return result;
	}
}