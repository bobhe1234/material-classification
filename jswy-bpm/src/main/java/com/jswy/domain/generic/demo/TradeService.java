package com.jswy.domain.generic.demo;

import com.jswy.domain.generic.demo.model.TradeRecord;

/**
 * 先定义服务接口，接口的定义需要遵循现实业务的操作，切勿以程序逻辑或数据库逻辑来设计定义出增删改查<br>
 * 主要的思考方向是交易对象对外可提供哪些服务，这种服务的定义是粗粒度且高内聚的，切勿将某些具体代码实现层面的方法定义出来
 * 接口的输入输出参数尽量考虑以对象的形式，充分兼容各种场景变化
 * 关于前端需要的复杂查询方法可不在此定义，一般情况下查询并非是一种领域服务且没有数据变化，可单独处理
 * 领域服务的实现主要关注逻辑实现，切勿包含技术基础类代码，比如缓存实现，数据库实现，远程调用等<br>
 * Created on 2020/9/7 11:40 上午
 *
 * @author bobhe123 Description: 交易服务
 */
public interface TradeService {

	/**
	 * 充值
	 *
	 * @param tradeRecord
	 * @return
	 */
	TradeRecord recharge(TradeRecord tradeRecord);

	/**
	 * 消费
	 *
	 * @param tradeRecord
	 * @return
	 */
	TradeRecord consume(TradeRecord tradeRecord);
}
