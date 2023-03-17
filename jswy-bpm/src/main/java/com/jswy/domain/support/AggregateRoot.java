package com.jswy.domain.support;

import java.io.Serializable;

/**
 * 聚合根接口
 * 
 * @author admin
 *
 * @param <T>
 * @param <ID>
 */
public interface AggregateRoot<ID extends /*Identifier*/Serializable> extends Entity<ID> {
}