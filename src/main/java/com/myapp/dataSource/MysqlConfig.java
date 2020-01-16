package com.myapp.dataSource;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories( 
	basePackages = "com.myapp.repository.mysqlRepository",
	entityManagerFactoryRef = "mysqlEntityManager", 
	transactionManagerRef = "mysqlTransactionManager"
)
public class MysqlConfig {

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean mysqlEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(mysqlDataSource());
		em.setPackagesToScan(new String[] { "com.myapp.jpa" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		em.setJpaPropertyMap(properties);

		return em;

	}
	
	@Bean
	public DataSource mysqlDataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("datasource.mysql.driverClassName"));
		dataSource.setUrl(env.getProperty("datasource.mysql.url"));
		dataSource.setUsername(env.getProperty("datasource.mysql.user"));
		dataSource.setPassword(env.getProperty("datasource.mysql.pass"));
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager mysqlTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(mysqlEntityManager().getObject());
		return transactionManager;
	}

}