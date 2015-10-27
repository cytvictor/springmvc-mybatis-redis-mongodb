package com.codyy.rrt.resource.service.test;

import org.junit.Test;

import com.codyy.rrt.base.Constant;
import com.codyy.rrt.resource.service.ResourceLogService;
import com.codyy.rrt.resource.test.BaseTest;

public class ResourceLogServiceTest extends BaseTest{
	
	private ResourceLogService resourceLogService;
	
	@Test
	public void testInsertData(){
		resourceLogService = context.getBean(ResourceLogService.class);
		resourceLogService.addLogResource("test", Constant.RES_ACTION_RECOMMEND, "111", "I don't know");
		
		/*SqlSessionFactory factory = (SqlSessionFactory)context.getBean("sqlSessionFactoryForBaseInfo");
		
		SqlSession session = factory.openSession();
		
		ResourceLogMapper mapper = session.getMapper(ResourceLogMapper.class);
		
		mapper.insert(resourceLog);*/
		
	}
	
}
