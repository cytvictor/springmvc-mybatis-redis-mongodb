package com.codyy.rrt.resource.test;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xierongbing
 * @date 2015年8月7日 上午9:26:13 
 * @description
 * 测试的类继承BaseTest
 */
public class BaseTest {
	public static ApplicationContext context ;
	public static String springPath = "spring-context-project.xml";
	
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext(springPath) ;
	}
}
