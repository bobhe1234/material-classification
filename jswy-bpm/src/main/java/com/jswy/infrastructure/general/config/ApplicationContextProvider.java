package com.jswy.infrastructure.general.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 在初始加载时缓存 Spring 应用程序上下文。然后使用上下文查找 Spring 托管 bean
 * 
 * @author admin
 *
 */
@Slf4j
@Component
public final class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	private static void setContext(ApplicationContext ctx) {
		ApplicationContextProvider.applicationContext = ctx;
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		setContext(ctx);
	}

	public static <T> T getBean(Class<T> tClass) {
		return applicationContext.getBean(tClass);
	}
}