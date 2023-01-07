package com.jswy.domain.generic.demo.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.DomainEvents;

import com.jswy.domain.generic.demo.enums.TradeStatus;
import com.jswy.domain.generic.demo.enums.TradeType;
import com.jswy.domain.generic.demo.event.TradeEvent;
import com.jswy.domain.support.AggregateRoot;

/**
 * 交易对象： 从钱包交易例子的系统设计中，钱包的任何操作如：充值、消息等都是通过交易对象驱动钱包余额的变化<br>
 * 交易对象和钱包对象均为实体对象且组成聚合关系，交易对象是钱包交易业务模型的聚合根，代表聚合向外提供调用服务
 * 
 * @author admin
 *
 */
public class TradeRecord implements AggregateRoot<TradeRecordId> {
	TradeRecordId id;
	CustomerAssociation customer_link;

	/**
	 * 交易ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "traderecord_id")
	@GenericGenerator(name = "traderecord_id", strategy = "com.muyuer.springdemo.core.TradeRecordIDGenerator")
	@Column(name = "traderecord_id", unique = true)
	private String tradeId;

	/**
	 * 交易号
	 */
	@Column(unique = true)
	private String tradeNumber;
	/**
	 * 交易金额
	 */
	private BigDecimal tradeAmount;
	/**
	 * 交易类型
	 */
	@Enumerated(EnumType.STRING)
	private TradeType tradeType;
	/**
	 * 交易余额
	 */
	private BigDecimal balance;
	/**
	 * 钱包
	 */
	@ManyToOne
	private Customer customer;

	/**
	 * 交易状态
	 */
	@Enumerated(EnumType.STRING)
	private TradeStatus tradeStatus;

	/**
	 * domainEvents()为领域事件发布的一种实现，<br>
	 * 作用是交易对象任何的数据操作都将触发事件的发布，再配合事件订阅实现事件驱动设计模型，当然也可以有别的实现方式
	 * 
	 * @return
	 */
	@DomainEvents
	public List<Object> domainEvents() {
		return Collections.singletonList(new TradeEvent(this));
	}

	@Override
	public TradeRecordId getId() {
		// TODO Auto-generated method stub
		return null;
	}
}