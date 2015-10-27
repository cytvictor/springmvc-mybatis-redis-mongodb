package com.codyy.rrt.commons.controller;

import java.io.IOException;
import java.io.InputStream;

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
public class HeadImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeadImageServlet() {
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
		GridFS gfs = new GridFS(db, "HEADPIC");
		GridFSDBFile file = gfs.findOne(path);
		if (file != null){
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
