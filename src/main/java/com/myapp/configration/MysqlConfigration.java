package com.myapp.configration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories( 
	basePackages = "com.myapp.dao",
	entityManagerFactoryRef = "mysqlEntityManager", 
	transactionManagerRef = "mysqlTransactionManager"
)
public class MysqlConfigration {

	
	private final Environment env;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean mysqlEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(mysqlDataSource());
		em.setPackagesToScan(new String[] { "com.myapp.entity" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.put("hibernate.dialect", env.getProperty("datasource.mysql.dialect"));
		em.setJpaPropertyMap(properties);

		return em;

	}
	
	@Bean
	@Primary
	public DataSource mysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("datasource.mysql.driverClassName"));
		dataSource.setUrl(env.getProperty("datasource.mysql.url"));
		dataSource.setUsername(env.getProperty("datasource.mysql.username"));
		dataSource.setPassword(env.getProperty("datasource.mysql.password"));
		
		if(log.isInfoEnabled()) {
			log.info("mysql data Source url : " + dataSource.getUrl() + ", userName : " + dataSource.getUsername() + ", password : " + dataSource.getPassword());
		}
			
		
		return dataSource;
	}

	@Bean
	@Primary
	public PlatformTransactionManager mysqlTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(mysqlEntityManager().getObject());
		
		return transactionManager;
	}

}