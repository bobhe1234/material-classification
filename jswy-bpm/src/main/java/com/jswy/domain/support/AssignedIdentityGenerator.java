package com.jswy.domain.support;

import java.io.Serializable;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

/**
 * 自定义identifier，如下的identifier生成策略
 * 
 * @author admin
 *
 */
public class AssignedIdentityGenerator extends IdentityGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor s, Object obj) {
		if (obj instanceof Identifiable) {
			Identifiable identifiable = (Identifiable) obj;
			Serializable id = identifiable.getId();
			if (id != null) {
				return id;
			}
		}
		return super.generate(s, obj);
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
}