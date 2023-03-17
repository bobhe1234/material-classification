package com.jswy.domain.generic.demo.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jswy.domain.support.AggregateRoot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "t_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product implements AggregateRoot<ProductMaster> {
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_master", nullable = false)
	private ProductMaster product_master;

	@Column(name = "create_time", length = 11, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Timestamp create_time = new Timestamp(System.currentTimeMillis());
	@Column(name = "modify_time", length = 11, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp modify_time = new Timestamp(System.currentTimeMillis());

	@Column(name = "creator", length = 40, nullable = false, updatable = false)
	private String creator;
	@Column(name = "modifer", length = 40, nullable = false, updatable = true)
	private String modifer;

	@Column(name = "category_id", length = 10, nullable = false)
	private Integer categoryId;

	@Column(name = "product_status", length = 10, nullable = false)
	private Integer productStatus;

	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "remark", length = 256)
	private String remark;

	@Column(name = "allow_across_category", length = 1, nullable = false)
	private Boolean allowAcrossCategory;

	public Product() {
	}

	public Product(Integer id, ProductMaster product_master, BigDecimal price, Integer categoryId,
			Integer productStatus, String remark, Boolean allowAcrossCategory) {
		this.id = id;
		this.product_master = product_master;
		this.price = price;
		this.categoryId = categoryId;
		this.productStatus = productStatus;
		this.remark = remark;
		this.allowAcrossCategory = allowAcrossCategory;
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
		return Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getAllowAcrossCategory() {
		return allowAcrossCategory;
	}

	public void setAllowAcrossCategory(Boolean allowAcrossCategory) {
		this.allowAcrossCategory = allowAcrossCategory;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getModify_time() {
		return modify_time;
	}

	public void setModify_time(Timestamp modify_time) {
		this.modify_time = modify_time;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setId(ProductMaster product_master) {
		this.product_master = product_master;
	}

	@Override
	public ProductMaster getId() {
		return this.product_master;
	}
}