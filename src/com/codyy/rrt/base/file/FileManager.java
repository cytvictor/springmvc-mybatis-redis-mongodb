/**
  FileManagerImpl.java Created on 2007-3-30 下午03:22:38
  Copyright (c) 2007 by 浙江新能量科技有限公司
 */
package com.codyy.rrt.base.file;

import java.io.File;
import java.io.InputStream;

/**
 * 文件读,写接口
 */
public interface FileManager {

	/**
	 * 将已知文件名创建到指定目录及文件名中
	 * 
	 * @param curFile
	 *            已知文件名及路径
	 * @param fileName
	 *            要创建的文件名
	 * @return boolean
	 * @throws FPException
	 */
	public boolean createFile(String curFile, String fileName);

	/**
	 * 把指定的内容写到新文件中
	 * 
	 * @param content
	 *            String
	 * @param fileName
	 *            String 要生成的文件名
	 * @return boolean
	 * @throws SgException
	 */
	public boolean writeFile(String parentPath, String content, String fileName);

	/**
	 * 删除当前根目录下的指定文件
	 * 
	 * @param fileName
	 *            指定目录下的文件名
	 * @return boolean
	 */
	public boolean deleteFile(String fileName);

	/**
	 * 删除指定目录下的指定文件
	 * 
	 * @param parentPath
	 *            String 文件夹路径
	 * @param fileName
	 *            String 指定目录下的文件名
	 * @return boolean
	 */
	public boolean deleteFile(String parentPath, String fileName);

	/**
	 * 删除当前根路径文件夹
	 * 
	 * @return boolean
	 */
	public boolean deleteFolder();

	/**
	 * 删除指定的文件夹
	 * 
	 * @param dir
	 *            文件夹
	 * @return boolean
	 */
	public boolean deleteFolder(File dir);

	/**
	 * 删除指定根目录下的所有文件
	 * 
	 * @return boolean
	 */
	public boolean deleteAllFile();

	/**
	 * 删除指定根目录下的所有文件
	 * 
	 * @param parentPath
	 *            String 根目录路径
	 * @return boolean
	 */
	public boolean deleteAllFile(String parentPath);

	/**
	 * 得到指定文件的文件大小
	 * 
	 * @param fileName
	 *            String
	 * @return long
	 */
	public long getFileSize(String fileName);

	/**
	 * 得到指定目录下指定文件的大小
	 * 
	 * @param parent
	 *            String 文件所在目录
	 * @param fileName
	 *            String 文件名
	 * @return long
	 */
	public long getFileSize(String parentPath, String fileName);

	/**
	 * 得到指定CLASSPATH目录下指定文件流
	 * 
	 * @param classPathFile
	 *            String 相对classes目录路径及文件名
	 * @return　InputStream
	 */
	public InputStream getFileStreamByClassPath(String classPathFile);

	/**
	 * Copy文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public boolean fileCopy(String oldPath, String newPath);
}
