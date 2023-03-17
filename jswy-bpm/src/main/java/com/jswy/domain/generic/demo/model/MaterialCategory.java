package com.jswy.domain.generic.demo.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 物料分类树,特征如下:<br>
 * 1. 按照标准,树最大只有4层<br>
 * 2. 作为定义,分类树不会频繁改动,且数据量不大<br>
 * 3. 设计自带树层级,父项ID,根ID为0<br>
 * 
 * @author admin
 *
 */
@Entity
@Setter
@Getter
@Table(name = "t_material_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialCategory {
	/**
	 * 自增主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11, nullable = false, unique = true)
	private Integer id;

	/**
	 * 分类编码
	 */
	@Column(name = "category_no", columnDefinition = "varchar(32) NOT NULL COMMENT '分类编码'")
	private String category_no;

	/**
	 * 分类ID
	 */
	@Column(name = "category_id", columnDefinition = "int(10) NOT NULL COMMENT '分类id'")
	private Integer category_id;
	/**
	 * 分类名称
	 */
	@Column(name = "category_name", columnDefinition = "varchar(100) NOT NULL COMMENT '分类名称'")
	private String category_name;

	@JoinTable(name = "t_category_tree", joinColumns = {
			@JoinColumn(name = "parent_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "sub_id", referencedColumnName = "id") })
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_tree", nullable = false)
	private ProductMaster category_tree;

	/**
	 * 标识分类树中当前分类节点所在层级/深度的位置
	 */
	@Column(name = "category_dept", columnDefinition = "tinyint(4) NOT NULL DEFAULT '0' COMMENT '分类层级深度'")
	private Integer category_dept;

	/**
	 * 标识在分类树中对应父分类节点的ID
	 */
	@Column(name = "category_parent_id", columnDefinition = "int(11) NOT NULL COMMENT '父分类id'")
	private String category_parent_id;

	@Column(name = "creator", length = 40, nullable = false, updatable = false)
	private String creator;
	@Column(name = "modifer", length = 40, nullable = false, updatable = true)
	private String modifer;
	@Column(name = "create_time", columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
	private Timestamp create_time = new Timestamp(System.currentTimeMillis());
	@Column(name = "modify_time", length = 11, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'")
	private Timestamp modify_time = new Timestamp(System.currentTimeMillis());

}
