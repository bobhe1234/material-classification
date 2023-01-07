package com.jswy.application.demo;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jswy.domain.generic.demo.TradeService;
import com.jswy.domain.generic.demo.model.TradeRecord;

/**
 * 交易服务
 * 
 * @author admin
 *
 */
@Component
public class TradeManager {

	private final TradeService tradeService;

	public TradeManager(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	// 充值
	@Transactional(rollbackFor = Exception.class)
	public TradeRecord recharge(TradeRecord tradeRecord) {
		return tradeService.recharge(tradeRecord);
	}

	// 消费
	@Transactional(rollbackFor = Exception.class)
	public TradeRecord consume(TradeRecord tradeRecord) {
		return tradeService.consume(tradeRecord);
	}
}