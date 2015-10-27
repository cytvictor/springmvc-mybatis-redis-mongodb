package com.codyy.rrt.resource.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import com.codyy.rrt.base.Page;
import com.codyy.rrt.base.Pageable;
import com.codyy.rrt.base.SimplePage;
import com.codyy.rrt.base.util.ObjectUtil;
import com.codyy.rrt.base.util.StringUtil;
import com.codyy.rrt.commons.CommonsConstant;
import com.codyy.rrt.commons.entity.LoginUser;
import com.codyy.rrt.resource.model.BaseUser;
import com.codyy.rrt.resource.service.BaseUserService;

/**
 * 基础controller，实现通用方法
 */
public abstract class BaseController extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	@Resource
	private BaseUserService baseUserService;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleException(Exception ex, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		ex.printStackTrace();
		view.setViewName("system/fail");
		return view;
	}

	/**
	 * page对象转换成easy ui需要的map对象
	 */
	public Map<String, Object> pageToEasyUiMap(Pageable<?> page, List<?> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalRecords());
		if (CollectionUtils.isEmpty(list)) {
			map.put("rows", page.getList());
		} else {
			map.put("rows", list);
		}
		return map;
	}

	/**
	 * ajax返回调用
	 * 
	 * @param response
	 * @param object
	 */
	protected void callBack(HttpServletResponse response, Object data) {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("content-type", "text/html;charset=UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setDateHeader("Expires", 0);
			PrintWriter out = response.getWriter();
			if (data instanceof String) {
				out.print(data.toString());
			} else {
				out.print(ObjectUtil.obj2Json(data));
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否开启PHP对接
	 * 
	 * @return
	 */
	protected boolean getPhpSwitchFlag() {
		return "true".equals(ObjectUtil.getAppConfig("php.switch.flag"));
	}

	/**
	 * 获取ip
	 * 
	 * @param response
	 * @param data
	 */
	protected String getRealIp(HttpServletRequest request) {
		String headIp = request.getHeader("X-Real-IP");
		if (ObjectUtil.isBlank(headIp)) {
			headIp = request.getRemoteAddr();
		}
		return headIp;
	}

	/**
	 * 获取sso登录用户
	 * 
	 * @param request
	 * @return
	 */
	protected LoginUser getLoginUser(HttpServletRequest request) {
		LoginUser user = (LoginUser) request.getSession().getAttribute(CommonsConstant.SESSION_LOGIN_USER);
		return user;
	}

	/**
	 * 获取被访问用户
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	protected boolean getVisitorUser(HttpServletRequest request, String id) {
		if (StringUtil.isBlank(id)) {
			return false;
		}
		BaseUser baseUser = baseUserService.findByBaseUserId(id);
		if (baseUser == null) {
			return false;
		}
		request.setAttribute("visitorUser", baseUser);
		request.setAttribute("id", id);
		return true;
	}

	/**
	 * 生成page对象
	 * 
	 * @param request
	 * @return
	 */
	protected Page buildPage(HttpServletRequest request) {
		String p = request.getParameter("p");
		if (StringUtils.isNotBlank(p) && StringUtils.isNumeric(p)) {
			Page page = new SimplePage(Integer.valueOf(p));
			return page;
		} else {
			return new SimplePage();
		}
	}

	/**
	 * 生成page对象
	 * 
	 * @param request
	 * @return
	 */
	protected Page buildPageAndPagesize(HttpServletRequest request, int pagesize) {
		String p = request.getParameter("p");
		if (StringUtils.isNotBlank(p) && StringUtils.isNumeric(p)) {
			Page page = new SimplePage(Integer.valueOf(p), pagesize);
			return page;
		} else {
			return new SimplePage(1, pagesize);
		}
	}

	/**
	 * 同步分页 封装分页字符串
	 * 
	 * @param pageable
	 * @param request
	 * @return
	 */
	protected String htmlPage(Pageable<?> pageable, HttpServletRequest request) {
		int page = pageable.getCurrentPage();
		int totalPage = pageable.getTotalPages();

		String queryString = request.getQueryString();
		if (queryString != null) {
			queryString = queryString.replaceAll("(^p=[0-9]*$)|(^p=[0-9]*&)|(&p=[0-9]*$)", "");
			queryString = queryString.replaceAll("&p=[0-9]*&", "&");
		} else {
			queryString = "";
		}
		return htmlPages(page, totalPage, queryString);
	}

	protected String htmlPages(int page, int totalPage, String queryString) {
		if (totalPage <= 0)
			totalPage = 1;

		int pageCount = totalPage;
		if (pageCount <= 1)
			return ""; // 当内容数量小于等于一页显示上限时，不显示翻页控件

		// 当页数大于一页，且显示的是第一页内容时，不显示首页与上一页
		// 首页
		String firstPage = "";
		if (page > 1) {
			firstPage = (page > 1 ? "<a class='page_first' href=\"?" + queryString + "&p=1\">首页</a>" : "<a class='page_first nolink' href='javascript:;'>首页</a>");
		}
		// 上一页
		String prevPage = "";
		if (page > 1) {
			prevPage = (page > 1 ? "<a class='page_prev' href=\"?" + queryString + "&p=" + Integer.toString(page - 1) + "\">上一页</a>" : "<a class='page_prev nolink' href='javascript:;'>上一页</a>");
		}
		// 当页数大于一页，且显示的是最后一页内容时，不显示下一页与最后一页
		// 下一页
		String nextPage = "";
		if (page < pageCount) {
			nextPage = (page < pageCount ? "<a class='page_next' href=\"?" + queryString + "&p=" + Integer.toString(page + 1) + "\">下一页</a>" : "下一页");
		}
		// 最后一页
		String lastPage = "";
		if (page < pageCount) {
			lastPage = (page < pageCount ? "<a class='page_last' href=\"?" + queryString + "&p=" + Integer.toString(pageCount) + "\">末页 </a>" : "末页");
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
				numPage += "<a class='curr' href='javascript:;'>" + Integer.toString(i) + "</a>";
			else
				numPage += "<a href=\"?" + queryString + "&p=" + Integer.toString(i) + "\">" + Integer.toString(i) + "</a>";
		}
		if (pageCount - end > 1)
			numPage += "<span class=\"etc\">...</span>";
		if (page == pageCount)
			numPage += "<a class='curr' href='javascript:;'>" + Integer.toString(pageCount) + "</a>";
		else
			numPage += "<a href=\"?" + queryString + "&p=" + Integer.toString(pageCount) + "\">" + Integer.toString(pageCount) + "</a>";

		String str = "<div class=\"pageNavi\">" + firstPage + prevPage + numPage + nextPage + lastPage + "共<span>" + Integer.toString(pageCount) + "</span>页" + "</div>";
		return str;
	}

	/**
	 * 异步分页 封装分页字符串
	 * 
	 * @param pageable
	 * @param request
	 * @return
	 */
	protected String ajaxHtmlPage(int page, int totalPage, HttpServletRequest request) {
		String jsFunction = request.getParameter("jsFunction");
		if (totalPage <= 0)
			totalPage = 1;

		int pageCount = totalPage;
		if (pageCount <= 1)
			return ""; // 当内容数量小于等于一页显示上限时，不显示翻页控件

		// 当页数大于一页，且显示的是第一页内容时，不显示首页与上一页
		// 首页
		String firstPage = "";
		if (page > 1) {
			firstPage = "<li class=\"disabled pageButton\">" + (page > 1 ? "<a href=\"javascript:void(0);\" onclick='" + jsFunction + "(1);'>首页</a>" : "首页") + "</li>";
		}
		// 上一页
		String prevPage = "";
		if (page > 1) {
			prevPage = "<li class=\"disabled pageButton\">" + (page > 1 ? "<a href=\"javascript:void(0);\" onclick=\"" + jsFunction + "(" + Integer.toString(page - 1) + ");\">上一页</a>" : "上一页") + "</li>";
		}
		// 当页数大于一页，且显示的是最后一页内容时，不显示下一页与最后一页
		// 下一页
		String nextPage = "";
		if (page < pageCount) {
			nextPage = "<li class=\"pageButton\">" + (page < pageCount ? "<a href=\"javascript:void(0);\" onclick=\"" + jsFunction + "(" + Integer.toString(page + 1) + ");\">下一页</a>" : "下一页") + "</li>";
		}
		// 最后一页
		String lastPage = "";
		if (page < pageCount) {
			lastPage = "<li class=\"pageButton\">" + (page < pageCount ? "<a href=\"javascript:void(0);\" onclick=\"" + jsFunction + "(" + Integer.toString(pageCount) + ");\">末页 </a>" : "末页") + "</li>";
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
				numPage += "<li><a href=\"javascript:void(0);\" onclick=\"" + jsFunction + "(" + Integer.toString(i) + ");\">" + Integer.toString(i) + "</a></li>";
		}
		if (pageCount - end > 1)
			numPage += "<li class=\"etc\">...</li>";
		if (page == pageCount)
			numPage += "<li class=\"current\">" + Integer.toString(pageCount) + "</li>";
		else
			numPage += "<li><a href=\"javascript:void(0);\" onclick=\"" + jsFunction + "(" + Integer.toString(pageCount) + ");\">" + Integer.toString(pageCount) + "</a></li>";

		String str = "<div id=\"flip\">" + "<div class=\"pageInfo\">共<span>" + Integer.toString(pageCount) + "</span>页</div>" + "<div class=\"pagination\">" + "<ul id=\"my-pagination\">" + firstPage + prevPage + numPage + nextPage + lastPage + "</ul></div></div>";
		return str;
	}

	/**
	 * 异步分页 封装分页字符串
	 * 
	 * @param pageable
	 * @param request
	 * @return
	 */
	protected String ajaxHtmlPage(Pageable<?> pageable, HttpServletRequest request) {
		String jsFunction = request.getParameter("jsFunction");
		int page = pageable.getCurrentPage();
		int totalPage = pageable.getTotalPages();
		if (totalPage <= 0)
			totalPage = 1;

		int pageCount = totalPage;
		if (pageCount <= 1)
			return ""; // 当内容数量小于等于一页显示上限时，不显示翻页控件

		String queryString = request.getQueryString();
		if (queryString != null) {
			queryString = queryString.replaceAll("(^p=[0-9]*$)|(^p=[0-9]*&)|(&p=[0-9]*$)", "");
			queryString = queryString.replaceAll("&p=[0-9]*&", "&");
		} else {
			queryString = "";
		}
		queryString = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath() // 项目名称
				+ request.getServletPath() // 请求页面或其他地址
				+ "?" + queryString;

		// 当页数大于一页，且显示的是第一页内容时，不显示首页与上一页
		// 首页
		String firstPage = "";
		if (page > 1) {
			firstPage = (page > 1 ? "<a class='page_first' href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=1');\">首页</a>" : "<a class='page_first nolink' href='javascript:;'>首页</a>");
		}
		// 上一页
		String prevPage = "";
		if (page > 1) {
			prevPage = (page > 1 ? "<a class='page_prev' href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=" + Integer.toString(page - 1) + "');\">上一页</a>" : "<a class='page_prev nolink' href='javascript:;'>上一页</a>");
		}
		// 当页数大于一页，且显示的是最后一页内容时，不显示下一页与最后一页
		// 下一页
		String nextPage = "";
		if (page < pageCount) {
			nextPage = (page < pageCount ? "<a class='page_next' href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=" + Integer.toString(page + 1) + "');\">下一页</a>" : "下一页");
		}
		// 最后一页
		String lastPage = "";
		if (page < pageCount) {
			lastPage = (page < pageCount ? "<a class='page_last' href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=" + Integer.toString(pageCount) + "');\">末页 </a>" : "末页");
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
				numPage += "<a class='curr' href='javascript:;'>" + Integer.toString(i) + "</a>";
			else
				numPage += "<a href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=" + Integer.toString(i) + "');\">" + Integer.toString(i) + "</a>";
		}

		if (pageCount - end > 1)
			numPage += "<span class=\"etc\">...</span>";
		if (page == pageCount)
			numPage += "<a class='curr' href='javascript:;'>" + Integer.toString(pageCount) + "</a>";
		else
			numPage += "<a href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=" + Integer.toString(pageCount) + "');\">" + Integer.toString(pageCount) + "</a>";

		String str = "<div class=\"pageNavi\">" + firstPage + prevPage + numPage + nextPage + lastPage + "共<span>" + Integer.toString(pageCount) + "</span>页" + "</div>";
		return str;
	}

	/**
	 * 异步分页 显示1/5 封装分页字符串
	 * 
	 * @param pageable
	 * @param request
	 * @return
	 */
	protected String ajaxEasyHtmlPage(Pageable<?> pageable, HttpServletRequest request) {
		String jsFunction = request.getParameter("jsFunction");
		int page = pageable.getCurrentPage();
		int totalPage = pageable.getTotalPages();
		if (totalPage <= 0)
			totalPage = 1;

		int pageCount = totalPage;
		if (pageCount <= 1)
			return ""; // 当内容数量小于等于一页显示上限时，不显示翻页控件

		String queryString = request.getQueryString();
		if (queryString != null) {
			queryString = queryString.replaceAll("(^p=[0-9]*$)|(^p=[0-9]*&)|(&p=[0-9]*$)", "");
			queryString = queryString.replaceAll("&p=[0-9]*&", "&");
		} else {
			queryString = "";
		}
		queryString = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath() // 项目名称
				+ request.getServletPath() // 请求页面或其他地址
				+ "?" + queryString;

		// 当页数大于一页，且显示的是第一页内容时，不显示首页与上一页
		// 上一页
		String prevPage = "";
		if (page > 1) {
			prevPage = (page > 1 ? "<a class='page_prev' href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=" + Integer.toString(page - 1) + "');\">上一页</a>" : "<a class='page_prev nolink' href='javascript:;'>上一页</a>");
		}
		// 当页数大于一页，且显示的是最后一页内容时，不显示下一页与最后一页
		// 下一页
		String nextPage = "";
		if (page < pageCount) {
			nextPage = (page < pageCount ? "<a class='page_next' href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=" + Integer.toString(page + 1) + "');\">下一页</a>" : "下一页");
		}

		// 页码
		String numPage = "<li class=\"current\">" + page + "/" + totalPage + "</li>";

		String str = "<div class=\"pageNavi\">" + prevPage + numPage + nextPage + "共<span>" + Integer.toString(pageCount) + "</span>页" + "</div>";
		return str;
	}

	/**
	 * 简单ajax分页
	 * 
	 * @param pageable
	 * @param request
	 * @return
	 */
	protected String simpleAjaxHtmlPage(Pageable<?> pageable, HttpServletRequest request) {
		String jsFunction = request.getParameter("jsFunction");
		int page = pageable.getCurrentPage();
		int totalPage = pageable.getTotalPages();
		if (totalPage <= 0)
			totalPage = 1;

		int pageCount = totalPage;
		if (pageCount <= 1)
			return ""; // 当内容数量小于等于一页显示上限时，不显示翻页控件

		String queryString = request.getQueryString();
		if (queryString != null) {
			queryString = queryString.replaceAll("(^p=[0-9]*$)|(^p=[0-9]*&)|(&p=[0-9]*$)", "");
			queryString = queryString.replaceAll("&p=[0-9]*&", "&");
		} else {
			queryString = "";
		}
		queryString = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() // 端口号
				+ request.getContextPath() // 项目名称
				+ request.getServletPath() // 请求页面或其他地址
				+ "?" + queryString;

		// 当页数大于一页，且显示的是第一页内容时，不显示首页与上一页

		// 上一页
		String prevPage = "";
		if (page > 1) {
			prevPage = "<li class=\"disabled pageButton\" style=\"display:inline;\">" + (page > 1 ? "<a href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=" + Integer.toString(page - 1) + "');\">上一页</a>" : "上一页") + "</li>";
		}
		// 当页数大于一页，且显示的是最后一页内容时，不显示下一页与最后一页
		// 下一页
		String nextPage = "";
		if (page < pageCount) {
			nextPage = "<li class=\"pageButton\" style=\"display:inline;\">" + (page < pageCount ? "<a href=\"javascript:void(0);\" onclick=\"" + jsFunction + "('" + queryString + "&p=" + Integer.toString(page + 1) + "');\">下一页</a>" : "下一页") + "</li>";
		}

		// 页码
		String numPage = page + "/" + totalPage;
		;
		String str = prevPage + "&nbsp;&nbsp;" + numPage + "&nbsp;&nbsp;" + nextPage;
		return str;
	}

	/**
	 * 获取id数组
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	protected String[] obtainIds(HttpServletRequest request, String paramName) {
		String idsStr = request.getParameter(paramName);
		if (StringUtils.isBlank(idsStr)) {
			return null;
		}
		String[] idStrArr = idsStr.split(",");
		return idStrArr;
	}

	/**
	 * 构建EasyUIPage
	 * 
	 * @author yangyongwu
	 * @param request
	 * @return
	 * 
	 */
	protected Page buildEasyUIPage(HttpServletRequest request) {
		String p = request.getParameter("page");
		String rows = request.getParameter("rows");
		if (StringUtils.isNotBlank(p) && StringUtils.isNumeric(p)) {
			Page page = new SimplePage(Integer.valueOf(p));
			if (StringUtils.isNotBlank(rows) && StringUtils.isNumeric(rows)) {
				page.setPageCountData(Integer.valueOf(rows));
			}
			return page;
		} else {
			return new SimplePage();
		}
	}
}