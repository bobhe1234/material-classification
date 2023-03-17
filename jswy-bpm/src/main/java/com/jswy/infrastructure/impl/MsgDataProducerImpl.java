package com.jswy.infrastructure.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jswy.application.service.MsgDataProducer;
import com.jswy.domain.generic.demo.model.MsgData;
import com.jswy.infrastructure.queue.disruptor.MsgDataEvent;
import com.lmax.disruptor.RingBuffer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
@Service
public class MsgDataProducerImpl implements MsgDataProducer {

	private Logger logger = LoggerFactory.getLogger(MsgDataProducerImpl.class);
	@Autowired
	private RingBuffer<MsgDataEvent> ringBuffer;

	public MsgDataProducerImpl() {
	}

	public MsgDataProducerImpl(RingBuffer<MsgDataEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	/**
	 * 处理数据
	 */
	@Override
	public void processData(String message) {
		logger.info("队列服务/生产者 - 接受外部消息: {}", message);

		MsgData msgData = new MsgData(message);
		/**
		 * 发布消息的两种方式: publishEvent/publish
		 */
		// ringBuffer.publishEvent((event, sequence, data) -> event.setValue(msgData),
		// message);

		/**
		 * 预定下一个Event槽可以写入的位置序号<br>
		 * 根据序号获取事件，然后修改事件数据，然后发布 event<br>
		 * 
		 */
		long sequence = ringBuffer.next();
		try {
			MsgDataEvent data = ringBuffer.get(sequence);
			data.setValue(msgData);// 给Event填充数据
			logger.info("队列服务/生产者 - 往环形缓冲队列填充消息：{}", msgData);
		} catch (Exception e) {
			logger.error("队列服务/生产者 - 异常信息 for : e = {},{}", e, e.getMessage());
		} finally {
			// 发布Event，激活观察者去消费，将sequence传递给改消费者
			// 注意pulish放finally里确保每次发布成功
			// 如果某个请求的sequence未被提交将会堵塞后续的发布操作或者其他的producer
			ringBuffer.publish(sequence);
		}

		logger.info("队列服务/生产者 - 完成发布事件: {}", msgData);
	}
}