package com.jswy.infrastructure.queue.disruptor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.disruptor")
public class DisruptorProperties {

	/**
	 * 环形缓冲区的大小:队列长度，必须是2的幂
	 */
	private Integer ringBufferSize = 2 * 1024 * 1024;

	/**
	 * 是否支持多生产者
	 */
	private boolean isMultipleProducer = false;

	/**
	 * @return the ProducerType
	 */
	public ProducerType getProducerType() {
		if (isMultipleProducer) {
			return ProducerType.MULTI;
		}
		return ProducerType.SINGLE;
	}

	/**
	 * @return the ExceptionHandler
	 */
	public final ExceptionHandler getHandlerException() {
		return new DisruptorHandlerException();
	}

	/**
	 * @return the WaitStrategy
	 */
	@Bean
	public final WaitStrategy getWaitStrategy() {
		return new YieldingWaitStrategy();
	}

	/**
	 * @return the EventFactory
	 */
	@Bean
	public final EventFactory<MsgDataEvent> getEventFactory() {
		return new MsgDataEventFactory();
	}

	/**
	 * @return EventHandler
	 */
	@Bean
	public final MsgDataEventHandler getEventHandler() {
		return new MsgDataEventHandler();
	}

	/**
	 * @return the ringBufferSize
	 */
	@Bean
	public ThreadFactory getDefaultThreadFactory() {
		// treadFactory = new CustThreadFactory(defaultThread);
		return Executors.defaultThreadFactory();
	}

	/**
	 * @return the ringBufferSize
	 */
	@Bean
	public ThreadFactory getDaemonThreadFactory() {
		// treadFactory = new CustThreadFactory(defaultThread);
		return DaemonThreadFactory.INSTANCE;
	}

	/**
	 * @return the ringBufferSize
	 */
	public Integer getRingBufferSize() {
		return ringBufferSize;
	}

	/**
	 * @param ringBufferSize the ringBufferSize to set
	 */
	public void setRingBufferSize(Integer ringBufferSize) {
		this.ringBufferSize = ringBufferSize;
	}

	/**
	 * @return the isMultipleProducer
	 */
	public boolean isMultipleProducer() {
		return isMultipleProducer;
	}

	/**
	 * @param isMultipleProducer the isMultipleProducer to set
	 */
	public void setMultipleProducer(boolean isMultipleProducer) {
		this.isMultipleProducer = isMultipleProducer;
	}

	public DisruptorProperties() {
	}

	public DisruptorProperties(boolean isMultipleProducer) {
		super();
		this.isMultipleProducer = isMultipleProducer;
	}

	public DisruptorProperties(Integer ringBufferSize, boolean isMultipleProducer) {
		super();
		this.ringBufferSize = ringBufferSize;
		this.isMultipleProducer = isMultipleProducer;
	}

}
