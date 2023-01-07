package com.jswy.domain.generic.demo.model;

import java.io.Serializable;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

/**
 * 自定义ID生成器
 * 
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2018-12-08 15:42
 */
public class TradeRecordIDGenerator extends IdentityGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
		Object id = new TradeRecord().getId();
		if (id != null) {
			return (Serializable) id;
		}
		return super.generate(session, object);
	}
}