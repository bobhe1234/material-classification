package com.jswy.application.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.jswy.domain.generic.demo.event.TradeEvent;
import com.jswy.domain.generic.demo.repository.TradeRepository;

/**
 * 交易事件订阅<br>
 * 事件订阅是进程内多个领域操作协作解耦的一种实现方式，它也是进程内所有后续操作的接入口
 * 它与应用服务的组合操作用途不一样，组合是根据场景需求可增可减，但事件订阅后的操作是相对固化的，主要是满足逻辑的一致性要求
 * TransactionPhase.AFTER_COMMIT配置是在前一操作事务完成后再调用，从而减少后续操作对前操作的影响
 * 事件订阅可能会有多个消息主体，为了方便管理最好统一在一个类里处理 MQ消息发布一般放在事件订阅中
 * 
 * @author admin
 *
 */
@Component
public class TradeEventProcessor {

	@Autowired
	private TradeRepository tradeRepository;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, condition = "# tradeEvent.tradeStatus.name() == 'SUCCEED'")
	public void TradeSucceed(TradeEvent tradeEvent) {
		tradeRepository.sendMQEvent(tradeEvent);
	}
}