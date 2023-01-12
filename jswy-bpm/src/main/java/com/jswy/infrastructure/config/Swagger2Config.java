package com.jswy.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.common.base.Predicates;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger的配置类:<br>
 * 1.低于springboot 2.0版本的配置类:WebMvcConfigurerAdapter只有实现该接口才可用，当前版本Spring-Boot:
 * (v2.3.7.RELEASE)可用 2.高于springboot 2.0版本的配置类:WebMvcConfigurer可用
 * 
 * @author admin
 *
 */
@EnableSwagger2
@Configuration
@EnableWebMvc
public class Swagger2Config {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
//				.apis(RequestHandlerSelectors.any())// 对所有api进行监控
//				.groupName("myDocket").enable(true).pathMapping("/")
				.apiInfo(apiInfo()).select()// 选择那些路径和api会生成document
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))// 类注解拦截方法
				.paths(Predicates.not(PathSelectors.regex("/error.*")))// 错误路径不监控，不显示错误的接口地址
				.paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
				.paths(PathSelectors.any()).build();
	}

	// 构建 api文档的详细信息函数
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Swagger2 REST API")// 页面标题
				.description("*** Web Service Api ***").version("2.0.0").license("Apache 2.0")// 条款地址
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").build();// 描述
	}
}