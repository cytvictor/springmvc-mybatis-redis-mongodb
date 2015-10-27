package com.codyy.rrt.commons.entity;

public class AppConfig {

	private String uploadfolder;
	
	private Long appSessionTimeout;	
	//文档转换------------------------------------
	private String transServerIp;	
	
	private Integer transServerPort;
	
	private String transFolder;
	
	private Integer transThread; //同时转换线程数
	
	
	//视频转换mp4加水印
	private String mp4transFolder;		//转码服务器映射目录
	
	private Integer mp4transThread;		//一次定时任务视频转换线程数
	
	//视频截图
	private String printscreenFolder;		//截图服务器映射目录
	
	private Integer printscreenInternal;	//自动截图时，每隔多久一张图片
	
	private String transferChars;		//需要转换的字符
	
	public String getTransServerIp() {
		return transServerIp;
	}

	public void setTransServerIp(String transServerIp) {
		this.transServerIp = transServerIp;
	}

	public Integer getTransServerPort() {
		return transServerPort;
	}

	public void setTransServerPort(Integer transServerPort) {
		this.transServerPort = transServerPort;
	}

	public String getTransFolder() {
		return transFolder;
	}

	public void setTransFolder(String transFolder) {
		this.transFolder = transFolder;
	}

	public Integer getTransThread() {
		return transThread;
	}

	public void setTransThread(Integer transThread) {
		this.transThread = transThread;
	}

	public String getMp4transFolder() {
		return mp4transFolder;
	}

	public void setMp4transFolder(String mp4transFolder) {
		this.mp4transFolder = mp4transFolder;
	}

	public Integer getMp4transThread() {
		return mp4transThread;
	}

	public void setMp4transThread(Integer mp4transThread) {
		this.mp4transThread = mp4transThread;
	}

	public String getPrintscreenFolder() {
		return printscreenFolder;
	}

	public void setPrintscreenFolder(String printscreenFolder) {
		this.printscreenFolder = printscreenFolder;
	}

	public Integer getPrintscreenInternal() {
		return printscreenInternal;
	}

	public void setPrintscreenInternal(Integer printscreenInternal) {
		this.printscreenInternal = printscreenInternal;
	}

	public String getUploadfolder() {
		return uploadfolder;
	}

	public void setUploadfolder(String uploadfolder) {
		this.uploadfolder = uploadfolder;
	}


	public Long getAppSessionTimeout() {
		return appSessionTimeout;
	}

	public void setAppSessionTimeout(Long appSessionTimeout) {
		this.appSessionTimeout = appSessionTimeout;
	}

	public String getTransferChars() {
		return transferChars;
	}

	public void setTransferChars(String transferChars) {
		this.transferChars = transferChars;
	}
	
	
}
