package com.jswy;

import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.alibaba.druid.pool.DruidDataSource;
import com.jswy.domain.support.IRepositoryImpl;

/**
 * exclude=securityAutoConfiguration
 * 解决GlobalAuthenticationConfigurerAdapter.class] cannot be opened because it
 * does not exist报错
 * 
 * @author bobhe
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableJpaRepositories(repositoryBaseClass = IRepositoryImpl.class) // 配置MyRepoImpl类为其他接口动态代理的实现基类
@EntityScan(basePackages = "com.jswy.domain.generic.demo.model") // 扫描的Entity文件路径
@MapperScan(basePackages = "com.jswy.domain.generic.demo.mapper") // 扫描的mapper文件路径
//@ComponentScan(value = "com.jswy.infrastructure.impl") // 扫描 会导致无法登录?
public class MaterialClassificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaterialClassificationApplication.class, args);
	}

	/**
	 * 注册数据源bean
	 * 
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dateSource() {
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}

	@Bean
	public TomcatServletWebServerFactory tomcatFactory() {
		return new TomcatServletWebServerFactory() {

			/**
			 * 降级tomcat版本，使用8.5.0 或以下版本。8.5.0版本中不会对manifest进行分析加载 或者增加一下代码设置不扫描Manifest文件
			 */
			@Override
			protected void postProcessContext(Context context) {
				((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
			}
		};
	}
}
