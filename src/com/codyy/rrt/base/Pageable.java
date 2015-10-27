package com.codyy.rrt.base;

import java.util.Collection;
import java.util.List;

/**
 * 后台查询返回前台使用
 * 
 */
public interface Pageable<T> extends Page {

	/**
	 * 获得总页数
	 * 
	 * @return 总页数
	 */
	public Integer getTotalPages();

	/**
	 * 计算总页数
	 * 
	 * @return
	 */
	public Integer calTotalPages();

	/**
	 * 设置总页数
	 * 
	 * @param pages
	 *            总页数
	 */
	public void setTotalPages(Integer pages);

	/**
	 * 获得总记录数
	 * 
	 * @return 总记录数
	 */
	public Long getTotalRecords();

	/**
	 * 设置总记录数
	 * 
	 * @param totalRecords
	 *            总记录数
	 */
	public void setTotalRecords(Long totalRecords);

	/**
	 * 获得页数据
	 * 
	 * @return 页数据
	 */
	public List<T> getList();

	/**
	 * 增加数据
	 * 
	 * @param collection
	 *            增加的页数据
	 */
	public void addData(Collection<T> collection);

	/**
	 * 增加数据
	 * 
	 * @param array
	 *            增加的页数据
	 */
	public void addData(T[] array);
}
