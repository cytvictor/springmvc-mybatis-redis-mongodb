package com.codyy.rrt.resource.test;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.codyy.rrt.resource.mapper.ResourceMapper;

/**
 * @BeforeClass –> @Before –> @Test –> @After –> @AfterClass
 * @author kongchuifang
 * @date 2014-10-21
 */
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-project.xml" })
public class MybatisTest {

	@Resource
	private ResourceMapper resourceMapper;

	/**
	 * 针对所有测试，只执行一次，且必须为public static void;
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("before class");
	}

	/**
	 * 针对所有测试，将会在所有测试方法执行结束后执行一次，且必须为public static void;
	 */
	@AfterClass
	public static void afterClass() {
		System.out.println("after class");
	}

	/**
	 * 初始化方法，在任何一个测试执行之前必须执行的代码;
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("before");
	}

	/**
	 * 释放资源，在任何测试执行之后需要进行的收尾工作。在每个测试方法执行之后执行一次，该annotation只能修饰public void 方法
	 */
	@After
	public void after() {
		System.out.println("after");
	}

	/**
	 * 测试方法，表明这是一个测试方法。在Junit中将会自动被执行
	 */
	@Test
	public void testMapper() {
		try {
		} catch (Exception e) {
			fail("Test failed!");
		}
	}

	/**
	 * 略的测试方法，标注的含义就是“某些方法尚未完成，暂不参与此次测试
	 */
	@Ignore
	public void testOtherSpringObject() {
		fail("Not yet implemented");
	}

}
