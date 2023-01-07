package com.jswy.infrastructure.persistent.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.jswy.domain.generic.demo.model.Customer;
import com.jswy.domain.generic.demo.model.CustomerId;

/**
 * 创建一个自定义的BaseDaoFactoryBean来代替默认的RepositoryFactoryBean。RepositoryFactoryBean负责返回一个RepositoryFactory，<br>
 * Spring Data Jpa
 * 将使用RepositoryFactory来创建Repository具体实现，这里我们用BaseDaoImpl代替SimpleJpaRepository作为Repository接口的实现。<br>
 * 这样我们就能够达到为所有Repository添加或修改自定义方法的目的
 * 
 * @author admin
 *
 * @param <R>
 * @param <T>
 * @param <ID>
 */
public class CustomerRepositoryFactoryBean<R extends Repository<T, ID>, T, ID>
		extends JpaRepositoryFactoryBean<R, T, ID> {

	private Class<Customer> domainClass;

	public CustomerRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@SuppressWarnings("rawtypes")
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		return new MyJpaRepositoryFactory(em);
	}

	private static class MyJpaRepositoryFactory<T, ID> extends JpaRepositoryFactory {

		private final EntityManager em;

		public MyJpaRepositoryFactory(EntityManager em) {
			super(em);
			this.em = em;
		}

		protected SimpleJpaRepository<Customer, CustomerId> getTargetRepository(RepositoryInformation information,
				EntityManager entityManager) {
			Class<Customer> domainClass = (Class<Customer>) information.getDomainType();
			if (Customer.class.isAssignableFrom(domainClass)) {
				return new CustomersRepositoryImpl(domainClass, entityManager);
			} else {
				return new SimpleJpaRepository<Customer, CustomerId>(domainClass, entityManager);
			}
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return metadata.getDomainType().isAssignableFrom(Customer.class) ? CustomersRepositoryImpl.class
					: SimpleJpaRepository.class;
		}
	}
}