package com.jswy.domain.support;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

/**
 * 所有领域模型数据存储基类,记录操作日志/表级隔离字段,扩展属性
 * 
 * @author admin
 *
 */
@Data
@MappedSuperclass
public class Domain implements Serializable {

	private static final long serialVersionUID = 7888539025639498560L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "create_date", length = 11, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
	@CreatedDate
	@CreationTimestamp
	private Timestamp create_date = new Timestamp(System.currentTimeMillis());

	@Column(name = "modify_date", length = 11, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@LastModifiedDate
	@UpdateTimestamp
	private Timestamp modify_date = new Timestamp(System.currentTimeMillis());

	@Column(name = "creator", length = 40, nullable = false, updatable = false)
	private String creator;
	@Column(name = "modifer", length = 40, nullable = false, updatable = true)
	private String modifer;

	@Column(name = "deleted")
	private boolean deleted;

	/**
	 * 扩展属性参数
	 */
//	@OneToMany(cascade = CascadeType.REFRESH) // 示级联对象在Role表存在则进行update操作，而不做save操作
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<String, String> extended;

	public Domain() {
	}

	public Domain(Long id, Timestamp create_date, Timestamp modify_date, String creator, String modifer,
			boolean deleted, Map<String, String> extended) {
		super();
		this.id = id;
		this.create_date = create_date;
		this.modify_date = modify_date;
		this.creator = creator;
		this.modifer = modifer;
		this.deleted = deleted;
		this.extended = extended;
	}

	@Override
	public String toString() {
		return "Domain [id=" + id + ", create_date=" + create_date + ", modify_date=" + modify_date + ", creator="
				+ creator + ", modifer=" + modifer + ", deleted=" + deleted + ", extended=" + extended + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(extended, create_date, creator, deleted, id, modifer, modify_date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Domain))
			return false;
		Domain other = (Domain) obj;
		return Objects.equals(extended, other.extended) && Objects.equals(create_date, other.create_date)
				&& Objects.equals(creator, other.creator) && deleted == other.deleted && Objects.equals(id, other.id)
				&& Objects.equals(modifer, other.modifer) && Objects.equals(modify_date, other.modify_date);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the create_date
	 */
	public Timestamp getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return the modify_date
	 */
	public Timestamp getModify_date() {
		return modify_date;
	}

	/**
	 * @param modify_date the modify_date to set
	 */
	public void setModify_date(Timestamp modify_date) {
		this.modify_date = modify_date;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the modifer
	 */
	public String getModifer() {
		return modifer;
	}

	/**
	 * @param modifer the modifer to set
	 */
	public void setModifer(String modifer) {
		this.modifer = modifer;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the additional
	 */
	public Map<String, String> getExtended() {
		return extended;
	}

	/**
	 * @param additional the additional to set
	 */
	public void setExtended(Map<String, String> extended) {
		this.extended = extended;
	}
}
