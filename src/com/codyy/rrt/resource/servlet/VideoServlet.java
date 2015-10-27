package com.codyy.rrt.resource.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.codyy.rrt.base.util.VideoUtil;

/**
 * Servlet implementation class ImageServlet
 */
public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VideoServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI();
		String type = request.getParameter("type");
		int idx = path.lastIndexOf('/');
		String filename = path.substring(idx + 1, path.length());
		try {
			if (StringUtils.isNotBlank(type)&&type.equals("zigong")){
				VideoUtil.getFile(request, response,VideoUtil.buildTransPathZG(filename));
			} else {
				VideoUtil.getFile(request, response,VideoUtil.buildTransPath(filename));
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
