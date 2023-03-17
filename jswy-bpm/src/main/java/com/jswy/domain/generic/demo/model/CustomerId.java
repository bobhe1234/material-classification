package com.jswy.domain.generic.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jswy.domain.support.Column;

/**
 * Embeddable和 EmbeddedId大多数时候用于JPA / Spring Data中的复合主键 <br>
 * NOTE : 符合条件： 1.必须实现Serializable <br>
 * 2.必须有默认的 public无参数的构造方法、必须覆盖 equals和 hashCode 方法，这些要求与使用复合主键的要求相同<br>
 * 3.将嵌入式主键类使用 @Embeddable 标注，表示这个是一个嵌入式类。
 *
 * @author admin
 *
 */
@Entity
@Table(name = "t_customer_id")
public class CustomerId implements /* Identifier */Serializable {
	private static final long serialVersionUID = -5043491834395669890L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CustomerId() {
	}

	public CustomerId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!(other instanceof CustomerId))
			return false;

		CustomerId otherStudentId = (CustomerId) other;

		return this.getId() == otherStudentId.id;
	}

	@Override
	public int hashCode() {
//		return this.id.hashCode();
		int result = 17;
		result = 37 * result + this.getId();
//		result = 37 * result + (getCodigoActividades() == null ? 0 : this.getCodigoActividades().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "CustomerId [id=" + id + "]";
	}

	public static CustomerId of(Integer id) {
		return new CustomerId(id);
	}
}