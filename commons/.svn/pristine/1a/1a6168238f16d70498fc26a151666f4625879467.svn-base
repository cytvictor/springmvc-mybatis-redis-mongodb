package com.codyy.rrt.commons.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StreamUtils;

import com.codyy.base.web.SpringContext;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * Servlet implementation class ImageServlet
 */
public class AttachedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttachedServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		int p = path.lastIndexOf('/');
		if (p >= 0){
			path = path.substring(p + 1);
		}
		MongoTemplate template = SpringContext.getBean(MongoTemplate.class);
		DB db = template.getDb();
		GridFS gfs = new GridFS(db, "attached");
		GridFSDBFile file = gfs.findOne(path);
		if (file != null){
			String filename = file.get("original").toString();
			response.setContentType("application/octet-stream");  
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1"));// firefox浏览器
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));// IE浏览器
			}else{
				response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			}
			response.setHeader("Content-Length", String.valueOf(file.getLength()));
			InputStream input = file.getInputStream();
			StreamUtils.copy(input, response.getOutputStream());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
