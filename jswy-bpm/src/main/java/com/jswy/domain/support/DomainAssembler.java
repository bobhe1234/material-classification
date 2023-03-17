package com.jswy.domain.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.util.Assert;

import cn.hutool.core.lang.UUID;

/**
 * DTO和领域模型转换处理,转换内部逻辑抽象,子类进行个性化重写(可支持柔性BOM定制/需求生产?)
 * 
 * @author admin
 *
 * @param <T>
 * @param <D>
 */
public abstract class DomainAssembler<T extends Domain, D extends DExtensible> {

	/**
	 * DTO转换为数据模型接口
	 * 
	 * @param dto
	 * @return
	 */
	protected abstract T toEntity(D dto);

	/**
	 * 更新逻辑内部扩展接口
	 * 
	 * @param entity
	 * @param dto
	 * @return
	 */
	protected boolean updateInternal(final T entity, final D dto) {
		return false;
	}

	/**
	 * 扩展属性更新逻辑
	 * 
	 * @param entity
	 * @param extended
	 * @return
	 */
	protected boolean updateExtended(T entity, Map<String, String> extended) {
		Map<String, String> oldMap = entity.getExtended();
		Map<String, String> newValues = extended == null ? new HashMap<>() : extended;
		if (oldMap == null || !oldMap.equals(newValues)) {
			Optional<Map<String, String>> newExtended = Optional.of(newValues);
			newExtended.ifPresent(entity::setExtended);// 如果存在
			return newExtended.isPresent();
		}
		return true;
	}

	/**
	 * 创建
	 * 
	 * @param dto
	 * @return
	 */
	public T create(final D dto) {
		Assert.notNull(dto, "dto is null!");
		T entity = toEntity(dto);
		entity.setId(UUID.randomUUID().getMostSignificantBits());
		update(entity, dto);
		return entity;
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 * @param dto
	 * @return
	 */
	public boolean update(final T entity, final D dto) {
		Assert.notNull(entity, "entity is null!");
		Assert.notNull(dto, "desc is null!");
		boolean extended = updateExtended(entity, dto.getExtended());
		boolean internal = updateInternal(entity, dto);
		return extended || internal;
	}
}
