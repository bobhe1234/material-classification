package com.jswy.domain.generic.demo.repository;

import java.util.List;

import com.jswy.domain.generic.demo.model.TradeRecord;
import com.jswy.domain.generic.demo.model.TradeRecordId;

/**
 * 
 * @author admin
 *
 */
public interface TradeRepository {

	/**
	 * 根据Id查询对象实例
	 * 
	 * @param tradeRecord
	 */
	TradeRecord instanceOf(TradeRecordId tradeRecordId);

	/**
	 * 根据Id查询对象
	 * 
	 * @param tradeRecordId
	 * @return
	 */
	TradeRecord findById(TradeRecordId tradeRecordId);

	/**
	 * 保存(saveOrUpdate，DDD的基于持久化资源库实现方式)
	 * 
	 * @param tradeRecord
	 * @return
	 */
	void save(TradeRecord tradeRecord);

	/**
	 * 删除
	 * 
	 * @param tradeRecord
	 * @return
	 */
	void delete(TradeRecord tradeRecord);

	/**
	 * 获取所有对象列表
	 * 
	 * @return
	 */
	List<TradeRecord> findAll();

	/**
	 * 根据Name和Number查询对象
	 * 
	 * @param name
	 * @param qq
	 * @return
	 */
	TradeRecord findByTradeNumber(String tradeNumber);
}
