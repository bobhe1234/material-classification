package com.jswy.interfaces.assembler;

import com.jswy.domain.support.DExtensible;

import lombok.Data;

/**
 * DTO参数
 * 
 * @author admin
 *
 */
@Data
public class UserDto extends DExtensible {

	private Long id;
	private String name;
	private Integer age;

}
