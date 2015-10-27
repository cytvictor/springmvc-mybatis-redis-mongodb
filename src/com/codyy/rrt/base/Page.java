package com.codyy.rrt.base;

/**
 * 前台JSP页面传递参数
 */
public interface Page {
	
	/**
	 * 获得当前页
	 * @return 当前页
	 */
	public Integer getCurrentPage();
	
	/**
	 * 设置当前页
	 * @param currentPage 当前页
	 */
	public void setCurrentPage(Integer currentPage);
	
	/**
	 * 获得每页记录数
	 * @return 每页记录数
	 */
	public Integer getPageCountData();
	
	/**
	 * 设置每页记录数
	 * @param pageCount 每页记录数
	 */
	public void setPageCountData(Integer pageCountData);
	
}
