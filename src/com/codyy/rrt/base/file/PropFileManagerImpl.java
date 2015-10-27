package com.codyy.rrt.base.file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 属性文件读、写实现类
 */
public class PropFileManagerImpl implements PropFileManager {
	private static Log log = LogFactory.getLog(PropFileManagerImpl.class);

	private Properties prop;
	public ResourceBundle bundle = null;

	/**
	 * 构造函数
	 * 
	 */
	public PropFileManagerImpl() {

	}

	/**
	 * 构造函数
	 * 
	 * @param filePath
	 *            String 文件全路径
	 */
	public PropFileManagerImpl(String filePath) {
		log.debug("load properties file:" + filePath);
		prop = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			prop.load(in);
			in.close();
		} catch (Exception ex) {
			log.debug(ex);
		}
	}

	/**
	 * 装载Properties配置文件
	 * 
	 * @param classPath
	 *            String 相对classes目录路径
	 * @return　Properties
	 */
	@Override
	public Properties load(String classPath) {
		log.debug("load properties file:" + classPath);
		prop = new Properties();
		try {
			prop.load(FileManagerFactory.getFileManager().getFileStreamByClassPath(classPath));
		} catch (Exception ex) {
			log.debug(ex);
			ex.printStackTrace();
			prop = null;
		}
		return prop;
	}

	/**
	 * 装载Properties配置文件到ResourceBundle，通过类路径
	 * 
	 * @param propName
	 *            String 类路径名
	 * @return
	 */
	@Override
	public ResourceBundle loadPropByClassType(String propName) {
		bundle = ResourceBundle.getBundle(propName);
		return bundle;
	}

	/**
	 * 得到ResourceBundle绑定中的属性值
	 * 
	 * @param property
	 *            String key值
	 * @return String
	 */
	@Override
	public String getPropertyValue(String property) {
		String propValue = "";
		try {
			if (bundle != null) {
				propValue = bundle.getString(property);
				propValue = new String(propValue.getBytes("iso8859-1"));
			}
		} catch (Exception ex) {
			log.debug(ex);
		}
		return propValue;
	}

	/**
	 * 得到String属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param prop
	 *            　属性
	 * @param defaultValue
	 *            String 缺省值
	 * @return
	 */
	@Override
	public String getString(String property, Properties prop, String defaultValue) {
		String propValue = prop.getProperty(property);
		try {
			if (propValue == null) {
				propValue = defaultValue;
			} else {
				// System.out.println("====propValue=====" + propValue);
				propValue = new String(propValue.getBytes("iso8859-1"));
			}
		} catch (Exception ex) {
			propValue = defaultValue;
		}
		return propValue;
	}

	/**
	 * 得到String属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param defaultValue
	 *            String 缺省值
	 * @return
	 */
	@Override
	public String getString(String property, String defaultValue) {
		Properties properties = prop;
		return getString(property, properties, defaultValue);
	}

	/**
	 * 得到String属性值
	 * 
	 * @param property
	 *            属性名称
	 * @return
	 */
	@Override
	public String getString(String property) {
		return getString(property, "");
		// return prop.getProperty(property);
	}

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
	@Override
	public int getInt(String property, Properties properties, int defaultValue) {
		String propValue = properties.getProperty(property);
		return (propValue == null) ? defaultValue : Integer.parseInt(propValue);
	}

	/**
	 * 得到int属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param defaultValue
	 *            int 缺省值
	 * @return int
	 */
	@Override
	public int getInt(String property, int defaultValue) {
		Properties properties = prop;
		return getInt(property, properties, defaultValue);
	}

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
	@Override
	public long getLong(String property, Properties properties, long defaultValue) {
		String propValue = properties.getProperty(property);
		return (propValue == null) ? defaultValue : Long.parseLong(propValue);
	}

	/**
	 * 得到long属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param defaultValue
	 *            long 缺省值
	 * @return long
	 */
	@Override
	public long getLong(String property, long defaultValue) {
		Properties properties = prop;
		return getLong(property, properties, defaultValue);
	}

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
	@Override
	public boolean getBoolean(String property, Properties properties, boolean defaultValue) {
		String propValue = properties.getProperty(property);
		return (propValue == null) ? defaultValue : Boolean.valueOf(propValue).booleanValue();
	}

	/**
	 * 得到boolean属性值
	 * 
	 * @param property
	 *            属性名称
	 * @param defaultValue
	 *            缺省值
	 * @return boolean
	 */
	@Override
	public boolean getBoolean(String property, boolean defaultValue) {
		Properties properties = prop;
		return getBoolean(property, properties, defaultValue);
	}

	/**
	 * 将属性值保存到Map中 name=第一个值 value=第二个值, 类推
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
	@Override
	public Map toMap(String property, String delim, Properties properties) {
		Map<String, String> map = new HashMap<String, String>();
		String propValue = properties.getProperty(property);
		if (propValue != null) {
			StringTokenizer tokens = new StringTokenizer(propValue, delim);
			while (tokens.hasMoreTokens()) {
				map.put(tokens.nextToken(), tokens.hasMoreElements() ? tokens.nextToken() : "");
			}
		}
		return map;
	}

	/**
	 * 将属性值保存到Map中
	 * 
	 * @param property
	 *            属性名称
	 * @param delim
	 *            属性值分隔符
	 * @return Map
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map toMap(String property, String delim) {
		Properties properties = prop;
		return toMap(property, delim, properties);
	}

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
	@Override
	public String[] toStringArray(String property, String delim, Properties properties) {
		String propValue = properties.getProperty(property);
		return propValue.split(delim);
		// return StringUtil.split(delim,propValue,false);
	}

	/**
	 * 将属性值保存到数组中
	 * 
	 * @param property
	 *            属性名称
	 * @param delim
	 *            属性值分隔符
	 * @return
	 */
	@Override
	public String[] toStringArray(String property, String delim) {
		Properties properties = prop;
		return toStringArray(property, delim, properties);
	}
}
