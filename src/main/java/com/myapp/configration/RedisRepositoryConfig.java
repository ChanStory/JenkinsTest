package com.myapp.configration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() throws IOException {
    	//DB에 관한 정보는 외부 properties파일에서 가져옴
		FileReader resources = new FileReader("DB_JWT_Info.properties");
		Properties properties = new Properties();
		
		properties.load(resources);
    	
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(properties.getProperty("spring.redis.host"),
																									 Integer.parseInt(properties.getProperty("spring.redis.port")));
        redisStandaloneConfiguration.setPassword(properties.getProperty("spring.redis.password"));
        
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() throws IOException {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}