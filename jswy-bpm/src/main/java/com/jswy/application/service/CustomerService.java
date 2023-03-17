package com.jswy.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jswy.domain.generic.demo.model.Customer;
import com.jswy.domain.generic.demo.model.CustomerId;
import com.jswy.domain.generic.demo.repository.CustomersRepository;
import com.jswy.domain.generic.demo.specification.CustomerSpecifications;
import com.jswy.domain.generic.demo.specification.GenericSpecifications;

/**
 * 事务的控制在领域层
 * 
 * @author admin
 *
 */
@Service
@Persistent
public class CustomerService {

	@PersistenceContext
	EntityManager entityManager;
	private static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
	/**
	 * 采用默认继承Spring Data JPA的子实现方式
	 */
	@Autowired
	private CustomersRepository cusomterRepo;

	/**
	 * 新增数据(JPA实现)
	 * 
	 * @param entity
	 */
	@Transactional
	public void saveOrUpdate(Customer input_Customer) {
		LOGGER.debug("saveOrUpdate Beg->input_Customer:" + input_Customer);
		Optional<Customer> oldObj = cusomterRepo.findById(input_Customer.getId());

		Customer temp_Customer = oldObj.get();
		temp_Customer.setName(input_Customer.getName());
		temp_Customer.setPhone(input_Customer.getPhone());
		temp_Customer.setEmail(input_Customer.getEmail());
		temp_Customer.setDescription(input_Customer.getDescription());
		if (!cusomterRepo.existsById(input_Customer.getId())) {
			CustomerId customer_id = new CustomerId(input_Customer.getId());
			temp_Customer.setCustomer_id(customer_id);
		}
		LOGGER.debug("saveOrUpdate End->Customer:" + temp_Customer);
		// saveAndFlush存储内存且同步到数据库(save高并发下因延迟导致数据误差)
		cusomterRepo.saveAndFlush(temp_Customer);
	}

	/**
	 * 根据id，更新Name(自写SQL,JPA实现)
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional
	public Optional<Customer> updateNameById(Integer id, String updateName) {
		LOGGER.debug("modify Beg->id:" + id + ",updateName:" + updateName);
		cusomterRepo.updateNameById(id, updateName);
		Optional<Customer> opt = cusomterRepo.findById(id);
		LOGGER.debug("modify End->opt:" + opt);
		return opt;
	}

	/**
	 * 删除数据(JPA实现)
	 * 
	 * @param entity
	 */
	@Transactional
	public void delete(Customer entity) {
		cusomterRepo.delete(entity);
	}

	/**
	 * 根据Name，查询数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<Customer> findByName(String name) {
		LOGGER.debug("findByName Beg->name:" + name);
		// ----------- method1[已验证] entityManager从Hibernate的JPA API实现--------------
//		try {
//			String jpql = "select o from Customer o where o.name = :name";
//			results = entityManager.createQuery(jpql, Customer.class).setParameter("name", name).getSingleResult();
//		批量修改大量Entity对象时，这种对各个Entity对象的自动同步将会造成数据库性能压力。
//		为此，可以使managed/persistent状态的Entity对象转变为detached状态的Entity对象，从而避免自动同步
//		dtos..tolist(entityList);//提前转为dto,再dettach
//		entityManager.detach(entity);
//		} catch (RuntimeException re) {
//			throw re;
//		}

		// ----------- method2[已验证] 根据方法命名规则执行操作--------------
//		results = cusomterRepository.findByName(name);

		// ----------- method3[已验证] 根据对象名称,查找所有对象列表(定制规格,JPA复杂查询)--------------
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);// 实体Customer属性name,值
		CustomerSpecifications spec = new CustomerSpecifications(paramMap);
		List<Customer> results = cusomterRepo.findAll(spec);
		LOGGER.debug("findByName End->results:" + results);
		return results;
	}

	/**
	 * 输入分页条件,查找所有对象列表(定制规格,JPA复杂分页查询)
	 * 
	 * @return
	 * @throws Exception
	 */
	public Page<Customer> listByPage(int page, int size) throws Exception {
		LOGGER.debug("listByPage Beg->page:" + page + ",size:" + size);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Specification<Customer> spec = GenericSpecifications.specificationByMap(paramMap);
		Pageable pagable = GenericSpecifications.ofPage(page, size);
		Page<Customer> page_o = cusomterRepo.findAll(spec, pagable);
		LOGGER.debug("listByPage End->page_object:" + page_o);
		return page_o;
	}
}
