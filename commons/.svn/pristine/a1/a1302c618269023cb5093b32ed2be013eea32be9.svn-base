package com.codyy.rrt.commons;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.codyy.rrt.commons.utils.FileUtils;

public class FileSizeConvertTag extends TagSupport {

	private static final long serialVersionUID = -1453647734881995558L;

	// === 声明文件大小size变量
	private String value;

	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException {
		// === 输出
		JspWriter out = pageContext.getOut();
		try {
			out.write(FileUtils.changeFileSizeType(value));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

}
