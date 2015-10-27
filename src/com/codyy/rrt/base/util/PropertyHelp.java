package com.codyy.rrt.base.util;

/**
 * @{#} PropertiesHelp.java Created on 2007-7-24下午04:02:37
 *
 * Copyright (c) 2007 by 浙江新能量科技有限公司.
 */
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.codyy.rrt.base.file.FileManagerFactory;
import com.codyy.rrt.base.file.PropFileManager;

/**
 * 
 * <p>
 * Title: 属性文件操作辅助
 * </p>
 * 
 * <p>
 * Description: 属性文件操作辅助
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: FreshPower
 * </p>
 * 
 * @author <a href=mailto:liuxd@freshpower.cn>Nick Yau</a>
 * @version 1.0
 */
public abstract class PropertyHelp {

	// 属性文件管理器
	private static PropFileManager propFileManager = FileManagerFactory
			.getPropFileManager();

	/**
	 * 得到属性文件中某个键的值
	 * 
	 * @param filePath
	 *            属性文件路径(不包括属性文件扩展名)
	 * @param key
	 *            需要得到值的键
	 * @return String 需要得到键的值
	 */
	public static String getPropertyValue(String filePath, String key) {
		propFileManager.loadPropByClassType(filePath);
		return propFileManager.getPropertyValue(key);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map loadMap(String filePath) {
		ResourceBundle b = propFileManager.loadPropByClassType(filePath);
		Enumeration e = b.getKeys();
		Map p = new HashMap();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			p.put(key, b.getString(key.toString()));
		}
		return p;
	}
}