package com.jswy.domain.generic.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.jswy.domain.generic.demo.model.Customer;
import com.jswy.domain.generic.demo.model.CustomerId;
import com.jswy.domain.support.AssociationResolver;
import com.jswy.domain.support.IRepository;

/**
 * Customers式一个标准的Spring Data仓储，我们可以轻松地通过其存储库显式地解决与其他聚合的关联<br>
 * 声明定制共享行为的接口，用 @NoRepositoryBean<br>
 * 因为要公用，必须通用，不能失去JPA默认方法，所以继承相关Repository类<br>
 * 基础设施接口放在领域层主要的目的是减少领域层对基础设施层的依赖 <br>
 * 接口的设计是不可暴露实现的技术细节，如不能将拼装的SQL作为参数
 * 
 * @author admin
 *
 */
@NoRepositoryBean
public interface CustomersRepository
		extends IRepository<Customer, CustomerId>, AssociationResolver<Customer, CustomerId> {

	/**
	 * 使用时自定义接口继承JpaRepository，传入泛型，第一个参数为要操作的实体类，第二个参数为该实体类的主键类型
	 * 
	 * @param id
	 * @return
	 */
	Customer findById(Long id);

	/**
	 * 在方法上标注@Query来指定本地查询
	 * 参数nativeQuery默认为false，nativeQuery=false时，value参数写的是JPQL，JPQL是用来操作model对象的
	 * 
	 * @param title
	 * @return
	 */
	@Query(value = "select s from Customer s where s.title like %?1")
	public List<Customer> findByTitle(String title);

	/**
	 * 如果要在以20为一页的结果中，获取第2页结果，则如下使用：
	 */
	Page<Customer> findAll(Pageable pageable);

	/**
	 * 设定是否排序
	 */
	List<Customer> findAll(Sort sort);

	/**
	 * 可以通过自定义的 JPQL 完成 UPDATE 和 DELETE 操作. 注意: JPQL 不支持使用 INSERT 在 @Query 注解中编写
	 * JPQL 语句, 但必须使用 @Modifying 进行修饰. 以通知 SpringData, 这是一个 UPDATE 或 DELETE 操作
	 * UPDATE 或 DELETE 操作需要使用事务, 此时需要定义 Service 层. 在 Service 层的方法上添加事务操作. 默认情况下,
	 * SpringData 的每个方法上有事务, 但都是一个只读事务. 他们不能完成修改操作!
	 * 
	 * @param id
	 * @param updateName
	 * @return
	 */
	@Modifying
	@Query("UPDATE Person p SET p.name = :name WHERE p.id < :id")
	int updatePersonById(@Param("id") Integer id, @Param("name") String updateName);
}
