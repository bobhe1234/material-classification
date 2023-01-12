package com.jswy.domain.generic.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.jswy.domain.generic.demo.model.User;

/**
 * 基于SpringMVC框架开发web应用--数据操作层
 */
public interface UserRepository extends CrudRepository<User, Long> {
}