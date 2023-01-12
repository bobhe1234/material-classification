package com.jswy.domain.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 自定义构建公用的Repository基类，默认继承了JPA、Paging等接口实现<br>
 * 1 Repository 提供了findBy + 属性 方法<br>
 * 2 CrudRepository 继承了Repository 提供了对数据的增删改查<br>
 * 3 PagingAndSortRepository 继承了CrudRepository
 * --提供了对数据的分页和排序，缺点是只能对所有的数据进行分页或者排序，不能做条件判断<br>
 * 4 JpaRepository： 继承了PagingAndSortRepository
 * --开发中经常使用的接口，主要继承了PagingAndSortRepository，对返回值类型做了适配<br>
 * 5 JpaSpecificationExecutor 提供多条件查询 DDD的资源库有两种实现方式：<br>
 * 1. 面向集合的资源库：标致动作add 2. 面向持久化的资源库：标致动作save，本设计采用该方式
 * 
 * @author admin
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean // <1>注释告诉Spring Data不要尝试直接实例化该接口
public interface IRepository<T extends AggregateRoot<ID>, ID extends Identifier> // <2>将存储库服务的实体限制为仅聚合根
		extends JpaRepository<T, ID>, // <3>扩展JpaRepository (本身继承PagingAndSortingRepository，扩展具备分页和排序的能力)
		JpaSpecificationExecutor<T> { // <4>个人更喜欢规范而不是查询方法,主要提供了多条件查询的支持，并且可以在查询中添加分页和排序
}
