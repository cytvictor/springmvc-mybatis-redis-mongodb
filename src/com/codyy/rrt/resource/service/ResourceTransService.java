package com.codyy.rrt.resource.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codyy.rrt.base.util.PathUtil;
import com.codyy.rrt.base.util.VideoUtil;
import com.codyy.rrt.commons.entity.AppConfig;
import com.codyy.rrt.resource.common.Constants;
import com.codyy.rrt.resource.mapper.ResourceMapper;
import com.codyy.rrt.resource.model.ResourceColumn;
import com.codyy.rrt.resource.model.Resources;

@Transactional
@Service("resourceTransService")
public class ResourceTransService {

	@Autowired
	private ResourceMapper				resourceMapper;
	@Autowired
	private SkeletonService				skeletonService;
	@Autowired
	private AppConfig					config;

	private static Logger				logger						= Logger.getLogger(ResourceTransService.class);

	private static final byte			MSG_TYPE_TRANS				= 2;

	//视频转换服务器
	private static final String			trans_servers_properties	= "mp4-trans-servers.properties";

	private static Queue<TransServer>	serverList					= null;

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

	private static class TransServer {
		public String	ip;
		public Integer	port;
	}

	private static synchronized void initPoolAndClient() {
		serverList = new LinkedList<TransServer>();
		// 从cache-servers.properties中读取server列表
		Properties properties = new Properties();
		try {
			properties.load(ResourceTransService.class.getClassLoader().getResourceAsStream(trans_servers_properties));
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
	 * 查找资源类型为精品课程，微课程，或轻松一刻的视频类型, trans的状态为pendding的数据
	 * 检查文件已经转换，则修改状态未1，转换失败则改为-1，未转换则发送消息转换
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void transMedia() throws NoSuchAlgorithmException {
		logger.debug("Start video transfer task");
		List<Resources> list = resourceMapper.findVideoTransPendding(config.getMp4transThread());
		for (Resources resource : list) {
			logger.debug("resource.id=" + resource.getId());
			String filename = resource.getHighDefine();
			String status = Constants.TRANS_SUCCESS;// filename为空的情况下视为成功
			if (StringUtils.isNotBlank(filename)) {
				status = transferVideoFile(filename);
				if (status.equals(Constants.TRANS_FAILED)) {
					resource.setTransFlag(Constants.TRANS_FAILED);
					resourceMapper.updateResourceTrans(resource);// 任何资源转换失败，则视为整个资源转换失败
					continue;
				}
			}

			filename = resource.getNormalDefine();
			String status2 = Constants.TRANS_SUCCESS;// filename为空的情况下视为成功
			if (StringUtils.isNotBlank(filename)) {
				status2 = transferVideoFile(filename);
				if (status2.equals(Constants.TRANS_FAILED)) {
					resource.setTransFlag(Constants.TRANS_FAILED);
					resourceMapper.updateResourceTrans(resource);// 任何资源转换失败，则视为整个资源转换失败
					continue;
				}
			}

			if (status.equals(Constants.TRANS_SUCCESS) && status2.equals(Constants.TRANS_SUCCESS)) {
				resource.setTransFlag(Constants.TRANS_SUCCESS);
				resourceMapper.updateResourceTrans(resource);
			}

		}
		logger.debug("end video transfer task");
	}

	public static void main(String args[]) {
		String filename = "3876db2f-d87c-4865-95d0-2a99f55eb8e9.trans.mp4";
		String prefix = filename.substring(0, filename.lastIndexOf('.'));
		String suffix = filename.substring(filename.lastIndexOf('.') + 1, filename.length());
		System.out.println(prefix);
		System.out.println(suffix);
	}

	/**
	 * 转换文件 如果文件非法或转换错误返回-1 如果文件没有转换则发送转换命令并返回0 如果文件已经转换成功返回1
	 * 
	 * @param filename
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private String transferVideoFile(String filename) throws NoSuchAlgorithmException {
		String fullFilename = VideoUtil.buildPath(filename);

		// 如果没有后缀,标记为转换失败
		if (filename.indexOf('.') == -1) {
			logger.debug("filename error:" + filename);
			return Constants.TRANS_FAILED;
		}

		// 如果文件不存在,标记为失败
		File file = new File(fullFilename);
		if (!file.exists() || file.isDirectory()) {
			logger.debug("resource file not exist:" + fullFilename);
			return Constants.TRANS_FAILED;
		}

		// 检查文件是否已经转换成功
		int dot = fullFilename.lastIndexOf('.');
		String prefix = fullFilename.substring(0, dot);
		String failedFilename = fullFilename + ".failed";
		File failedFile = new File(failedFilename);
		if (failedFile.exists() && failedFile.isFile()) {
			logger.debug("Trans failed, failedFilename=" + failedFilename);
			return Constants.TRANS_FAILED;
		}

		String successFilename = prefix + ".trans.mp4";
		File successFile = new File(successFilename);
		if (successFile.exists() && successFile.isFile()) {
			logger.debug("Trans successful, successFilename=" + successFilename);
			return Constants.TRANS_SUCCESS;
		}

		// 两个文件都未找到，待转换
		// 向转换服务器发送转换服务
		try {
			TransServer server = getServer();
			if (server != null) {
				logger.debug("send video trans:" + fullFilename);
				Socket socket = new Socket(server.ip, server.port);
				OutputStream output = socket.getOutputStream();
				byte[] bts = new byte[128];
				bts[0] = MSG_TYPE_TRANS;
				bts[1] = 0;
				bts[2] = 0;
				bts[3] = 0;
				bts[4] = 0;
				byte[] namebytes = fullFilename.getBytes();
				System.arraycopy(namebytes, 0, bts, 5, namebytes.length);
				output.write(bts);
				output.flush();
				output.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Constants.TRANS_PENDDING;
	}

	/**
	 * 转换文件, 读取Resource中trans为pending状态的元资源数据, 发送给转换服务器 已经转换的标记为success状态
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void transDocument() throws NoSuchAlgorithmException {
		logger.debug("start document transfer task");
		List<Resources> list = resourceMapper.findStudyResourceTransPendding(config.getTransThread());
		for (Resources resource : list) {
			ResourceColumn resourceColumn = skeletonService.findResourceColumnById(resource.getResourceColumnId());
			if (resourceColumn.getColumnType().equals(Constants.RESOURCE_COLUMN_DOCUMENT)) {
				String filename = resource.getStudyResource();
				String fullFilename = PathUtil.buildPath(filename);
				String transFilename = PathUtil.buildTransDocumentPath(filename);
				// 如果没有后缀,标记为转换失败
				if (filename.indexOf('.') == -1) {
					resource.setTransFlag(Constants.TRANS_FAILED);
					resourceMapper.updateResourceTrans(resource);
					logger.error("filename error:" + fullFilename);
					continue;
				}

				// 如果文件不存在,标记为失败
				File file = new File(fullFilename);
				if (!file.exists() || file.isDirectory()) {
					resource.setTransFlag(Constants.TRANS_FAILED);
					resourceMapper.updateResourceTrans(resource);
					logger.error("resource file not exist:" + fullFilename);
					continue;
				}

				// 向转换服务器发送转换服务
				try {
					logger.debug("send trans file:" + transFilename);
					Socket socket = new Socket(config.getTransServerIp(), config.getTransServerPort());
					OutputStream output = socket.getOutputStream();
					output.write(transFilename.getBytes());
					output.flush();
					output.close();
					socket.close();
					resource.setTransFlag(Constants.TRANS_TRANSING);
					resourceMapper.updateResourceTrans(resource);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		logger.debug("end document transfer task");
	}

	public void checkTransDocument() throws NoSuchAlgorithmException {
		logger.debug("Check trans document result start");
		List<Resources> list = resourceMapper.findStudyResourceTransTransing();
		for (Resources resource : list) {
			ResourceColumn resourceColumn = skeletonService.findResourceColumnById(resource.getResourceColumnId());
			if (resourceColumn.getColumnType().equals(Constants.RESOURCE_COLUMN_DOCUMENT)) {
				String fullFilename = PathUtil.buildPath(resource.getStudyResource());
				// 检查文件是否已经转换成功,成后修改转换状态和页数
				String prex = fullFilename.substring(0, fullFilename.lastIndexOf('.'));
				String pageFilename = prex + "/totalpage.txt";
				logger.debug("pageFilename=" + pageFilename);
				File pageFile = new File(pageFilename);
				if (pageFile.exists() && pageFile.isFile()) {
					logger.debug("pageFilename exist:" + pageFilename);
					try {
						FileReader reader = new FileReader(pageFile);
						char[] buffer = new char[512];
						int len = reader.read(buffer);
						if (len > 0) {
							String s = new String(buffer, 0, len);
							if (NumberUtils.isNumber(s)) {
								Integer pageCount = Integer.valueOf(s);
								logger.debug("pageCount=:" + pageCount);
								resource.setTransFlag(Constants.TRANS_SUCCESS);
								resource.setTransPageCount(pageCount);
								resourceMapper.updateResourceTrans(resource);
								continue;
							}
						}
						reader.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		logger.debug("End check trans document result");
	}
}
