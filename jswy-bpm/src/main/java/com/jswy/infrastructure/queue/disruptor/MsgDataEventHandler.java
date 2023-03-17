package com.jswy.infrastructure.queue.disruptor;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jswy.domain.generic.demo.model.MsgData;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import lombok.Data;

/**
 * 构造EventHandler-消费者/'序列数据'的事件处理器: 作为事件的句柄或入口来执行相应业务服务
 * 
 * @author admin
 *
 */
public class MsgDataEventHandler implements EventHandler<MsgDataEvent>, WorkHandler<MsgDataEvent> {
	private Logger logger = LoggerFactory.getLogger(MsgDataEventHandler.class);

	/**
	 * 事件处理:拿到事件及数据后,调用Service服务进行处理
	 * 
	 * @throws Exception
	 */
	@Override
	public void onEvent(MsgDataEvent event) throws Exception {
		logger.info("队列服务/消费者(工作单元) : 开始 - 消费消息：{}", event);
		try {
			MsgData data = event.getValue();
			logger.info("队列服务/消费者(工作单元) : 消费消息 - 数据对象：{}", data);
			if (data != null) {
				String deviceInfo = data.getDeviceInfoStr();
				logger.info("队列服务/消费者(工作单元) : 消费消息 - 数据对象->设备信息:" + deviceInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("队列服务/消费者(工作单元) - 处理消息失败{}", e);
			throw new Exception("exception:" + e);
		}
		logger.info("队列服务/消费者(工作单元) : 结束! 消费消息{} 当前时间[{}]", event, new Date());
	}

	/**
	 * 事件驱动监听--消费者消费的主体
	 */
	@Override
	public void onEvent(MsgDataEvent event, long sequence, boolean endOfBatch) throws Exception {
		logger.info("队列服务/消费者[独立消费] : 接收消息{},序列{},是否批量结束{},Thread{}", event, sequence, endOfBatch,
				Thread.currentThread().getName());
		this.onEvent(event);
	}
}