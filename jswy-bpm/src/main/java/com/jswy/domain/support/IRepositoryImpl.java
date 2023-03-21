package com.jswy.domain.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * 默认继承Simple子实现,IRepositoryImpl类为其他接口动态代理的实现基类
 * 
 * @author admin
 *
 */
public class IRepositoryImpl<T /* extends AggregateRoot<ID> */, ID extends /*Identifier*/Serializable> extends SimpleJpaRepository<T, ID>
		implements IRepository<T, ID> {

	// 通过构造方法注入 EntityManager 实例
	private final EntityManager entityManager;

	/**
	 * 继承构造2
	 * 
	 * @param domainClass
	 * @param entityManager
	 */
	public IRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}

	/**
	 * 继承构造1
	 * 
	 * @param entityInformation
	 * @param entityManager
	 */
	public IRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

}
