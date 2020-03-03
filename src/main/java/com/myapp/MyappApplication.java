package com.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableAspectJAutoProxy //AOP를 사용하기위해 선언 @Aspect 어노테이션을 사용 할수 있게 해준다
@SpringBootApplication
@EnableJpaAuditing //JpaAuditing을 사용하기위해 선언
public class MyappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyappApplication.class, args);
	}
	
	//패스워드는 해쉬함수로 암호화 해야 하기 때문에 회원가입, 로그인 시 사용
	@Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
