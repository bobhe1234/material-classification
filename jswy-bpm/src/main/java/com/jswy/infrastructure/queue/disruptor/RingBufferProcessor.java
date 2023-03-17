package com.jswy.infrastructure.queue.disruptor;

import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * Disuptor为何能称之为高性能的无锁队列框架呢？<br>
 * 1 缓存行填充，避免缓存频繁失效。【java8中也引入@sun.misc.Contended注解来避免伪共享】<br>
 * 2 无锁竞争：通过CAS 【二阶段提交】<br>
 * 3 环形数组：数据都是覆盖，避免GC<br>
 * 4 底层更多的使用位运算来提升效率<br>
 * 
 * @return
 */
@Component
@Configuration
public class RingBufferProcessor {

	private Logger logger = LoggerFactory.getLogger(RingBufferProcessor.class);
	private final static DisruptorProperties properties;
	static {
		properties = new DisruptorProperties();
	}

	/**
	 * 
	 * 注册Disruptor的消息Bean:获取ringbuffer环，用于接取生产者生产的事件;<br>
	 * 1 事件处理的线程池，Disruptor通过ExecutorSerivce提供的线程来触发consumer的事件处理<br>
	 * 2 指定事件工厂<br>
	 * 3 指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率<br>
	 * 4 单线程模式，获取额外的性能<br>
	 * 5 设置事件业务处理器---消费者<br>
	 * 6 设置事件---自定义异常<br>
	 * 
	 * @param config
	 * @param factory
	 * @param consumerHandler
	 */
	@Bean("MsgDataEvent")
	public RingBuffer<MsgDataEvent> msgDataEventRingBuffer() {
		long beg = System.currentTimeMillis();
		int ringBufferSize = properties.getRingBufferSize();
		MsgDataEventHandler handler = properties.getEventHandler();
		ThreadFactory service = properties.getDefaultThreadFactory();
		EventFactory<MsgDataEvent> factory = properties.getEventFactory();
		ExceptionHandler<MsgDataEvent> exception = properties.getHandlerException();

		/**
		 * 
		 * Disruptor 创建三个参数介绍<br>
		 * 1、生成传递数据的工厂<br>
		 * 2、缓冲区的大小。它必须是2的幂<br>
		 * 3、事件线程工厂方式 :Executors.defaultThreadFactory()/DaemonThreadFactory.INSTANCE<br>
		 */
		Disruptor<MsgDataEvent> disruptor = new Disruptor<MsgDataEvent>(factory, ringBufferSize, service);
		disruptor.setDefaultExceptionHandler(exception);
		/**
		 * 注: 从企业解决方案角度考虑,选择1同时加一个history或backup消费,完整的将原始数据记录,以便回头重置<br>
		 * disruptor.handleEventsWith(EventHandler...
		 * handlers)：将多个EventHandler的实现类传入，封装成一个com.lmax.disruptor.dsl.EventHandlerGroup<T>。<br>
		 * 每个消费者都会对同一条消息进行独立消费，各个消费者之间不存在竞争。<br>
		 * 事件的处理类是BatchEventProcessor + EventProcessorInfo。<br>
		 * <br>
		 * disruptor.handleEventsWithWorkerPool(WorkHandler...
		 * handlers)：将多个WorkHandler的实现类传入，封装成一个EventHandlerGroup<T>。对于同一条消息不重复消费；<br>
		 * 如果 c0 消费了消息 m，则 c1 不再消费消息 m。<br>
		 * 事件信息的处理类是com.lmax.disruptor.WorkerPool（N个WorkProcessor）+ WorkerPoolInfo<br>
		 */
		disruptor.handleEventsWith(handler);
		disruptor.start();
		
		long end = System.currentTimeMillis();
		logger.info("队列服务/初始化 - 启动线程 耗时[{}] min", (end - beg) / 60000);
		return disruptor.getRingBuffer();
	}
}