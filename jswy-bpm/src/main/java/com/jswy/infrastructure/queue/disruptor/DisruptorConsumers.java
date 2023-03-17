package com.jswy.infrastructure.queue.disruptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = { "com.portal.disruptor" })
//多实例几个消费者
public class DisruptorConsumers {

	/**
	 * smsParamEventHandler1
	 *
	 * @return SeriesDataEventHandler
	 */
	@Bean
	public MsgDataEventHandler smsParamEventHandler1() {
		return new MsgDataEventHandler();
	}

	/**
	 * smsParamEventHandler2
	 *
	 * @return SeriesDataEventHandler
	 */
	@Bean
	public MsgDataEventHandler smsParamEventHandler2() {
		return new MsgDataEventHandler();
	}

	/**
	 * smsParamEventHandler3
	 *
	 * @return SeriesDataEventHandler
	 */
	@Bean
	public MsgDataEventHandler smsParamEventHandler3() {
		return new MsgDataEventHandler();
	}

	/**
	 * smsParamEventHandler4
	 *
	 * @return SeriesDataEventHandler
	 */
	@Bean
	public MsgDataEventHandler smsParamEventHandler4() {
		return new MsgDataEventHandler();
	}

	/**
	 * smsParamEventHandler5
	 *
	 * @return SeriesDataEventHandler
	 */
	@Bean
	public MsgDataEventHandler smsParamEventHandler5() {
		return new MsgDataEventHandler();
	}
}