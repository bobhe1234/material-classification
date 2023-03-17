//package com.jswy.infrastructure.impl;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.jpa.repository.support.JpaEntityInformation;
//
//import com.jswy.domain.generic.demo.model.Customer;
//import com.jswy.domain.generic.demo.model.CustomerId;
//import com.jswy.domain.generic.demo.repository.CustomersRepository;
//import com.jswy.domain.support.IRepositoryImpl;
//
///**
// * 默认继承Simple子实现
// * 
// * @author admin
// *
// */
//public class CustomersRepositoryImpl extends IRepositoryImpl<Customer, CustomerId> implements CustomersRepository {
//
//	// 通过构造方法注入 EntityManager 实例
//	@PersistenceContext
//	private final EntityManager entityManager;
//
//	/**
//	 * 继承构造1
//	 * 
//	 * @param entityInformation
//	 * @param entityManager
//	 */
//	public CustomersRepositoryImpl(JpaEntityInformation<Customer, ?> entityInformation, EntityManager entityManager) {
//		super(entityInformation, entityManager);
//		this.entityManager = entityManager;
//	}
//
//	/**
//	 * 继承构造2
//	 * 
//	 * @param domainClass
//	 * @param entityManager
//	 */
//	public CustomersRepositoryImpl(Class<Customer> domainClass, EntityManager entityManager) {
//		super(domainClass, entityManager);
//		this.entityManager = entityManager;
//	}
//
//	/**
//	 * 构建复杂查询(根据查询规格得到结果)
//	 */
//	public List<Customer> find(Specification<Customer> specification, Pageable pageable) {
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//		// use specification.getType() to create a Root<T> instance
//		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Customer.class);
//		Root<Customer> root = criteriaQuery.from(Customer.class);
//
//		// get predicate from specification
//		Predicate predicate = specification.toPredicate(root, criteriaQuery, criteriaBuilder);
//
//		// set predicate and execute query
//		criteriaQuery.where(predicate);
//		return entityManager.createQuery(criteriaQuery).getResultList();
//	}
//
//	@Override
//	public List<Customer> findByName(String title) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Customer> updateNameById(Integer id, String updateName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
