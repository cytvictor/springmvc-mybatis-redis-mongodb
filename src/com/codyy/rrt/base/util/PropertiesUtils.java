package com.codyy.rrt.base.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.codyy.rrt.resource.common.Constants;

public final class PropertiesUtils {

	private static final Log log = LogFactory.getLog(PropertiesUtils.class);
	
	private static final Map<String, Properties> map = new HashMap<String, Properties>();

	public static String getPropertyValue(String fileName, String key) throws IOException {
		Properties p = map.get(fileName);
		if (p == null) {
			synchronized (PropertiesUtils.class) {
				if (p == null) {
					p = new Properties();
					p.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName));
					map.put(fileName, p);
				}
			}
		}
		return (String) p.get(key);
	}

	/**
	 * 获得最大上传附件大小，单位为B
	 * 
	 * @param key
	 * @return
	 */
	public static Long getMaxUploadSize(String key) {
		try {
			String maxUploadSize = getPropertyValue(Constants.PATH_CONFIG_PROPERTIES, key);
			if (StringUtils.isNotEmpty(maxUploadSize)) {
				return Long.valueOf(maxUploadSize);
			}
		} catch (Exception e) {
			log.info(e);
		}
		if (Constants.MAX_UPLOAD_SIZE_VIDEO.equals(key)) {
			return 1024l;
		} else if (Constants.MAX_UPLOAD_SIZE_DOCUMENT.equals(key)) {
			return 5l;
		} else if (Constants.MAX_UPLOAD_SIZE_PICTURE.equals(key)) {
			return 4l;
		} else if (Constants.MAX_UPLOAD_SIZE_THUMB.equals(key)) {
			return 4l;
		}
		return 10l;
	}
}
