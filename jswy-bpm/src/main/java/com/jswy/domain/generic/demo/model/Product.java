package com.jswy.domain.generic.demo.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jswy.domain.support.AggregateRoot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "t_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product implements AggregateRoot<ProductId> {
	/**
	 * JPA提供的四种标准用法为TABLE,SEQUENCE,IDENTITY,AUTO<br>
	 * 1.​IDENTITY:主键由数据库自动生成（主要是自动增长型）,Mysql支持
	 * 2.​SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列，Oracle支持<br>
	 * 3.AUTO：主键由程序控制<br>
	 * 4.​TABLE：使用一个特定的数据库表格来保存主键<br>
	 * IDENTITY生成策略。 就是ORM框架不负责生成ID，而是由数据库创建表时，将主键列，制定成自增（auto
	 * increment）。应用程序向数据库插入数据时，无需指定该列的值
	 */
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_no", length = 32, nullable = false, unique = true)
	private String productNo;

	@Column(name = "name", length = 64, nullable = false)
	private String name;

	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "category_id", nullable = false)
	private Integer categoryId;

	@Column(name = "product_status", nullable = false)
	private Integer productStatus;

	@Column(name = "remark", length = 256)
	private String remark;

	@Column(name = "allow_across_category", nullable = false)
	private Boolean allowAcrossCategory;

	private Product(Long id, String productNo, String name, BigDecimal price, Integer categoryId, Integer productStatus,
			String remark, Boolean allowAcrossCategory) {
		this.id = id;
		this.productNo = productNo;
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
		this.productStatus = productStatus;
		this.remark = remark;
		this.allowAcrossCategory = allowAcrossCategory;
	}

	public static Product of(Long id, String productNo, String name, BigDecimal price, Integer categoryId,
			Integer productStatus, String remark, Boolean allowAcrossCategory) {
		return new Product(id, productNo, name, price, categoryId, productStatus, remark, allowAcrossCategory);
	}

	public static Product of(String productNo, String name, BigDecimal price, Integer categoryId, Integer productStatus,
			String remark, Boolean allowAcrossCategory) {
		return new Product(null, productNo, name, price, categoryId, productStatus, remark, allowAcrossCategory);
	}

	/**
	 * productNo是唯一的
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Product product = (Product) o;
		return Objects.equals(productNo, product.productNo);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(productNo);
	}

	@Override
	public ProductId getId() {
		// TODO Auto-generated method stub
		return null;
	}
}