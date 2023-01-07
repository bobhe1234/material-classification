package com.jswy.domain.generic.demo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import com.jswy.domain.support.AggregateRoot;

@Entity
@Table(name = "t_customer")
public class Customer implements AggregateRoot<CustomerId> {

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
	@GeneratedValue(generator = "customer_id", strategy = GenerationType.IDENTITY) // 关联customer_id
	@SequenceGenerator(name = "customer_id", sequenceName = "customer_seq", allocationSize = 1)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@ManyToOne
	@JoinColumn(name = "product_id") // @JoinColumn:代表外键字段
	private Product product;

	@Column(name = "email")
	private String email;

	@Column(name = "creation_time")
	protected Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
	@Column(name = "last_modified_time")
	protected Timestamp modificationTimestamp = new Timestamp(System.currentTimeMillis());

	@Column(name = "is_delete")
	protected int is_delete;// 是否删除。0:未删除;1:已删除

	/**
	 * 创建日期，对应ts_insert字段
	 */
	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Timestamp creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	/**
	 * 修改日期，对应ts_update字段
	 */
	public Timestamp getModificationTimestamp() {
		return modificationTimestamp;
	}

	public void setModificationTimestamp(Timestamp modificationTimestamp) {
		this.modificationTimestamp = modificationTimestamp;
	}

	/**
	 * 是否删除，对应dr字段
	 * 
	 * @return
	 */

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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public CustomerId getId() {
		// TODO Auto-generated method stub
		return null;
	}
}
