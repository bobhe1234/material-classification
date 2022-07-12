package com.jswy;

import org.activiti.engine.RepositoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试activiti与spring整合是否成功(如果数据库没有表，就会自动创建25张表)
 **/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:activiti-spring.xml")
public class ActivitiTest {
	@Autowired
	private RepositoryService repositoryService;

	@Test
	public void test01() {
		System.out.println("部署对象:" + repositoryService);
	}
}