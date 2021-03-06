package com.jswy.infrastructure.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 为了实现SpringSecurity框架的用户权限的配置，以便在系统中使用用户权限信息<br>
 * 基本是在文件中定义出来的用户信息，当然也可以是数据库中查询的用户权限信息<br>
 * 处理流程时用到的任务负责人，需要添加在这里
 * 
 * @author bobhe
 *
 */
@Configuration
public class DemoApplicationConfiguration {
	private Logger logger = LoggerFactory.getLogger(DemoApplicationConfiguration.class);

	@Bean
	public UserDetailsService myUserDetailsService() {
		/**
		 * 往内存中设置有权限的角色和用户
		 */
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		// 这里添加用户，后面处理流程时用到的任务负责人，需要添加在这里
		String[][] usersGroupsAndRoles = { { "root", "root", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam" },
				{ "other", "123456", "ROLE_ACTIVITI_USER", "GROUP_otherTeam" },
				{ "system", "123456", "ROLE_ACTIVITI_USER" }, { "admin", "123456", "ROLE_ACTIVITI_ADMIN" }, };

		for (String[] user : usersGroupsAndRoles) {
			List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2, user.length));
			logger.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings
					+ "]");
			inMemoryUserDetailsManager.createUser(new User(user[0], passwordEncoder().encode(user[1]),
					authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
		}

		return inMemoryUserDetailsManager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}