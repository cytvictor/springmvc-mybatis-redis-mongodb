package com.codyy.rrt.resource.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("thirdInterface")
public class ThirdInterface{
	
	@RequestMapping("rrtPrintScreenTest")
	public void printScreenTest(String DBType,HttpServletResponse response) throws Exception{
		String ip = "";
		int port = 0;
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("print-screen-servers.properties");
			Properties properties = new Properties();
			properties.load(in);
			Socket s;
			if(properties.size() == 0){
				in.close();
				response.getWriter().write("{'result':'0','ip':'"+ip+":"+port+"'}");
			}else{
				for (Map.Entry<Object, Object> entry : properties.entrySet()) {
					ip = entry.getKey().toString();
					port = Integer.valueOf(entry.getValue().toString());
					s = new Socket();
					s.connect(new InetSocketAddress(ip,port), 5000);
					s.close();
				}
				in.close();
				response.getWriter().write("{'result':'1','ip':'"+ip+":"+port+"'}");
			}
		} catch (IOException e) {
			System.out.println("printScreen Server failed !!!");
			response.getWriter().write("{'result':'0','ip':'"+ip+":"+port+"'}");
		}
	}
	
	@RequestMapping("rrtVideoTransTest")
	public void videoTransTest(String DBType,HttpServletResponse response) throws Exception{
		String ip = "";
		int port = 0;
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("mp4-trans-servers.properties");
			Properties properties = new Properties();
			properties.load(in);
			Socket s;
			if(properties.size() == 0){
				in.close();
				response.getWriter().write("{'result':'0','ip':'"+ip+":"+port+"'}");
			}else{
				for (Map.Entry<Object, Object> entry : properties.entrySet()) {
					ip = entry.getKey().toString();
					port = Integer.valueOf(entry.getValue().toString());
					s = new Socket();
					s.connect(new InetSocketAddress(ip,port), 5000);
					s.close();
				}
				in.close();
				response.getWriter().write("{'result':'1','ip':'"+ip+":"+port+"'}");
			}
		} catch (IOException e) {
			System.out.println("videoTrans Server failed !!!");
			response.getWriter().write("{'result':'0','ip':'"+ip+":"+port+"'}");
		}
	}
	
}