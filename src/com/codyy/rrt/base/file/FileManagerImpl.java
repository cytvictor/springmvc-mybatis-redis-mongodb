package com.codyy.rrt.base.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 */
public class FileManagerImpl implements FileManager {
	private static Log log = LogFactory.getLog(FileManagerImpl.class);

	private static String fileSeparator = System.getProperty("file.separator");

	private transient String curRootPath;

	public FileManagerImpl() {

	}

	/**
	 * 构造函数 创建文件生成根目录文件夹
	 * 
	 * @param rootPath
	 *            String the root path for create file
	 */
	public FileManagerImpl(String rootPath) {
		// sub string
		curRootPath = rootPath;
		File root = new File(curRootPath);
		// make folders.
		if (!root.exists()) {
			root.mkdirs();
		}
	}

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
	@Override
	public boolean createFile(String curFile, String fileName) {
		log.debug(curFile);
		File folder = new File(curRootPath);
		// 删除根目录文件夹下所有文件
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				// delete file
				if (files[i].isFile()) {
					files[i].delete();
				}
			}
		}
		//
		File nFile = new File(folder, fileName);
		try {
			if (nFile.createNewFile()) {
				// get inputstream from formfile
				BufferedInputStream input = new BufferedInputStream(new FileInputStream(curFile));
				// InputStream input = new FileInputStream(curFile);//速度慢
				BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(nFile));

				int read = input.read();
				while (read != -1) {
					bo.write(read);
					read = input.read();
				}
				bo.flush();
				input.close();
				bo.close();
			}
		} catch (Exception ex) {
			log.debug("create file failure", ex);
			return false;
		}
		return true;
	}

	public String readFile(String fileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return null;
			}
			FileReader reader = new FileReader(file);
			int fileLen = (int) file.length();
			char[] chars = new char[fileLen];
			reader.read(chars);
			return String.valueOf(chars);
		} catch (Exception ex) {
			// TODO: handle exception
			log.debug("read file failure", ex);
			return null;
		}
	}

	/**
	 * 把指定的内容写到新文件中
	 * 
	 * @param content
	 *            String
	 * @param fileName
	 *            String 要生成的文件名
	 * @param type
	 *            String append为追加
	 * @return boolean
	 * @throws SgException
	 */
	public boolean writeFile(String parentPath, String content, String fileName, String type) {
		try {
			File root = new File(parentPath);
			// make folders.
			if (!root.exists()) {
				root.mkdirs();
			}
			String filePath = parentPath + fileSeparator + fileName;
			FileWriter fileWriter;
			if (type.equals("append")) {
				fileWriter = new FileWriter(filePath, true);
			} else {
				fileWriter = new FileWriter(filePath);
			}
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 重载函数把内容写到新文件中
	 * 
	 * @param content
	 *            String
	 * @param fileName
	 *            String 要生成的文件名
	 * @return boolean
	 * @throws SgException
	 */
	@Override
	public boolean writeFile(String parentPath, String content, String fileName) {
		return writeFile(parentPath, content, fileName, "new");
	}

	/**
	 * 删除当前根目录下的指定文件
	 * 
	 * @param fileName
	 *            指定目录下的文件名
	 * @return boolean
	 */
	@Override
	public boolean deleteFile(String fileName) {
		try {
			File folder = new File(curRootPath);
			if (folder.exists() && folder.isDirectory()) {
				File[] files = folder.listFiles();
				for (int i = 0; i < files.length; i++) {
					// delete file
					if (files[i].isFile() && files[i].getName().equals(fileName)) {
						files[i].delete();
					}
				}
			}
		} catch (Exception ex) {
			log.debug(ex);
			return false;
		}
		return true;
	}

	/**
	 * 删除指定目录下的指定文件
	 * 
	 * @param fileName
	 *            指定目录下的文件名
	 * @return boolean
	 */
	@Override
	public boolean deleteFile(String parentPath, String fileName) {
		curRootPath = parentPath;
		return deleteFile(fileName);
	}

	/**
	 * 删除当前根目录下的所有文件
	 * 
	 * @return boolean
	 */
	@Override
	public boolean deleteAllFile() {
		try {
			File folder = new File(curRootPath);
			if (folder.exists() && folder.isDirectory()) {
				File[] files = folder.listFiles();
				for (int i = 0; i < files.length; i++) {
					// delete file
					if (files[i].isFile()) {
						files[i].delete();
					}
				}
			}
		} catch (Exception ex) {
			log.debug(ex);
			return false;
		}
		return true;
	}

	/**
	 * 删除当前根路径文件夹
	 * 
	 * @return boolean
	 */
	@Override
	public boolean deleteFolder() {
		try {

			if (curRootPath != null) {
				File folder = new File(curRootPath);
				return deleteFolder(folder);
			} else {
				return false;
			}
		} catch (Exception ex) {
			log.debug("delete root folder failure!");
			return false;
		}
	}

	/**
	 * 删除指定的文件夹
	 * 
	 * @param dir
	 *            文件夹
	 * @return boolean
	 */
	@Override
	public boolean deleteFolder(File dir) {
		try {
			if (dir != null && dir.isDirectory()) {
				File[] entries = dir.listFiles();
				int sz = entries.length;
				for (int i = 0; i < sz; i++) {
					if (entries[i].isDirectory()) {
						deleteFolder(entries[i]);
					} else {
						entries[i].delete();
					}
				}
				dir.delete();
			}
		} catch (Exception ex) {
			log.debug("delete dir failure!");
			return false;
		}
		return true;
	}

	/**
	 * 删除指定根目录下的所有文件
	 * 
	 * @param parentPath
	 *            文件夹目录
	 * @return boolean
	 */
	@Override
	public boolean deleteAllFile(String parentPath) {
		curRootPath = parentPath;
		return deleteAllFile();
	}

	/**
	 * 得到指定文件的文件大小
	 * 
	 * @param fileName
	 *            String
	 * @return long
	 */
	@Override
	public long getFileSize(String fileName) {
		System.out.println("curRootPath:" + curRootPath + "end" + "===" + fileName);
		File file = new File(curRootPath, fileName);
		if (file.exists() && file.isFile()) {
			log.debug("file: + fileName" + " size is " + file.length() + "字节");
			return file.length();
		}

		return -1L;
	}

	/**
	 * 得到指定目录下指定文件的大小
	 * 
	 * @param parent
	 *            String 文件所在目录
	 * @param fileName
	 *            String 文件名
	 * @return long
	 */
	@Override
	public long getFileSize(String parentPath, String fileName) {
		curRootPath = parentPath;
		return getFileSize(fileName);
	}

	@Override
	public InputStream getFileStreamByClassPath(String classPathFile) {
		return FileManagerImpl.class.getResourceAsStream(classPathFile);
	}

	/**
	 * Copy文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return boolean
	 */
	@Override
	public boolean fileCopy(String oldPath, String newPath) {
		try {

			File file = new File(oldPath);

			FileInputStream fis = new FileInputStream(file);

			FileOutputStream fos = new FileOutputStream(newPath);

			byte[] iobuff = new byte[(int) file.length()];
			int bytes;

			while ((bytes = fis.read(iobuff)) != -1) {
				fos.write(iobuff, 0, bytes);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
			log.info("Copy Issuance File Fail !", e);
			return false;
		}

		return true;
	}
}