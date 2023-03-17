package com.jswy.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 访问静态资源被拦截问题--》加入资源处理器
 * 
 * @author bobhe
 *
 */
@Configuration
@EnableWebMvc
public class WebMVCConfig implements WebMvcConfigurer {

	/**
	 * 放行、映射资源：其中addResourceHandler指向映射路径，addResourceLocations指向资源文件路径
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");// 映射static路径下的静态资源为直接路径（camunda表单配置需要）
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");// 映射templates模板动态资源路径

		registry.addResourceHandler("camunda.cfg.xml").addResourceLocations("classpath:/");// 加入camunda.cfg.xml配置资源，否则找不到‘processEngineConfiguration’
//		 registry.addResourceHandler("/**").addResourceLocations("classpath:/application.yml");

		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");// 添加对swagger资源的放行
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * 设置跨域请求
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")// 设置允许跨域的路径
				.allowedOrigins("*")// 设置允许跨域请求的域名
				.allowCredentials(true)// 是否允许证书 不再默认开启
				.allowedMethods("GET", "POST", "PUT", "DELETE")// 设置允许的方法
				.maxAge(3600);// 跨域允许时间
	}
}