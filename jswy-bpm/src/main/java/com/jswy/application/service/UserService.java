package com.jswy.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jswy.domain.generic.demo.mapper.UserMapper;
import com.jswy.domain.generic.demo.model.User;
import com.jswy.domain.generic.demo.repository.UserRepository;

/**
 * 应用层服务类：调用Spring Data JPA和Mybatis接口进行业务处理
 * 
 * @author admin
 *
 */
@Service
public class UserService {

	/**
	 * Spring Data JPA
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * Mybatis映射类
	 */
	@Autowired
	private UserMapper userMapper;

	/**
	 * 返回所有的用户（JPA实现）
	 * 
	 * @return
	 */
	public List<User> listUsers() {
		return (List<User>) userRepository.findAll();
	}

	/**
	 * 保存用户（JPA实现）
	 * 
	 * @param user
	 * @return
	 */
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * 删除用户（JPA实现）
	 * 
	 * @param id
	 */
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	/**
	 * 查找用户（JPA实现）
	 * 
	 * @param id
	 * @return
	 */
	public User findUser(Long id) {
		return userRepository.findById(id).get();
	}

	/**
	 * 根据名称查找用户--Mybatis实现
	 * 
	 * @param name
	 * @return
	 */
	public List<User> searchUser(String name) {
		return userMapper.findByName(name);
	}

}