package com.jswy.domain.generic.activiti.modeler;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ActivitiTestController {

	private static final Logger logger = LoggerFactory.getLogger(ActivitiTestController.class);

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void test1() {
		logger.info("Start.........");
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("test");
		logger.info("流程启动成功，流程id:{}", pi.getId());
	}

	@RequestMapping("/test2")
	public void test2() {
		String userId = "root";
		List<Task> resultTask = taskService.createTaskQuery().processDefinitionKey("test")
				.taskCandidateOrAssigned(userId).list();
		logger.info("任务列表：{}", resultTask);
	}

}
