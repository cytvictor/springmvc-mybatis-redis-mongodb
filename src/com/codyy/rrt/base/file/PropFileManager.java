/**
  PropFileManager.java Created on 2007-4-2 下午04:41:02
  Copyright (c) 2007 by 浙江新能量科技有限公司
 */
package com.codyy.rrt.base.file;

import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 属性文件读,写接口
 */
public interface PropFileManager {

	/**
	 * 装载Properties配置文件
	 * 
	 * @param classPath
	 *            String 相对classes目录路径
	 * @return　Properties
	 */
	public Properties load(String classPath);

	/**
	 * 装载Properties配置文件到ResourceBundle，通过类路径
	 * 
	 * @param propName
	 *            String 类路径名
	 *            如：com.freshpower.common.conf.email表示装载WEB-INF/classes
	 *            /com/freshpower/common/conf/email.properties文件
	 * @return ResourceBundle
	 */
	public ResourceBundle loadPropByClassType(String propName);

	/**
	 * 得到String属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param prop
	 *            属性
	 * @param defaultValue
	 *            String 缺省值
	 * @return String
	 */
	public String getString(String property, Properties prop, String defaultValue);

	/**
	 * 得到String属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param defaultValue
	 *            String 缺省值
	 * @return
	 */
	public String getString(String property, String defaultValue);

	/**
	 * 得到String属性值
	 * 
	 * @param property
	 *            属性名称
	 * @return
	 */
	public String getString(String property);

	/**
	 * 得到ResourceBundle绑定中的属性值
	 * 
	 * @param property
	 *            String key值
	 * @return String
	 */
	public String getPropertyValue(String property);

	/**
	 * 得到int属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param properties
	 *            属性
	 * @param defaultValue
	 *            int 缺省值
	 * @return int
	 */
	public int getInt(String property, Properties properties, int defaultValue);

	/**
	 * 得到int属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param defaultValue
	 *            int 缺省值
	 * @return int
	 */
	public int getInt(String property, int defaultValue);

	/**
	 * 得到long属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param properties
	 *            属性
	 * @param defaultValue
	 *            long 缺省值
	 * @return long
	 */
	public long getLong(String property, Properties properties, long defaultValue);

	/**
	 * 得到long属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param defaultValue
	 *            long 缺省值
	 * @return long
	 */
	public long getLong(String property, long defaultValue);

	/**
	 * 得到boolean属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param properties
	 *            属性
	 * @param defaultValue
	 *            缺省值
	 * @return boolean
	 */
	public boolean getBoolean(String property, Properties properties, boolean defaultValue);

	/**
	 * 得到boolean属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param defaultValue
	 *            缺省值
	 * @return boolean
	 */
	public boolean getBoolean(String property, boolean defaultValue);

	/**
	 * 将属性值保存到Map中
	 * 
	 * @param property
	 *            属性名称
	 * @param delim
	 *            属性值分隔符
	 * @param properties
	 *            属性
	 * @return Map
	 */
	@SuppressWarnings("rawtypes")
	public Map toMap(String property, String delim, Properties properties);

	/**
	 * 将属性值保存到Map中 name=第一个值 value=第二个值, 类推
	 * 
	 * @param property
	 *            属性名称
	 * @param delim
	 *            属性值分隔符
	 * @return Map
	 */
	@SuppressWarnings("rawtypes")
	public Map toMap(String property, String delim);

	/**
	 * 将属性值保存到数组中
	 * 
	 * @param property
	 *            属性名称
	 * @param delim
	 *            属性值分隔符
	 * @param properties
	 *            属性
	 * @return
	 */
	public String[] toStringArray(String property, String delim, Properties properties);

	/**
	 * 将属性值保存到数组中
	 * 
	 * @param property
	 *            属性名称
	 * @param delim
	 *            属性值分隔符
	 * @return
	 */
	public String[] toStringArray(String property, String delim);
}