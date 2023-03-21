package com.jswy.domain.generic.demo.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户类--映射表user,db语句如下：<br>
 * CREATE TABLE user1 ( id int NOT NULL , name varchar(128), password
 * varchar(16), phone varchar(16) , create_time timestamp, age int, PRIMARY KEY
 * (id) ) create sequence seq_user1_id increment by 1 start with 1 maxvalue
 * 999999999;
 *
 */
@Data
@Entity
@Table(name = "t_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "update aggrement set deleted=true where id=?")
@Where(clause = "deleted=false")
public class User {

	private static final long serialVersionUID = 4904258009554596176L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@NotBlank(message = "用户名称不能为空")
	@Column(name = "name")
	private String name;
//	@NotBlank(message = "用户性别不能为空")
	@Column(name = "sex")
	private String sex;
	@Column(name = "age")
	private int age;
	@Column(name = "email")
	@Pattern(message = "邮箱格式不符", regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")
	private String email;

//	public User() {
//		// TODO Auto-generated constructor stub
//	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, email, id, name, sex);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return age == other.age && Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(sex, other.sex);
	}

}