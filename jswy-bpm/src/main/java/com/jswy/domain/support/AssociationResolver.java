package com.jswy.domain.support;

import java.util.Optional;

/**
 * 
 * AssociationResolver公开使用关联的公开标识符来解析Associationvia findById(…)方法的方法。这样可以使<br>
 * CustomerRepository看起来像这样，并且可以直接使用，而无需进行任何其他更改。
 * 
 * @author admin
 *
 * @param <T>
 * @param <ID>
 */
public interface AssociationResolver<T extends AggregateRoot<ID>, ID extends Identifier>
		extends AggregateLookup<T, ID> {

	default Optional<T> resolve(Association<T, ID> association) {
		return findById(association.getId());
	}

	default T resolveRequired(Association<T, ID> association) {
		return resolve(association).orElseThrow(
				() -> new IllegalArgumentException(String.format("Could not resolve association %s!", association)));
	}
}