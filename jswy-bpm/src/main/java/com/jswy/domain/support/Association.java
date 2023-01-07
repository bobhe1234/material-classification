package com.jswy.domain.support;

/**
 * Association接口基本上是指向相关集合的标识符的一种间接方式，该标识符仅在模型内发挥表达作用<br>
 * 个人理解表示为，两个对象之间的Link关联引用关系
 * 
 * @author admin
 *
 * @param <T>
 * @param <ID>
 */
public interface Association<T extends AggregateRoot<ID>, ID extends Identifier> extends Identifiable<ID> {
}