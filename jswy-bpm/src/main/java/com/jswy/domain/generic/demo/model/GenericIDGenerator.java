package com.jswy.domain.generic.demo.model;

import java.io.Serializable;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import com.jswy.domain.support.Identifiable;
import com.jswy.infrastructure.util.SnowFlakeDemo;

/**
 * 自定义ID生成器:<br>
 * 1.测试的ID是Long类型所以这里继承的是IdentityGenerator类，<br>
 * 2.如果ID为String类型的话应该继承 UUIDGenerator 或者 UUIDGenerator
 * 
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2018-12-08 15:42
 */
public class GenericIDGenerator extends IdentityGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws MappingException {
		if (object instanceof Identifiable) {
			Identifiable identifiable = (Identifiable) object;
			Serializable id = identifiable.getId();
			if (id != null) {
				return id;
			}
		}
		return super.generate(session, object);
	}

	public Serializable generate(SessionImplementor session, Object obj) {
		if (obj instanceof Identifiable) {
			Identifiable identifiable = (Identifiable) obj;
			Serializable id = identifiable.getId();
			if (id != null) {
				return id;
			}
		}
		return super.generate(session, obj);
	}

	public Serializable generate1(SharedSessionContractImplementor session, Object object) throws MappingException {
		SnowFlakeDemo snowFlakeDemo = new SnowFlakeDemo();
		Object id = snowFlakeDemo.snowflakeId();//SnowflakeIdHelper.getId();
		if (id != null) {
			return (Serializable) id;
		}
		return super.generate(session, object);
	}
}