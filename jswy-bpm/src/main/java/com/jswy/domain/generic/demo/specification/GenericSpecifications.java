package com.jswy.domain.generic.demo.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * 通用统一的泛型的Specification实现
 * 
 * @author admin
 *
 */
public class GenericSpecifications {

	/**
	 * 自定义实现方式,没那么优雅
	 * 
	 * @param <T>
	 * @param paramMap 实体属性名/属性值
	 * @return
	 */
	public static <T> Specification<T> specificationByMap(Map<String, Object> paramMap) {
		return new Specification<T>() {
			private static final long serialVersionUID = 4585706739300972664L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (String entityAttrName : paramMap.keySet()) {
					if (null != entityAttrName && !"".equals(entityAttrName)) {
						predicates.add(builder.equal(root.get(entityAttrName), paramMap.get(entityAttrName)));
					}
				}
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	/**
	 * 统一校验分页参数
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static Pageable ofPage(int page, int size) throws Exception {
		if (page == 0 || size == 0) {
			throw new Exception("The page or size is empty, please input the value!");
		}
		return PageRequest.of(page, size);
	}

}
