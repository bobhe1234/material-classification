package com.jswy.domain.generic.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 设计为主表,存放产品编号/产品名字/创建时间/修改时间等主要特征属性,对于每个产品编号只有一条记录(可以有多个Product产品版本对象)
 * 
 * @author admin
 *
 */
@Entity
@Table(name = "t_product_master")
public class ProductMaster implements /* Identifier */Serializable {
	private static final long serialVersionUID = -5043491834395669890L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Column(name = "product_no", length = 32, nullable = false, unique = true)
	private String productNo;
	@Column(name = "name", length = 64, nullable = false)
	private String name;

	@Column(name = "create_time", length = 11, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
	private Timestamp create_time = new Timestamp(System.currentTimeMillis());
	@Column(name = "modify_time", length = 11, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp modify_time = new Timestamp(System.currentTimeMillis());

	@Column(name = "creator", length = 40, nullable = false, updatable = false)
	private String creator;
	@Column(name = "modifer", length = 40, nullable = false, updatable = true)
	private String modifer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifer() {
		return modifer;
	}

	public void setModifer(String modifer) {
		this.modifer = modifer;
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

	public ProductMaster() {
	}

	public ProductMaster(Integer id2) {
		this.id = id2;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!(other instanceof ProductMaster))
			return false;

		ProductMaster otherStudentId = (ProductMaster) other;

		return this.getId() == otherStudentId.id && this.getProductNo() == otherStudentId.productNo
				&& this.getName() == otherStudentId.name && this.getCreator() == otherStudentId.creator;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + this.getId();
		result = 37 * result + (getProductNo() == null ? 0 : this.getProductNo().hashCode());
		result = 37 * result + (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result + (getCreator() == null ? 0 : this.getCreator().hashCode());
		return result;
	}

}