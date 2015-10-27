package com.codyy.rrt.base.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.codyy.base.web.SpringContext;
import com.codyy.rrt.commons.entity.AppConfig;

public class PathUtil {

	// private static final Log logger = LogFactory.getLog(PathUtil.class);

	private static boolean isPicture(String suffix) {
		suffix = suffix.toLowerCase();
		if (suffix.equals(".jpg")) {
			return true;
		} else if (suffix.equals(".jpeg")) {
			return true;
		} else if (suffix.equals(".png")) {
			return true;
		} else if (suffix.equals(".gif")) {
			return true;
		} else if (suffix.equals(".bmp")) {
			return true;
		} else
			return false;
	}

	private static boolean isDocument(String suffix) {
		suffix = suffix.toLowerCase();
		if (suffix.equals(".doc")) {
			return true;
		} else if (suffix.equals(".docx")) {
			return true;
		} else if (suffix.equals(".xls")) {
			return true;
		} else if (suffix.equals(".xlsx")) {
			return true;
		} else if (suffix.equals(".ppt")) {
			return true;
		} else if (suffix.equals(".pptx")) {
			return true;
		} else if (suffix.equals(".pdf")) {
			return true;
		} else if (suffix.equals(".txt")) {
			return true;
		} else
			return false;
	}

	public static String buildPath(String filename)
			throws NoSuchAlgorithmException {
		AppConfig appConfig = SpringContext.getBean(AppConfig.class);
		StringBuffer buffer = new StringBuffer();
		buffer.append(appConfig.getUploadfolder());
		buffer.append(File.separatorChar);
		String suffix = "";
		int x = filename.lastIndexOf('.');
		if (x >= 0) {
			suffix = filename.substring(x);
		}
		if (isPicture(suffix)) {
			buffer.append("picture");
		} else if (isDocument(suffix)) {
			buffer.append("document");
		} else {
			buffer.append("other");
		}
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte tmp[] = md.digest(filename.getBytes());
		String code = CodeUtils.encodeHexString(tmp);
		buffer.append(File.separatorChar);
		buffer.append(code.subSequence(0, 2));
		buffer.append(File.separatorChar);
		buffer.append(code.subSequence(2, 4));
		buffer.append(File.separatorChar);
		buffer.append(code.subSequence(4, 6));
		buffer.append(File.separatorChar);
		buffer.append(filename);
		return buffer.toString();
	}

	private static char windowsSeparatorChar = '\\';

	public static String buildTransDocumentPath(String filename)
			throws NoSuchAlgorithmException {

		AppConfig appConfig = SpringContext.getBean(AppConfig.class);
		StringBuffer buffer = new StringBuffer();
		buffer.append(appConfig.getTransFolder());
		buffer.append(windowsSeparatorChar);
		String suffix = "";
		int x = filename.lastIndexOf('.');
		if (x >= 0) {
			suffix = filename.substring(x);
		}
		if (isPicture(suffix)) {
			buffer.append("picture");
		} else if (isDocument(suffix)) {
			buffer.append("document");
		} else {
			buffer.append("other");
		}
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte tmp[] = md.digest(filename.getBytes());
		String code = CodeUtils.encodeHexString(tmp);
		buffer.append(windowsSeparatorChar);
		buffer.append(code.subSequence(0, 2));
		buffer.append(windowsSeparatorChar);
		buffer.append(code.subSequence(2, 4));
		buffer.append(windowsSeparatorChar);
		buffer.append(code.subSequence(4, 6));
		buffer.append(windowsSeparatorChar);
		buffer.append(filename);
		return buffer.toString();
	}
}