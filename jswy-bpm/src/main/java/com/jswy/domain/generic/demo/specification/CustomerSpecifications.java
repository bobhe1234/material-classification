package com.jswy.domain.generic.demo.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.jswy.domain.generic.demo.model.Customer;

/**
 * 客制化Customer的Specification实现
 * 
 * @author admin
 *
 */
public class CustomerSpecifications implements Specification<Customer> {
	private static final long serialVersionUID = 1L;
	Map<String, Object> paramMap;

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public CustomerSpecifications(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 优雅:面向对象的方式(面对扩展开放且面对闭包关闭)
	 */
	@Override
	public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (String entityAttrName : paramMap.keySet()) {
			if (null != entityAttrName && !"".equals(entityAttrName)) {
				predicates.add(criteriaBuilder.equal(root.get(entityAttrName), paramMap.get(entityAttrName)));
			}
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
