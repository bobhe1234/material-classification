package com.jswy.interfaces.assembler;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.jswy.domain.generic.demo.model.User;
import com.jswy.domain.support.DomainAssembler;

/**
 * DTO转领域模型实现
 * 
 * @author admin
 *
 */
@Component
public class UserAssembler extends DomainAssembler<User, UserDto> {
	public UserAssembler() {
	}

	@Override
	protected User toEntity(UserDto dto) {
		User user = new User();
		try {
			// 将dto的属性拷贝到user
			BeanUtils.copyProperties(user, dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			System.out.println("error:" + e.getLocalizedMessage());
		}
		return user;
	}

	@Override
	protected boolean updateInternal(User entity, UserDto dto) {
		entity.setModifer("root");
		entity.setCreator("root");
		return true;
	}

}
