package com.codyy.rrt.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StreamUtils;

public class VideoUtil {

    private static final Log logger = LogFactory.getLog(VideoUtil.class);

    private static final String video_properties_config = "video.properties";

    private static Map<String, VideoServer> serverList = null;

    private static VideoServer currentServer = null;

    public static final String SERVER_FILE_SPLIT_FLAG = "_";

    public static class VideoServer {
	private String name;
	private String host;
	private String path;

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public String getHost() {
	    return host;
	}

	public void setHost(String host) {
	    this.host = host;
	}

	public String getPath() {
	    return path;
	}

	public void setPath(String path) {
	    this.path = path;
	}
    }

    private static void init() {
	serverList = new HashMap<String, VideoServer>();
	Properties properties = new Properties();
	try {
	    properties.load(VideoUtil.class.getClassLoader().getResourceAsStream(video_properties_config));
	    String property = properties.getProperty("server.list");
	    if (StringUtils.isNotBlank(property)) {
		String[] s = property.split(",");
		for (String serverName : s) {
		    String path = properties.getProperty("server." + serverName + ".path");
		    String host = properties.getProperty("server." + serverName + ".host");
		    VideoServer server = new VideoServer();
		    server.name = serverName;
		    server.path = path;
		    server.host = host;
		    serverList.put(serverName, server);
		}
	    }
	    String serverNameCurrent = properties.getProperty("server.current");
	    currentServer = serverList.get(serverNameCurrent);
	} catch (IOException e) {
	    logger.error("读取" + video_properties_config + "错误");
	    e.printStackTrace();
	}
	if (properties.size() == 0) {
	    logger.error(video_properties_config + "中的服务器数量为0");
	}
    }

    private static synchronized VideoServer getServer(String name) {
	if (serverList == null)
	    init();
	return serverList.get(name);
    }

    public static synchronized VideoServer getCurrentVideoServer() {
	if (currentServer == null)
	    init();
	return currentServer;
    }

    public static String buildPath(String filename) throws NoSuchAlgorithmException {
	String[] array = filename.split(SERVER_FILE_SPLIT_FLAG);
	String serverName = array[0];
	VideoServer server = getServer(serverName);
	StringBuffer buffer = new StringBuffer();
	buffer.append(server.getPath());
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

    /**
     * 构建固定转换后缀的视频文件
     * 
     * @param filename
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String buildTransPath(String filename) throws NoSuchAlgorithmException {
	String file = buildPath(filename);
	int dot = file.lastIndexOf('.');
	String prefix = file.substring(0, dot);
	String transFilename = prefix + ".trans.mp4";
	return transFilename;
    }
    
    /**
     * 自贡构建固定转换后缀的视频文件
     * 
     * @param filename
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String buildTransPathZG(String filename) throws NoSuchAlgorithmException {
	String file = buildPath(filename);
	int dot = file.lastIndexOf('.');
	String prefix = file.substring(0, dot);
	String transFilename = prefix + ".trans.mpg";
	return transFilename;
    }

    public static void getFile(HttpServletRequest request, HttpServletResponse response, String filename) throws IOException {
	File file = new File(filename);
	if (file.exists() && file.isFile()) {
	    response.setContentType("application/x-download");
	    response.setHeader("Accept-Ranges", "bytes");
	    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
	    String range = request.getHeader("Range");
	    if (StringUtils.isNotBlank(range)) {// 分段下载处理
		logger.debug("ranges:" + range);
		range = range.replaceAll("bytes=", "");
		if (StringUtils.isNotBlank(range)) {
		    long fileLength = file.length();
		    String[] x = range.split("-");
		    int start = Integer.parseInt(x[0]);// 开始位置
		    logger.debug("start:" + start);
		    Integer end = null;// 结束位置, 可能为空
		    if (x.length > 1) {
			end = Integer.valueOf(x[1]);
		    }
		    response.setHeader("Content-Length", String.valueOf(file.length() - start));
		    logger.debug("end:" + end);
		    // 检查位置是否超出范围
		    if (start < fileLength && (end == null || (end != null && end.longValue() <= fileLength && end > start))) {
			logger.debug("position verify ok.");
			try {
			    InputStream input = new FileInputStream(file);
			    int limit = -1;// 限制读取的长度
			    if (end != null)
				limit = end - start + 1;
			    input.skip(start);
			    logger.debug("skip " + start);
			    int readLength = 10240;
			    byte[] buffer = new byte[readLength];
			    if (limit != -1 && limit < readLength)
				readLength = limit;
			    logger.debug("set status and header");
			    response.setStatus(206);
			    String contentRange = new StringBuffer("bytes ").append(start).append("-").append(end == null ? "" : end).append("/").append(fileLength).toString();
			    response.setHeader("Content-Range", contentRange);
			    OutputStream out = response.getOutputStream();
			    logger.debug("start send bytes");
			    while (true) {
				int len = input.read(buffer, 0, readLength);
				if (len == -1)
				    break;
				out.write(buffer, 0, len);
				if (limit != -1) {
				    limit -= len;
				    if (limit <= 0)
					break;
				}
			    }
			    input.close();
			} catch (IOException e) {
			    logger.debug("client abort:" + e.getMessage());
			}
		    }
		}
		response.sendError(404);
	    } else {// 非分段下载
		logger.debug("no bytes tag");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		response.setStatus(200);
		try {
		    InputStream input = new FileInputStream(file);
		    StreamUtils.copy(input, response.getOutputStream());
		} catch (IOException e) {
		    logger.debug("client abort:" + e.getMessage());
		}
	    }
	} else {
	    // 未找到文件，返回404
	    logger.debug("file not found:" + filename);
	    response.sendError(404);
	}
    }
}