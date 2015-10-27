package com.codyy.rrt.base;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;

/**
 * 日志记录类,唯一模式设计,SystemLogger.getInstance()取得实例后,getLogger()取得Logger，记录日志；
 * <p>
 * Title
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 */
public class SystemLogger {
	private static final Log dbLogger = LogFactory.getLog("tianque.dblog");
	private static final Log errorLogger = LogFactory.getLog("tianque.errorlog");
	private static final Log operationLogger = LogFactory.getLog("tianque.operationlog");
	private static SystemLogger systemLogger = new SystemLogger();

	private SystemLogger() {
		// initDBLogger();
		// initErrorLogger();
		// initOperationLogger();
	}

	// 取得日志目录
	@SuppressWarnings({ "rawtypes" })
	public static Map getLogDirMap() {
		Map map = new LinkedHashMap();
		// map.put(GlobleValue.OPERATION_LOG, "操作日志");
		// map.put(GlobleValue.DB_LOG, "数据库日志");
		// map.put(GlobleValue.ERROR_LOG, "错误日志");
		return map;
	}

	// 取得日志文件
	public static File[] getLogFiles(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		return files;
	}

	// 取得日志文件(分页)
	public static File[] getLogFilesPages(String path, int start, int pageSize, int count) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		File[] files4Pages = null;

		if ((start + pageSize) < count) {
			files4Pages = new File[pageSize];
			for (int i = start; i < start + pageSize; i++) {
				files4Pages[i - start] = files[i];
			}
		} else {
			files4Pages = new File[count - start];
			for (int i = start; i < count; i++) {
				files4Pages[i - start] = files[i];
			}
		}
		return files4Pages;
	}

	/**
	 * 取得该对象实例
	 * 
	 * @return SystemLogger
	 */
	public static SystemLogger getInstance() {
		return systemLogger;
	}

	/**
	 * 取得Logger，Logger用法参考jdk1.4,java.util.logging 仅供OfficeDataSource调用。
	 * 
	 * @return Log
	 */
	public Log getDBLogger() {
		return dbLogger;
	}

	/**
	 * 取得Logger，Logger用法参考jdk1.4,java.util.logging
	 * 
	 * @return Log
	 */
	public Log getErrorLogger() {
		return errorLogger;
	}

	/**
	 * 取得Logger，Logger用法参考jdk1.4,java.util.logging
	 * 
	 * @return Log
	 */
	public Log getOperationLogger() {
		return operationLogger;
	}

	/**
	 * 把Exception转换为String,以便记入到日志中去。
	 * 
	 * @param ex
	 * @return
	 */
	public static String parseException(Exception ex) {
		StackTraceElement[] ste = ex.getStackTrace();
		StringBuffer sb = new StringBuffer(800);
		for (int i = 0; i < ste.length; i++) {
			sb.append(i).append(": ").append(ste[i]).append("/n");
		}
		return sb.toString();
	}

	/*
	 * private void initDBLogger() { try { String filePath = GlobleValue.DB_LOG
	 * + "db.txt"; File fileLog = new File(filePath); // 20070806
	 * System.out.println("fileLog.isExist:" + // fileLog.exists()); if
	 * (!fileLog.exists()) { fileLog.createNewFile(); } PatternLayout layout =
	 * new PatternLayout( "%-4r %-5p %3x %d{yyyy-MM-dd HH:mm:ss} | %m%n");
	 * DailyRollingFileAppender appender = new DailyRollingFileAppender( layout,
	 * filePath, "'.'yyyy-ww"); ((Log4JLogger)
	 * dbLogger).getLogger().addAppender(appender); } catch (IOException ex) {
	 * ex.printStackTrace(); } }
	 * 
	 * private void initOperationLogger() { try { String filePath =
	 * GlobleValue.OPERATION_LOG + "oa.txt"; File fileLog = new File(filePath);
	 * if (!fileLog.exists()) { fileLog.createNewFile(); } PatternLayout layout
	 * = new PatternLayout( "%-4r %-5p %3x %d{yyyy-MM-dd HH:mm:ss} | %m%n");
	 * DailyRollingFileAppender appender = new DailyRollingFileAppender( layout,
	 * filePath, "'.'yyyy-ww"); ((Log4JLogger)
	 * operationLogger).getLogger().addAppender(appender); } catch (IOException
	 * ex) { ex.printStackTrace(); } }
	 * 
	 * private void initErrorLogger() { try { String filePath =
	 * GlobleValue.ERROR_LOG + "error.txt"; File fileLog = new File(filePath);
	 * // 20070806 System.out.println("fileLog.isExist:" + // fileLog.exists());
	 * if (!fileLog.exists()) { fileLog.createNewFile(); } PatternLayout layout
	 * = new PatternLayout( "%-4r %-5p %3x %d{yyyy-MM-dd HH:mm:ss} | %m%n");
	 * DailyRollingFileAppender appender = new DailyRollingFileAppender( layout,
	 * filePath, "'.'yyyy-ww"); ConsoleAppender conAppender = new
	 * ConsoleAppender(layout); org.apache.log4j.Logger log = ((Log4JLogger)
	 * errorLogger) .getLogger(); log.addAppender(appender);
	 * log.addAppender(conAppender); log.setLevel(Level.ERROR); } catch
	 * (IOException ex) { ex.printStackTrace(); }
	 * 
	 * }
	 */

	public void info(String message) {
		((Log4JLogger) errorLogger).getLogger().info(message);
	}

	public void error(String message, Exception ex) {
		((Log4JLogger) errorLogger).getLogger().error(message, ex);
	}

	public void error(Exception ex) {
		((Log4JLogger) errorLogger).getLogger().error("Exception:", ex);
	}
}