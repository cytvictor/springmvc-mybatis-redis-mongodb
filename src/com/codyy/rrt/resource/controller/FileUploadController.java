package com.codyy.rrt.resource.controller;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.InputFormatException;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.codyy.base.web.SpringContext;
import com.codyy.rrt.base.CustomMultipartResolver;
import com.codyy.rrt.base.GlobleValue;
import com.codyy.rrt.base.ResultJson;
import com.codyy.rrt.base.util.DateTime;
import com.codyy.rrt.base.util.FileUploadProgressListener;
import com.codyy.rrt.base.util.NumUtil;
import com.codyy.rrt.base.util.ObjectUtil;
import com.codyy.rrt.base.util.PathUtil;
import com.codyy.rrt.base.util.PrintScreenHelper;
import com.codyy.rrt.base.util.Progress;
import com.codyy.rrt.base.util.PropertiesUtils;
import com.codyy.rrt.base.util.StringUtil;
import com.codyy.rrt.base.util.VideoUtil;
import com.codyy.rrt.base.util.VideoUtil.VideoServer;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.AppConfig;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.service.ResourceLogService;

/**
 * 文件上传
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/uploadHandle/")
public class FileUploadController extends BaseController {

	private static final Log logger = LogFactory.getLog(FileUploadController.class);

	// 上传成功
	private static final int UPLOAD_SUCCESS = 1;
	// 上传空文件
	private static final int UPLOAD_NULL_FILE = 2;
	// 不是允许上传类型
	private static final int UPLOAD_NOT_ALLOW_TYPE = 3;

	@Autowired
	private AppConfig appConfig;
	@Autowired
	private ResourceLogService resourceLogService;

	public static final String SESSION_PRINT_SCREEN = "SESSION_PRINT_SCREEN";

	// 截图服务器配置文件
	private static final String trans_servers_properties = "print-screen-servers.properties";
	// 截图服务器队列表
	private static Queue<TransServer> serverList = null;

	public static TransServer getServer() {
		if (serverList == null) {
			initPoolAndClient();
		}
		if (serverList.size() > 0) {
			TransServer server = serverList.poll();
			serverList.offer(server);
			return server;
		}
		return null;
	}

	/**
	 * 转换程序地址
	 */
	private static class TransServer {
		public String ip;
		public Integer port;
	}

	private static synchronized void initPoolAndClient() {
		serverList = new LinkedList<TransServer>();
		// 从cache-servers.properties中读取server列表
		Properties properties = new Properties();
		try {
			properties.load(FileUploadController.class.getClassLoader().getResourceAsStream(trans_servers_properties));
		} catch (IOException e) {
			logger.error("读取" + trans_servers_properties + "错误");
			e.printStackTrace();
			return;
		}
		if (properties.size() == 0) {
			logger.error(trans_servers_properties + "中的服务器数量为0");
			return;
		}
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			TransServer server = new TransServer();
			server.ip = entry.getKey().toString();
			server.port = Integer.valueOf(entry.getValue().toString());
			serverList.add(server);
		}
	}

	/**
	 * 上传文件
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response, Writer writer) throws IOException, NoSuchAlgorithmException {
		// get sequence
		String sequence = request.getParameter("sequence"); // 高清或普清
		String maxSize = request.getParameter("maxSize");
		logger.debug("sequence=" + sequence);
		if (StringUtils.isBlank(sequence)) {
			writer.write(new ResultJson(false, "参数sequence找不到!").toString());
			return;
		}

		// get print screen status
		boolean printScreen = false;
		if ("true".equals(request.getParameter("printScreen")))// 截图或不截图
			printScreen = true;
		if (printScreen) {
			// 检查SESSION_PRINT_SCREEN是否存在，如果存在则删除原有截图数据
			PrintScreenHelper helper = (PrintScreenHelper) request.getSession().getAttribute(SESSION_PRINT_SCREEN);
			request.getSession().removeAttribute(SESSION_PRINT_SCREEN);
			if (helper != null) {
				String filename = helper.getFullFilename();
				String prefix = filename.substring(0, filename.lastIndexOf('.'));
				for (int i = 1; i <= helper.getPictureCount(); i++) {
					File deleteThumbFile = new File(prefix + "." + i + ".jpeg");
					if (deleteThumbFile.exists()) {
						deleteThumbFile.delete();
					}
				}
			}
		}

		// prepare map and progress
		Map<String, Progress> map = (Map<String, Progress>) request.getSession().getAttribute(FileUploadProgressListener.SESSION_PROGRESS_NAME);
		if (map == null) {
			map = new HashMap<String, Progress>();
			request.getSession().setAttribute(FileUploadProgressListener.SESSION_PROGRESS_NAME, map);
		}
		Progress progress = map.get(sequence);
		if (progress != null) {// progress不为空时，原有资源未保存，需要删除
			AppConfig config = SpringContext.getBean(AppConfig.class);
			File deleteThumbFile = new File(config.getUploadfolder() + progress.getPhysicalFilename());
			if (deleteThumbFile.exists()) {
				deleteThumbFile.delete();
			}
		}
		progress = new Progress();
		map.put(sequence, progress);

		// resolver upload
		CustomMultipartResolver resolver = new CustomMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		if (sequence.equals("studyresource")) {
			Long maxUploadSize = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_DOCUMENT);
			resolver.setMaxUploadSize(maxUploadSize * 1024 * 1024);
		} else if (sequence.equals("thumb")) {
			Long maxUploadSize = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_THUMB);
			resolver.setMaxUploadSize(maxUploadSize * 1024 * 1024);
		} else if (sequence.startsWith("image")) {
			Long maxUploadSize = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_PICTURE);
			resolver.setMaxUploadSize(maxUploadSize * 1024 * 1024);
		} else {
			Long maxUploadSize = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_VIDEO);
			resolver.setMaxUploadSize(maxUploadSize * 1024 * 1024);
		}
		resolver.setServletContext(request.getSession().getServletContext());
		resolver.setMaxInMemorySize(1024 * 1024);
		if (StringUtil.isNotBlank(maxSize)) {
			resolver.setMaxUploadSize(Long.valueOf(maxSize));
		}
		resolver.setUploadTempDir(new FileSystemResource(appConfig.getUploadfolder() + "/resourceUploadTempDir"));
		MultipartHttpServletRequest multiRequest = null;
		try {
			multiRequest = resolver.resolveMultipart(request);
		} catch (MaxUploadSizeExceededException e) {
			map.remove(sequence);// 文件上传失败，需要移除Progress
			writer.write(new ResultJson(false, 0, "文件过大").toString());
			logger.debug("multiFile is big");
			return;
		}

		Map<String, MultipartFile> filemap = multiRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entry : filemap.entrySet()) {
			System.out.println("name=" + entry.getKey());
		}

		MultipartFile multiFile = null;
		Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			multiFile = entry.getValue();
			break;
		}
		if (multiFile == null || multiFile.getSize() == 0) {
			writer.write(new ResultJson(false, "文件内容为空!").toString());
			logger.debug("multiFile is empty");
			return;
		}
		String original = multiFile.getOriginalFilename();
		original = (original == null) ? "" : original;
		if (StringUtils.isNotEmpty(original)) {
			String prefix = original.substring(0, original.lastIndexOf('.'));
			String suffix = original.substring(original.lastIndexOf('.'));
			original = prefix + suffix.toLowerCase();
		}
		progress.setOriginalFilename(original);
		progress.setFileSize(multiFile.getSize());
		String suffix = "";
		if (original != null) {
			int x = original.lastIndexOf('.');
			if (x >= 0) {
				suffix = original.substring(x).toLowerCase();
			}
		}
		progress.setStatus(1);
		File file = null;
		if (sequence.equals("highdefine") || sequence.equals("middledefine")) {
			file = createVideoFile(progress, suffix);
		} else {
			file = createFile(progress, suffix);
		}
		logger.debug("Trans file to:" + progress.getPhysicalFilename());
		multiFile.transferTo(file);// 写入目标文件
		progress.setStatus(2);
		// 如果需要截图，则开始截图
		if (printScreen) {
			logger.debug("printScreen:true");
			PrintScreenHelper helper = new PrintScreenHelper();
			request.getSession().setAttribute(SESSION_PRINT_SCREEN, helper);
			helper.setFilename(progress.getPhysicalFilename());
			helper.setFullFilename(progress.getFullFilename());
			helper.setStatus(0);
			File source = new File(progress.getFullFilename());
			Encoder encoder = new Encoder();
			try {
				MultimediaInfo m = encoder.getInfo(source);
				int ls = (int) (m.getDuration() / 1000);
				logger.debug("duration=" + m.getDuration());
				if (ls <= 1) {
					helper.setStatus(-1);// 时长太短，则认为失败
				} else {
					helper.setDuration(ls);
					int count = 50;// 默认截取50张图片
					if (count * appConfig.getPrintscreenInternal() >= ls) {
						count = ls / appConfig.getPrintscreenInternal() - 1;
					}
					helper.setPictureCount(count);
					TransServer server = getServer();
					Socket socket = new Socket(server.ip, server.port);
					OutputStream output = socket.getOutputStream();
					byte[] bts = new byte[128];
					bts[0] = 1;// Frame type
					bts[1] = (byte) appConfig.getPrintscreenInternal().intValue();
					bts[2] = 0;
					bts[3] = 1;
					bts[4] = (byte) count;
					logger.debug("print screen count:" + count);
					byte[] namebytes = progress.getFullFilename().getBytes();
					logger.debug("Send print screen message, file:" + progress.getFullFilename());
					System.arraycopy(namebytes, 0, bts, 5, namebytes.length);
					output.write(bts);
					output.flush();
					output.close();
					socket.close();
					helper.setFrom(1);
					helper.setTo(count < 5 ? count : 5);
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.debug("Connect to print screen server failed.");
				helper.setStatus(-1);
			} catch (InputFormatException e) {
				e.printStackTrace();
				logger.debug("Video format error, can't read duration.");
				helper.setStatus(-1);
			} catch (EncoderException e) {
				e.printStackTrace();
				logger.debug("Video format error, can't read duration.");
				helper.setStatus(-1);
			}
		}
		JSONObject json = new JSONObject();
		json.put("result", true);
		json.put("name", progress.getPhysicalFilename());
		json.put("size", NumUtil.divideNumber(progress.getContentLength(), 1000000));
		json.put("filepath", file);
		writer.write(json.toString());
		logger.debug("transferTo over");
	}

	@RequestMapping(value = "kupload", method = RequestMethod.POST)
	public void uploadForKindEditor(HttpServletRequest request, HttpServletResponse response, Writer writer) throws IOException, NoSuchAlgorithmException {
		// get sequence
		String sequence = request.getParameter("sequence");
		String maxSize = request.getParameter("maxSize");
		logger.debug("sequence=" + sequence);
		if (StringUtils.isBlank(sequence)) {
			writer.write(makeKindEditorBackJson(true, "参数sequence找不到!", null).toString());
			return;
		}

		// resolver upload
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		if (sequence.equals("image")) {
			Long maxUploadSize = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_PICTURE);
			resolver.setMaxUploadSize(maxUploadSize * 1024 * 1024);
		} else {
			Long maxUploadSize = PropertiesUtils.getMaxUploadSize(Constants.MAX_UPLOAD_SIZE_VIDEO);
			resolver.setMaxUploadSize(maxUploadSize * 1024 * 1024);
		}
		resolver.setServletContext(request.getSession().getServletContext());
		resolver.setMaxInMemorySize(1024 * 1024);
		if (StringUtil.isNotBlank(maxSize)) {
			resolver.setMaxUploadSize(Long.valueOf(maxSize));
		}
		resolver.setUploadTempDir(new FileSystemResource(appConfig.getUploadfolder() + "/resourceUploadTempDir"));
		MultipartHttpServletRequest multiRequest = null;
		try {
			multiRequest = resolver.resolveMultipart(request);
		} catch (MaxUploadSizeExceededException e) {
			writer.write(makeKindEditorBackJson(true, "图片过大，请限制在5M!", null).toString());
			logger.debug("multiFile is big");
			return;
		}

		MultipartFile multiFile = null;
		Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			multiFile = entry.getValue();
			break;
		}
		if (multiFile == null || multiFile.getSize() == 0) {
			writer.write(makeKindEditorBackJson(false, "文件内容为空!", null).toString());
			logger.debug("multiFile is empty");
			return;
		}
		String original = multiFile.getOriginalFilename();
		original = (original == null) ? "" : original;
		String suffix = "";
		if (original != null) {
			int x = original.lastIndexOf('.');
			if (x >= 0) {
				suffix = original.substring(x);
			}
		}
		Progress progress = new Progress();
		File file = createFile(progress, suffix);
		logger.debug("Trans file to:" + progress.getPhysicalFilename());
		multiFile.transferTo(file);// 写入目标文件
		String contextPath = request.getContextPath();
		JSONObject json = makeKindEditorBackJson(false, "", contextPath + "/ImageServlet/" + progress.getPhysicalFilename());
		writer.write(json.toString());
		logger.debug("transferTo over:" + json.toString());
	}

	private JSONObject makeKindEditorBackJson(boolean hasError, String msg, String fileLoc) {
		JSONObject json = new JSONObject();
		json.put("error", hasError ? 1 : 0);
		json.put("message", msg);
		if (fileLoc != null) {
			json.put("url", fileLoc);
		}
		return json;
	}

	// 根据截图序列号显示临时截图的文件
	@RequestMapping(value = "displayPrintScreen")
	public void displayPrintScreen(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			PrintScreenHelper helper = (PrintScreenHelper) request.getSession().getAttribute(SESSION_PRINT_SCREEN);
			if (helper != null) {
				String filename = helper.getFullFilename();
				String prefix = filename.substring(0, filename.lastIndexOf('.'));
				File jpgfile = new File(prefix + "." + id + ".jpeg");
				if (jpgfile.exists() && jpgfile.isFile()) {
					InputStream input = new FileInputStream(jpgfile);
					response.addHeader("Cache-Control", "max-age=0");
					response.setContentType("image/jpeg");
					StreamUtils.copy(input, response.getOutputStream());
					input.close();
				}
			}
		}
	}

	// ajax请求获取截图的状态
	@RequestMapping(value = "printScreenProgress")
	public void printScreenProgress(HttpServletRequest request, Writer writer) throws IOException {
		// logger.debug("printScreenProgress");
		JSONObject json = new JSONObject();
		PrintScreenHelper helper = (PrintScreenHelper) request.getSession().getAttribute(SESSION_PRINT_SCREEN);
		if (helper != null) {
			String filename = helper.getFullFilename();
			String prefix = filename.substring(0, filename.lastIndexOf('.'));
			// 检查是否截图失败
			File failedFile = new File(prefix + ".jpeg.failed");
			File jpgfile = new File(prefix + "." + helper.getPictureCount() + ".jpeg");
			if (failedFile.exists() && failedFile.isFile()) {
				helper.setStatus(-1);
				logger.debug("printScreenProgress failed");
			} else if (jpgfile.exists() && jpgfile.isFile()) {
				helper.setStatus(1);
				logger.debug("printScreenProgress successful");
			}
			json.put("status", helper.getStatus());
			json.put("pictureCount", helper.getPictureCount());
			json.put("from", helper.getFrom());
			json.put("to", helper.getTo());
		} else {
			json.put("status", 0);// 文件还在等待转换
			logger.debug("not find PrintScreenHelper");
		}
		writer.write(json.toString());
	}

	/**
	 * 上传文件进度
	 * 
	 * @param request
	 * @param writer
	 * @throws IOException
	 */
	@RequestMapping(value = "progress", method = RequestMethod.POST)
	public void progress(HttpServletRequest request, Writer writer) throws IOException {
		String sequence = request.getParameter("sequence");
		// logger.debug("sequence=" + sequence);
		if (StringUtils.isBlank(sequence)) {
			writer.write(new ResultJson(false, "Sequence parameter error!").toString());
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String, Progress> map = (Map<String, Progress>) request.getSession().getAttribute(FileUploadProgressListener.SESSION_PROGRESS_NAME);
		if (map != null) {
			Progress progress = map.get(sequence);
			if (progress != null) {
				JSONObject json = new JSONObject();
				json.put("result", true);
				json.put("percent", NumUtil.getPercent(progress.getBytesRead(), progress.getContentLength()));
				json.put("read", NumUtil.divideNumber(progress.getBytesRead(), 1000000));
				json.put("size", NumUtil.divideNumber(progress.getContentLength(), 1000000));
				json.put("speed", buildSpeed(progress.getBytesRead(), progress.getStartTime()));
				json.put("status", progress.getStatus());
				writer.write(json.toString());
			} else {
				// logger.debug("progress is null");
			}
		} else {
			// logger.debug("map is null");
		}
	}

	private String buildSpeed(long length, long startTime) {
		long speed = length * 1000 / (System.currentTimeMillis() - startTime) / 1024;
		return String.valueOf(speed);
	}

	private File createVideoFile(Progress progress, String suffix) throws IOException, NoSuchAlgorithmException {
		String random = UUID.randomUUID().toString();
		VideoServer server = VideoUtil.getCurrentVideoServer();
		String filename = server.getName() + VideoUtil.SERVER_FILE_SPLIT_FLAG + random + suffix;
		String fullFilename = VideoUtil.buildPath(filename);
		File file = new File(fullFilename);
		if (file.exists()) {
			return createVideoFile(progress, suffix);
		} else {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			logger.debug("generate file:" + fullFilename);
			if (file.createNewFile()) {
				progress.setPhysicalFilename(filename);
				progress.setFullFilename(fullFilename);
				return file;
			} else
				throw new IOException("Create file failed!");
		}
	}

	private File createFile(Progress progress, String suffix) throws IOException, NoSuchAlgorithmException {
		String filename = UUID.randomUUID().toString() + suffix;
		String fullFilename = PathUtil.buildPath(filename);
		File file = new File(fullFilename);
		if (file.exists()) {
			return createFile(progress, suffix);
		} else {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			logger.debug("generate file:" + fullFilename);
			if (file.createNewFile()) {
				progress.setPhysicalFilename(filename);
				progress.setFullFilename(fullFilename);
				return file;
			} else
				throw new IOException("Create file failed!");
		}
	}

	@RequestMapping(value = "deleteProgress", method = RequestMethod.POST)
	public void deleteProgress(HttpServletRequest request, Writer writer) throws IOException {
		String sequence = request.getParameter("sequence");
		if (StringUtils.isBlank(sequence)) {
			writer.write(new ResultJson(false, "Sequence parameter error!").toString());
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String, Progress> map = (Map<String, Progress>) request.getSession().getAttribute(FileUploadProgressListener.SESSION_PROGRESS_NAME);
		JSONObject json = new JSONObject();
		if (map != null) {
			Progress progress = map.get(sequence);
			if (progress != null) {
				String filePath = progress.getFullFilename();
				File file = new File(filePath);
				if (file.exists()) {
					file.delete();
				}
			}
			map.put(sequence, null);
		}
		json.put("result", true);
		writer.write(json.toString());
	}

	/**
	 * 上传图片
	 */
	@RequestMapping(value = "uploadImgFile", method = RequestMethod.POST)
	public void uploadImgFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		// 上传图片默认文件夹
		String folder = "temp";
		List<String> filenames = new ArrayList<String>();
		int upload_result = handlerImgFile(request, folder, filenames);
		if (upload_result == UPLOAD_SUCCESS) {
			result.put("oldname", filenames.get(0));
			result.put("filename", filenames.get(1));
			result.put("filedir", "/temp");
			result.put("result", "yes");
		} else if (upload_result == UPLOAD_NULL_FILE) {
			result.put("result", "文件不存在！");
		} else if (upload_result == UPLOAD_NOT_ALLOW_TYPE) {
			result.put("result", "文件类型不正确！");
		} else {
			result.put("result", "上传失败！");
		}
		callBack(response, result);
	}

	/**
	 * 处理图片文件上传
	 */
	public int handlerImgFile(HttpServletRequest req, String folder, List<String> filenames) throws IOException {
		CustomMultipartResolver resolver = new CustomMultipartResolver();
		MultipartHttpServletRequest request = resolver.resolveMultipart(req);
		Date baseDate = new Date();
		String fileName = DateTime.toDate("yyyyMMddHHmmss", baseDate);
		MultipartFile file = request.getFile("Filedata");
		if (ObjectUtil.isBlank(file)) {
			file = request.getFile("file");
		}
		if (file == null) {// step-2 判断file是否为空
			return UPLOAD_NULL_FILE;
		}
		String orgFileName = file.getOriginalFilename();
		String imgType = FilenameUtils.getExtension(orgFileName).toLowerCase();
		if (!GlobleValue.IMG_TYPE_LIST.contains(imgType)) {
			return UPLOAD_NOT_ALLOW_TYPE;
		}
		orgFileName = (orgFileName == null) ? "" : orgFileName;
		Pattern p = Pattern.compile("\\s|\t|\r|\n|&|\\+|%|#|'");
		Matcher m = p.matcher(orgFileName);
		orgFileName = m.replaceAll("_");
		String realFilePath = GlobleValue.filePath(folder);
		if (!(new File(realFilePath).exists())) {
			new File(realFilePath).mkdirs();
		}
		String newfilename = FilenameUtils.getBaseName(orgFileName).concat(".") + fileName.concat(".").concat(FilenameUtils.getExtension(orgFileName).toLowerCase());
		String bigRealFilePath = realFilePath + File.separator + newfilename;
		if (file.getSize() > 0) {
			File targetFile = new File(bigRealFilePath);
			file.transferTo(targetFile);// 写入目标文件
		}
		filenames.add(orgFileName);
		filenames.add(newfilename);
		return UPLOAD_SUCCESS;
	}
}