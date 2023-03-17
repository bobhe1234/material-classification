package com.jswy.domain.generic.demo.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.jswy.domain.generic.demo.model.User;

/**
 * 客制化Customer的Specification实现:[指定sex和age范围]复杂查询的规格条件
 * 
 * @author admin
 *
 */
public class UserSpecifications implements Specification<User> {
	private static final long serialVersionUID = 1L;
	private String sex;
	private int ageBegin;
	private int ageEnd;

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	public UserSpecifications() {
	}

	/**
	 * @return the ageBegin
	 */
	public int getAgeBegin() {
		return ageBegin;
	}

	/**
	 * @param ageBegin the ageBegin to set
	 */
	public void setAgeBegin(int ageBegin) {
		this.ageBegin = ageBegin;
	}

	/**
	 * @return the ageEnd
	 */
	public int getAgeEnd() {
		return ageEnd;
	}

	/**
	 * @param ageEnd the ageEnd to set
	 */
	public void setAgeEnd(int ageEnd) {
		this.ageEnd = ageEnd;
	}

	public UserSpecifications(String sex, int ageBegin, int ageEnd) {
		super();
		this.sex = sex;
		this.ageBegin = ageBegin;
		this.ageEnd = ageEnd;
	}

	/**
	 * 优雅:面向对象的方式(面对扩展开放且面对闭包关闭)
	 */
	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (!StringUtils.isEmpty(sex)) { // 性别
			predicates.add(criteriaBuilder.equal(root.get("sex"), sex));
		}
		if (ageBegin != 0) { // 年龄大于ageBegin
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("age"), ageBegin));
		}
		if (ageEnd != 0) { // 年龄小于ageEnd
			predicates.add(criteriaBuilder.lessThan(root.get("age"), ageEnd));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
