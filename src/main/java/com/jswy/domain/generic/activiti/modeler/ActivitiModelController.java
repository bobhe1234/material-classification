package com.jswy.domain.generic.activiti.modeler;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jswy.domain.generic.activiti.entity.ModelMessage;

/**
 * 整合activiti在线流程编辑器
 * 
 * @author bobhe
 *
 */
@Controller
public class ActivitiModelController {

	private static final Logger logger = LoggerFactory.getLogger(ActivitiModelController.class);

	@Autowired
	ProcessEngine processEngine;
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	/**
	 * 新建一个空模型
	 */
	@RequestMapping("/create")
	public void newModel(HttpServletResponse response) throws IOException {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 设置一些默认信息
		String name = "new-process";
		String description = "";
		int revision = 1;
		String key = "process";

		// 初始化一个空模型
		Model model = repositoryService.newModel();
		model.setName(name);
		model.setKey(key);

		ObjectNode modelNode = objectMapper.createObjectNode();
		modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
		model.setMetaInfo(modelNode.toString());
		repositoryService.saveModel(model);

		// 完善ModelEditorSource
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);

		String id = model.getId();
		repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
		response.sendRedirect("/static/modeler.html?modelId=" + id);
	}

	/**
	 * 获取所有模型
	 */
	@RequestMapping("/modelList")
	@ResponseBody
	public Object modelList() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		return repositoryService.createModelQuery().list();
	}

	/**
	 * 发布模型为流程定义
	 */
	@RequestMapping("/deploy")
	@ResponseBody
	public Object deploy(String modelId) throws Exception {

		// 获取模型
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Model modelData = repositoryService.getModel(modelId);
		byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

		if (bytes == null) {
			return "模型数据为空，请先设计流程并成功保存，再进行发布。";
		}

		JsonNode modelNode = new ObjectMapper().readTree(bytes);

		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		if (model.getProcesses().size() == 0) {
			return "数据模型不符要求，请至少设计一条主线流程。";
		}
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

		// 发布流程
		String processName = modelData.getName() + ".bpmn20.xml";
		Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
				.addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
		modelData.setDeploymentId(deployment.getId());
		repositoryService.saveModel(modelData);

		return "SUCCESS";
	}

	/**
	 * 启动流程
	 */
	@RequestMapping("/start")
	@ResponseBody
	public Object startProcess(String keyName) {
		ProcessInstance process = processEngine.getRuntimeService().startProcessInstanceByKey(keyName);

		return process.getId() + " : " + process.getProcessDefinitionId();
	}

	/**
	 * 提交任务
	 */
	@RequestMapping("/run")
	@ResponseBody
	public Object run(String processInstanceId) {
		Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId)
				.singleResult();

		logger.info("task {} find ", task.getId());
		processEngine.getTaskService().complete(task.getId());
		return "SUCCESS";
	}

//	/**
//	 * 在同一个控制器中，注解了@ModelAttribute的方法实际上会在@RequestMapping方法之前被调用
//	 * 
//	 * @param modelMessage
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping(value = "/modeler", method = RequestMethod.POST)
//	public String modelerEdit(@ModelAttribute ModelMessage modelMessage, ModelMap modelMap) {
//		try {
//			logger.info("----------modelerEdit Beg ---------");
//			logger.info("----------modelMessage:" + modelMessage);
//			logger.info("----------modelMap:" + modelMap);
//			ObjectMapper objectMapper = new ObjectMapper();
//			ObjectNode editorNode = objectMapper.createObjectNode();
//			editorNode.put("id", "canvas");
//			editorNode.put("resourceId", "canvas");
//			ObjectNode stencilSetNode = objectMapper.createObjectNode();
//			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
//			editorNode.put("stencilSetNode", stencilSetNode);// get Model
//
//			RepositoryService repositoryService = processEngine.getRepositoryService();
//			Model modelData = repositoryService.newModel();
//			ObjectNode modelObjectNode = objectMapper.createObjectNode();
//			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelMessage.getName());
//			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
//			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, modelMessage.getDescription());
//			modelData.setMetaInfo(modelObjectNode.toString());
//			modelData.setName(modelMessage.getName());
//			modelData.setKey(modelMessage.getKey());
//
//			// save model
//			repositoryService.saveModel(modelData);
//			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
//			logger.info("modelData.getId():" + modelData.getId());
//			modelMap.addAttribute("modelId", modelData.getId());
//			return "modeler";
//		} catch (Exception e) {
//			logger.error("exception：" + e);
//		}
//		return null;
//	}
//
//	@RequestMapping(value = "/startProcess_test", method = RequestMethod.GET)
//	@ResponseStatus(value = HttpStatus.OK)
//	public void test1() {
//		logger.info("Start.........");
//		ProcessInstance pi = runtimeService.startProcessInstanceByKey("test");
//		logger.info("流程启动成功，流程id:{}", pi.getId());
//	}
//
//	@RequestMapping("/getTaskList_test")
//	public void test2() {
//		String userId = "root";
//		List<Task> resultTask = taskService.createTaskQuery().processDefinitionKey("test")
//				.taskCandidateOrAssigned(userId).list();
//		logger.info("任务列表：{}", resultTask);
//	}

}
