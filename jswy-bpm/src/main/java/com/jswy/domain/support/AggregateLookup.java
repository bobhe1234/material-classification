package com.jswy.domain.support;

import java.io.Serializable;
import java.util.Optional;

/**
 * AggregateLookup.findById(…)本质上等同于CrudRepository.findById(…)这不是巧合。<br>
 * 
 * @author admin
 *
 * @param <T>
 * @param <ID>
 */
public interface AggregateLookup<T extends AggregateRoot<ID>, ID extends /*Identifier*/Serializable> {
	Optional<T> findById(ID id);
}