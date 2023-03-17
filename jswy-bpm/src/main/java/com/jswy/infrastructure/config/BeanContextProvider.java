package com.jswy.infrastructure.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 在初始加载时缓存 Spring 应用程序上下文,然后使用上下文查找 Spring 托管 bean
 * 
 * @author admin
 *
 */
@Slf4j
@Component
public final class BeanContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanContextProvider.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
//
//	/**
//	 * 模板资源解析器
//	 * 
//	 * @return
//	 */
//	@Bean
//	@ConfigurationProperties(prefix = "spring.thymeleaf")
//	public SpringResourceTemplateResolver templateResolver() {
//		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//		templateResolver.setApplicationContext(this.applicationContext);
//		return templateResolver;
//	}
//
//	/**
//	 * Thymeleaf标准方言解释器
//	 * 
//	 * @return
//	 */
//	@Bean
//	public SpringTemplateEngine templateEngine() {
//		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//		templateEngine.setTemplateResolver(templateResolver());
//
//		// 支持Spring EL 表达式
//		templateEngine.setEnableSpringELCompiler(true);
//		return templateEngine;
//	}
//
//	/**
//	 * 视图解析器
//	 * 
//	 * @return
//	 */
//	@Bean
//	public ThymeleafViewResolver viewResolver() {
//		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//		viewResolver.setTemplateEngine(templateEngine());
//		return viewResolver;
//	}
}