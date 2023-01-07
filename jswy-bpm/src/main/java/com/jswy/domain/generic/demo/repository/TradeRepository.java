package com.jswy.domain.generic.demo.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.jswy.domain.generic.demo.event.TradeEvent;
import com.jswy.domain.generic.demo.model.TradeRecord;
import com.jswy.domain.generic.demo.model.TradeRecordId;
import com.jswy.domain.support.IRepository;

/**
 * 声明定制共享行为的接口，用 @NoRepositoryBean<br>
 * 因为要公用，必须通用，不能失去JPA默认方法，所以继承相关Repository类<br>
 * 基础设施接口放在领域层主要的目的是减少领域层对基础设施层的依赖 <br>
 * 接口的设计是不可暴露实现的技术细节，如不能将拼装的SQL作为参数
 * 
 * @author admin
 *
 */
@NoRepositoryBean
public interface TradeRepository extends IRepository<TradeRecord, TradeRecordId> {

	/**
	 * 保存
	 * 
	 * @param tradeRecord
	 * @return
	 */
	TradeRecord save(TradeRecord tradeRecord);

	/**
	 * 查询订单
	 * 
	 * @param tradeNumber
	 * @return
	 */
	TradeRecord findByTradeNumber(String tradeNumber);

	/**
	 * 发送MQ事件消息
	 * 
	 * @param tradeEvent
	 */
	void sendMQEvent(TradeEvent tradeEvent);

	/**
	 * 获取所有
	 * 
	 * @return
	 */
	List<TradeRecord> findAll();
}
