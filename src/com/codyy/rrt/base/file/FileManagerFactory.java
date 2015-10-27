package com.codyy.rrt.base.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 普通文件\Properties文件\Xml文件的工厂类
 */
public class FileManagerFactory {

	private static Log log = LogFactory.getLog(FileManagerFactory.class);

	/**
	 * 构造函数
	 *
	 */
	public FileManagerFactory() {
	};

	/**
	 * 得到FileManager接口的实现类,并创建根目录;
	 * 
	 * @param rootPath
	 *            String
	 * @return FileManager
	 */
	public static FileManager getFileManager(String rootPath) {
		log.debug(rootPath);
		return new FileManagerImpl(rootPath);
	}

	/**
	 * 得到FileManager接口的实现类
	 * 
	 * @return FileManager
	 */
	public static FileManager getFileManager() {
		return new FileManagerImpl();
	}

	/**
	 * 得到PropFileManager接口的实现类
	 * 
	 * @return PropFileManager
	 */
	public static PropFileManager getPropFileManager(String filePath) {
		return new PropFileManagerImpl(filePath);
	}

	/**
	 * 得到PropFileManager接口的实现类,调用默认构造函数
	 * 
	 * @return PropFileManager
	 */
	public static PropFileManager getPropFileManager() {
		return new PropFileManagerImpl();
	}
}
