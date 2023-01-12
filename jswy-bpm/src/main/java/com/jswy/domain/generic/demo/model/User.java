package com.jswy.domain.generic.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户类--映射表user,db语句如下：<br>
 * CREATE TABLE user1 ( id int NOT NULL , name varchar(128), password varchar(16), phone varchar(16) , create_time timestamp, age int, PRIMARY KEY (id) )
 * create sequence seq_user1_id increment by 1 start with 1 maxvalue 999999999;
 *
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4904258009554596176L;
	// 用户id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// 用户名
	@NotBlank(message = "用户名称不能为空")
	@Column(name = "name")
	private String name;
	// 邮箱
	@Column(name = "email")
	@Pattern(message = "邮箱格式不符", regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")
	private String email;

	public User(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}