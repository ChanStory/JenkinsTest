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
	basePackages = "com.myapp.repository.h2Repository",
	entityManagerFactoryRef = "h2EntityManager", 
	transactionManagerRef = "h2TransactionManager"
)
public class H2Config {

	@Autowired
	private Environment env;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean h2EntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(h2DataSource());
		em.setPackagesToScan(new String[] { "com.myapp.jpa" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		//properties.put("hibernate.dialect", env.getProperty("hibernate.h2.dialect"));
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		em.setJpaPropertyMap(properties);

		return em;

	}
	
	@Primary
	@Bean
	public DataSource h2DataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("datasource.h2.driverClassName"));
		dataSource.setUrl(env.getProperty("datasource.h2.url"));
		dataSource.setUsername(env.getProperty("datasource.h2.user"));
		dataSource.setPassword(env.getProperty("datasource.h2.pass"));
		return dataSource;
	}

	@Primary
	@Bean
	public PlatformTransactionManager h2TransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(h2EntityManager().getObject());
		return transactionManager;
	}

}