package com.jswy.infrastructure.persistent.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.jswy.domain.generic.demo.model.Customer;
import com.jswy.domain.generic.demo.model.CustomerId;
import com.jswy.domain.generic.demo.repository.CustomersRepository;

/**
 * XXXRepositoryImpl 与XXXRepository前面的名字必须相同，后面的也需要按照规则写<br>
 * 若将XXXRepositoryImpl与XXXRepository接口放在同意包下，XXXRepositoryImpl不需要添加@Repository注解，<br>
 * 但是当XXXRepositoryImpl与XXXRepository接口不在同一包下，需要在在XXXRepositoryImpl类上加@Repository注解进行修饰
 * 
 * @author admin
 *
 */
@Repository
public class CustomersRepositoryImpl extends SimpleJpaRepository<Customer, CustomerId> implements CustomersRepository {

	@PersistenceContext
	private final EntityManager entityManager; // 获取entityManger

	public CustomersRepositoryImpl(JpaEntityInformation<Customer, CustomerId> entityInformation,
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	/**
	 * BaseRepositoryImpl类继承SimpleJpaRepository类，并实现父类构造器
	 * 
	 * @param domainClass
	 * @param entityManager
	 */
	public CustomersRepositoryImpl(Class<Customer> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}

	/**
	 * Repository其具体实现上层是无感知的，若是之后咱们要切换为redis、mysql只须要修改这一层便可。
	 */
	Map<String, Customer> customerMap = new ConcurrentHashMap<>();

	@Override
	public Customer ofId(String id) {
		return customerMap.get(id);
	}

	@Override
	public void add(Customer aggregateRoot) {
		if (!(aggregateRoot instanceof Customer)) {
			return;
		}
		Customer customer = (Customer) aggregateRoot;
		customerMap.put(customer.getId().toString(), customer);
	}

	@Override
	public void remove(String id) {
		customerMap.remove(id);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<Customer> findByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updatePersonById(Integer id, String updateName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer find(CustomerId id) {
		return entityManager.find(Customer.class, id);
	}

	@Override
	public Customer findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
