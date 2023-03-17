package com.jswy.interfaces.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jswy.application.service.MsgDataProducer;
import com.jswy.interfaces.assembler.DataResponse;

import io.swagger.annotations.Api;

@RestController
@Api(tags = "内部队列")
@RequestMapping("/queue")
public class DisruptorController {
	private Logger logger = LoggerFactory.getLogger(DisruptorController.class);
	/**
	 * 注入SeriesDataEventQueueHelper消息生产者
	 */
	@Autowired
	private MsgDataProducer service;

	/**
	 * 项目内部使用Disruptor做消息队列;应用A通过/data 接口把数据发送到应用B ,然后通过seriesDataEventQueueHelper
	 * 把消息发给disruptor队列,<br>
	 * 消费者去消费,整个过程对不会堵塞应用A. 可接受消息丢失,
	 * 可以通过扩展SeriesDataEventQueueHelper来达到对disruptor队列的监控
	 * 
	 * @param message
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public DataResponse<String> receiverDeviceData(@RequestBody String message) throws InterruptedException {
		long startTime1 = System.currentTimeMillis();
		logger.info("队列服务:开始 - 接受消息[{}]", message);

		if (StringUtils.isEmpty(message)) {
			logger.info("receiver data is empty !");
			return new DataResponse<String>(400, "failed");
		}
		service.processData(message);
		long startTime3 = System.currentTimeMillis();
		logger.info("队列服务:结束 - 完成发布消息 当前时间[{}] 耗时[{}]", new Date(), startTime3 - startTime1);
		return new DataResponse<String>(200, "success");
	}
}
