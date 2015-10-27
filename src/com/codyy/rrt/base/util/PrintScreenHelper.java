package com.codyy.rrt.base.util;

/**
 * 视频截图状态
 */
public class PrintScreenHelper {

	private String filename;	//视频文件地址，相对地址
	private String fullFilename;//全地址
	private Integer duration;	//视频总时长
	private int status = 0;			//截图状态, 0=等待,  1=截图已经完成, -1=失败
	private Integer pictureCount = 0; 	//截图总数
	private Integer from;		//图片序列开始
	private Integer to;			//图片序列结束from,to的范围应该在pictureCount内
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getPictureCount() {
		return pictureCount;
	}
	public void setPictureCount(Integer pictureCount) {
		this.pictureCount = pictureCount;
	}
	public Integer getFrom() {
		return from;
	}
	public void setFrom(Integer from) {
		this.from = from;
	}
	public Integer getTo() {
		return to;
	}
	public void setTo(Integer to) {
		this.to = to;
	}
	public String getFullFilename() {
		return fullFilename;
	}
	public void setFullFilename(String fullFilename) {
		this.fullFilename = fullFilename;
	}
}
