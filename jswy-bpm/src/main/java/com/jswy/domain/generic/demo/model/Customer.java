package com.jswy.domain.generic.demo.model;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jswy.domain.support.AggregateRoot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer implements AggregateRoot<Integer> {

	/**
	 * JPA提供的四种标准用法为TABLE,SEQUENCE,IDENTITY,AUTO<br>
	 * 1.​IDENTITY:主键由数据库自动生成（主要是自动增长型）,Mysql支持
	 * 2.​SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列，Oracle支持<br>
	 * 3.AUTO：主键由程序控制<br>
	 * 4.​TABLE：使用一个特定的数据库表格来保存主键<br>
	 * 自定义的数据库的Sequence:表示通过ORD_SEQ1的sequence自增序列来给ID赋值<br>
	 * 这也是Hibernate官方推荐的方式，为每一个表创建一个Sequence。<br>
	 * Hibernate在生成@ID标注的字段的值时，先访问数据库，查询下一个Sequence的值，再封装完整的insert语句插入数据
	 */
	@Id
//	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(generator = "id", strategy = GenerationType.IDENTITY) // 关联customer_id
	@SequenceGenerator(name = "id", sequenceName = "t_customer_seq", allocationSize = 1)
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerId customer_id;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@CreatedDate // 开启了审计功能 创建时间和更新时间会自动设置并插入数据库
	@Column(name = "create_time", length = 11, nullable = false, updatable = false)
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp create_time = new Timestamp(System.currentTimeMillis());
	
	@LastModifiedDate//开启了审计功能 创建时间和更新时间会自动设置并插入数据库
	@Column(name = "modify_time", length = 11, nullable = false, updatable = true)
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp modify_time = new Timestamp(System.currentTimeMillis());

	/**
	 * 是否删除，对应dr字段-0:未删除;1:已删除
	 * 
	 * @return
	 */
	@Column(name = "is_delete", columnDefinition = "TINYINT(1) DEFAULT 0")
	protected int is_delete;

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

	/**
	 * 排除属性：假如我们想使实体类中的某个字段，在执行时，不创建数据库中的对应字段。使用@Transient即可
	 */
	@Transient
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) throws Exception {
//		if (id == null || id == 0) {
//			throw new Exception("id is empty, please input the value !");
//		}
		this.id = id;
	}

	public CustomerId getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(CustomerId customer_id) {
		this.customer_id = customer_id;
	}

	@Override
	public String toString() {
		return "id=" + this.id + ",customer_id=" + this.customer_id + ",name=" + this.name + ",phone=" + this.phone;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(create_time, customer_id, email, id, name, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Customer))
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(create_time, other.create_time) && Objects.equals(customer_id, other.customer_id)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone);
	}
}
