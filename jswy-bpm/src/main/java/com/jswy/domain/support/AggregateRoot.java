package com.jswy.domain.support;

/**
 * 聚合根接口
 * 
 * @author admin
 *
 * @param <T>
 * @param <ID>
 */
public interface AggregateRoot<ID extends Identifier> extends Entity<ID> {
}