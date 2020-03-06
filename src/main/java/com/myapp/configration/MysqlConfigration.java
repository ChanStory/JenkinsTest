package com.myapp.configration;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

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

/**
 * mysql 설정 클래스
 * 
 * @author chans
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
@PropertySource({ "application.properties" })
@EnableJpaRepositories( 
	basePackages = "com.myapp.dao", //Repository가 있는 패키지
	entityManagerFactoryRef = "mysqlEntityManager", //밑에서 생성할 EntityManagerFactory
	transactionManagerRef = "mysqlTransactionManager" //밑에서 생성할 트랜잭션 매니저
)
public class MysqlConfigration {
	
	private final Environment env; //내부 properties 파일

	/**
	 * mysqlEntityManager생성
	 * 
	 * @param
	 * @return LocalContainerEntityManagerFactoryBean
	 */
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean mysqlEntityManager() throws IOException {
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
	
	/**
	 * mysqlDataSource생성
	 * 
	 * @param
	 * @return DataSource
	 */
	@Bean
	@Primary
	public DataSource mysqlDataSource() throws IOException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		//DB커넥션에 관한 정보는 외부 properties파일에서 가져옴
		FileReader resources = new FileReader("DB_JWT_Info.properties");
		Properties properties = new Properties();
		
		properties.load(resources);
		
		dataSource.setUrl(properties.getProperty("datasource.mysql.url"));
		dataSource.setUsername(properties.getProperty("datasource.mysql.username"));
		dataSource.setPassword(properties.getProperty("datasource.mysql.password"));
		dataSource.setDriverClassName(properties.getProperty("datasource.mysql.driverClassName"));
		
		if(log.isInfoEnabled()) {
			log.info("mysql data Source url : " + dataSource.getUrl() + ", userName : " + dataSource.getUsername() + ", password : " + dataSource.getPassword());
		}
			
		return dataSource;
	}
	
	/**
	 * mysqlTransactionManager생성
	 * 
	 * @param
	 * @return PlatformTransactionManager
	 */
	@Bean
	@Primary
	public PlatformTransactionManager mysqlTransactionManager() throws IOException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(mysqlEntityManager().getObject());
		
		return transactionManager;
	}

}