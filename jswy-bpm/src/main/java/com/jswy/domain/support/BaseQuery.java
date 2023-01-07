package com.jswy.domain.support;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public abstract class BaseQuery {

	/**
	 * 当前页（从1开始）
	 */
	private int currentPage = 1;
	/**
	 * 每页条数（默认10条）
	 */
	private int pageSize = 10;

	/**
	 * 排序方式：ASC（默认：升序）/DESC
	 */
	private String sortType = "ASC";
	/**
	 * 排序字段
	 */
	private List<String> orderBy;

	public abstract Specification createSpecification();// 拿到排序规则的数据

	/**
	 * 
	 * @return
	 */
	public Sort createSort() {
		Sort sort = null;
		if (orderBy != null && !orderBy.isEmpty()) {
			Sort.Direction type = "ASC".equals(sortType.toUpperCase()) ? Sort.Direction.ASC : Sort.Direction.DESC;
			sort = Sort.by(type);
		}
		return sort;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public List<String> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(List<String> orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 专用：前台传从1开始，后台分页索引index下表从0开始
	 * 
	 * @return
	 */
	public int getJpaPage() {
		return currentPage - 1;
	}

}
