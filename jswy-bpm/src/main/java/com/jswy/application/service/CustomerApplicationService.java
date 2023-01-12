package com.jswy.application.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.jswy.domain.generic.demo.model.Customer;
import com.jswy.domain.generic.demo.repository.CustomersRepository;

/**
 * 事务的控制在领域层
 * 
 * @author admin
 *
 */
public class CustomerApplicationService {

	@PersistenceContext(unitName = "foshanshop")
	EntityManager entityManager;
	CustomersRepository cusomterRepo;

	/**
	 * 新增数据
	 * 
	 * @param entity
	 */
	public void save(Customer entity) {
		EntityTransaction trx = entityManager.getTransaction();
		try {
			trx.begin();
			cusomterRepo.save(entity);
			trx.commit();
		} catch (RuntimeException re) {
			trx.rollback();
			throw re;
		}
	}

	/**
	 * 根据Name，查询数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<Customer> findByName(Customer entity) {
		List<Customer> results = null;
		EntityTransaction trx = entityManager.getTransaction();
		try {
			trx.begin();
			String jpql = "select o from Customer o where o.name = :name";
			results = entityManager.createQuery(jpql, Customer.class).setParameter("name", entity.getName())
					.getResultList();
			trx.commit();
			entityManager.close();
		} catch (RuntimeException re) {
			trx.rollback();
			throw re;
		}
		return results;
	}
}
