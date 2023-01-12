package com.jswy.domain.support;

import javax.persistence.EntityManager;

/**
 * 通用的资源库模板类：,可在各个业务场景子实现类中作为域属性调用来实现资源库
 * 
 * @author admin
 *
 * @param <T>
 * @param <ID>
 */
public class GenericRepository<T extends AggregateRoot<ID>, ID extends Identifier> {
	private final Class<T> aggregateRoot;
	private final EntityManager entityManager;

	public GenericRepository(Class<T> aggregateRoot, EntityManager entityManager) {
		super();
		this.aggregateRoot = aggregateRoot;
		this.entityManager = entityManager;
	}

	/**
	 * 查找对象
	 * 
	 * @param <T>
	 * @param primaryKey
	 * @return
	 * @return
	 */
	public T findById(ID primaryKey) {
		return entityManager.find(aggregateRoot, primaryKey);
	}

	/**
	 * 保存或更新
	 * 
	 * @param entity
	 */
	public void save(T entity) {
		if (entityManager.contains(entity)) {
			entityManager.merge(entity);
		} else {
			entityManager.persist(entity);
		}
	}

	/**
	 * 删除对象
	 * 
	 * @param entity
	 */
	public void delete(T entity) {
		if (entityManager.contains(entity)) {
			entityManager.remove(entity);
		}
	}
}
