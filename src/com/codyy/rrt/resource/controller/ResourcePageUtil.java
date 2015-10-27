package com.codyy.rrt.resource.controller;

import java.io.UnsupportedEncodingException;

import com.codyy.rrt.base.Pageable;

/**
 * 资源分页工具
 */
public class ResourcePageUtil {

	/**
	 * 根据Pageable生成分页的字符串
	 * 
	 * @param pageable
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String buildPageInfo(Pageable<?> pageable) throws UnsupportedEncodingException {
		int page = pageable.getCurrentPage();
		int pageCount = pageable.getTotalPages();
		if (pageCount <= 1)
			return ""; // 当内容数量小于等于一页显示上限时，不显示翻页控件

		// 当页数大于一页，且显示的是第一页内容时，不显示首页与上一页
		// 首页
		String firstPage = "";
		if (page > 1) {
			firstPage = "<li class=\"disabled pageButton\">" + (page > 1 ? "<a href=\"javascript:queryResource(1)\">首页</a>" : "首页") + "</li>";
		}
		// 上一页
		String prevPage = "";
		if (page > 1) {
			prevPage = "<li class=\"disabled pageButton\">" + (page > 1 ? "<a href=\"javascript:queryResource(" + Integer.toString(page - 1) + ")\">上一页</a>" : "上一页") + "</li>";
		}
		// 当页数大于一页，且显示的是最后一页内容时，不显示下一页与最后一页
		// 下一页
		String nextPage = "";
		if (page < pageCount) {
			nextPage = "<li class=\"pageButton\">" + (page < pageCount ? "<a href=\"javascript:queryResource(" + Integer.toString(page + 1) + ")\">下一页</a>" : "下一页") + "</li>";
		}
		// 最后一页
		String lastPage = "";
		if (page < pageCount) {
			lastPage = "<li class=\"pageButton\">" + (page < pageCount ? "<a href=\"javascript:queryResource(" + Integer.toString(pageCount) + ")\">末页 </a>" : "末页") + "</li>";
		}

		// 页码
		String numPage = "";
		int start = 1;
		if (pageCount - page < 4) {
			start = pageCount - 5;
			if (start <= 0)
				start = 1;
		} else
			start = (page - 2 <= 0 ? 1 : page - 2);
		int end = (start + 4 >= pageCount ? pageCount - 1 : start + 4);
		int i = 0;
		for (i = start; i <= end; i++) {
			if (i == page)
				numPage += "<li class=\"current\">" + Integer.toString(i) + "</li>";
			else
				numPage += "<li><a href=\"javascript:queryResource(" + Integer.toString(i) + ")\">" + Integer.toString(i) + "</a></li>";
		}
		if (pageCount - end > 1)
			numPage += "<li class=\"etc\">...</li>";
		if (page == pageCount)
			numPage += "<li class=\"current\">" + Integer.toString(pageCount) + "</li>";
		else
			numPage += "<li><a href=\"javascript:queryResource(" + Integer.toString(pageCount) + ")\">" + Integer.toString(pageCount) + "</a></li>";

		String str = "<div id=\"flip\">" + "<div class=\"pageInfo\">共<span>" + Integer.toString(pageCount) + "</span>页</div>" + "<div class=\"pagination\">" + "<ul id=\"my-pagination\">" + firstPage + prevPage + numPage + nextPage + lastPage + "</ul></div></div>";
		return str;
	}

}
