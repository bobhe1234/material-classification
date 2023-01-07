package com.jswy.application.demo;

import org.springframework.stereotype.Component;

import com.jswy.domain.generic.demo.event.TradeEvent;

/**
 * 交易消息订阅<br>
 * 消息订阅是多个微服务间协作解耦的异步实现方式 消息体尽量以统一的对象包装进行传递，降低对象异构带来的处理难度
 * 
 * @author admin
 *
 */
@Component
public class TradeMQSubscription {

//	@RabbitListener(queues = "ddd-trade-succeed")
	public void receiveTradeMessage(TradeEvent tradeEvent) {
		System.err.println("========MQ Receiver============");
		System.err.println(tradeEvent);
	}
}
