package com.jswy.domain.generic.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jswy.domain.generic.demo.model.User;

/**
 * User没有继承自定义的通用IRepository<AggregateRoot,ID>聚合根实体设计<br>
 * 基于SpringMVC框架开发web应用--数据操作层的实现
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
//	/**
//	 * 根据要求查询一个对象(有关搜索条件全让Specification来定义,Specification隐藏了具体关于ORM对于的实现/jpa的话，基本就是把Specification转化为Predicate可以让EntityManager用来查询)
//	 * 
//	 * @param tradeRecordId
//	 * @return
//	 */
//	Customer find(String number);
//
//	/**
//	 * 构建复杂查询:获取满足要求的对象列表(有关搜索条件全让Specification来定义)
//	 * 
//	 * @param pageable
//	 * 
//	 * @return
//	 */
//	List<Customer> find(Specification<Customer> spec, Pageable pageable);
//
//	/**
//	 * 保存(saveOrUpdate，DDD的基于持久化资源库实现方式;add&update则是ORM的实现方式)
//	 * 
//	 * @param <S>
//	 * 
//	 * @param tradeRecord
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	Customer save(Customer entity);
//
//	/**
//	 * 删除
//	 * 
//	 * @param tradeRecord
//	 * @return
//	 */
//	void delete(Customer entity);
}