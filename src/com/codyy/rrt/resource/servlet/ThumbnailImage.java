package com.codyy.rrt.resource.servlet;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.codyy.rrt.base.util.PathUtil;
import com.codyy.rrt.base.util.ThumbnailImageTransfer;

public class ThumbnailImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ThumbnailImageTransfer thumbnailImageTransfer;

	public ThumbnailImage() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI();
		int idx = path.lastIndexOf('/');
		String filename = path.substring(idx + 1, path.length());
		String w = request.getParameter("width");
		String h = request.getParameter("height");
		int width = 100, height = 100;
		if (StringUtils.isNotBlank(w) && StringUtils.isNotBlank(h)) {
			Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
			Matcher isNum = pattern.matcher(w);
			Matcher isNum1 = pattern.matcher(h);
			if (isNum.matches() && isNum1.matches()) {
				width = (int) Double.parseDouble(w);
				height = (int) Double.parseDouble(h);
			}
		}

		try {
			File file = new File(PathUtil.buildPath(filename));
			if (file.exists()) {
				// InputStream input = new FileInputStream(file);
				response.addHeader("Cache-Control", "max-age=864000");
				ImageIO.write(thumbnailImageTransfer.getThumbnailImage(file,
						width, height), "JPEG", response.getOutputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		thumbnailImageTransfer = new ThumbnailImageTransfer();
	}

}
