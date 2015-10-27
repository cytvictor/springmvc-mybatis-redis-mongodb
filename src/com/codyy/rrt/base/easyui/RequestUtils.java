package com.codyy.rrt.base.easyui;

import javax.servlet.http.HttpServletRequest;

import com.codyy.rrt.base.Page;
import com.codyy.rrt.base.SimplePage;

/**
 * EasyUi页面数据封装
 */
public class RequestUtils {
	/**
	 * 将EasyUI的分页请求参数封装为Page对象
	 * 
	 * @param request
	 * @return
	 */
	public static Page buildPage(HttpServletRequest request) {
		Page page = new SimplePage();
		String paramPage = request.getParameter("page");// 当前页数
		if (paramPage != null && paramPage.length() > 0) {
			Integer intPage = Integer.valueOf(paramPage);
			page.setCurrentPage(intPage);
		}
		String paramSize = request.getParameter("rows");// 每页总数
		if (paramSize != null && paramSize.length() > 0) {
			Integer intSize = Integer.valueOf(paramSize);
			page.setPageCountData(intSize);
		}
		return page;
	}

}
