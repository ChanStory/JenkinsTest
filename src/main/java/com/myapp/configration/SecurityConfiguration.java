package com.myapp.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myapp.security.CustomAccessDeniedHandler;
import com.myapp.security.CustomAuthenticationEntryPoint;
import com.myapp.security.JwtAuthenticationFilter;
import com.myapp.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * 스프링 시큐리티 설정
 * 
 * @author chans
 */

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	// 접속 시 인증을 JWT로 하기위해
    private final JwtTokenProvider jwtTokenProvider;
    
    //스프링 시큐리티에서 인증을 담당하는 컴포넌트
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.formLogin().disable() //rest api이므로 미인증시 로그인폼으로 리다이렉트 됨을 방지
            .csrf().disable() //rest api이므로 csrf 기능을 disable
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt token으로 인증하므로 세션은 생성안함
            .and()
                .authorizeRequests() //다음 리퀘스트에 대한 사용권한 체크
                    .antMatchers("/*/login", "/*/join", "/exception/**").permitAll() //가입, 인증, exception 주소는 누구나 접근가능
                    .anyRequest().authenticated() //그외 나머지 요청은 모두 인증된 회원만 접근 가능
            .and()
            	.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) //리소스에 접근하기 위한 권한이 없을 시 처리하는 핸들러
            .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()) //리소스에 접근하기 위한 권한이 모자를 시 처리하는 핸들러
            .and()
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider) , UsernamePasswordAuthenticationFilter.class ); //jwt token 필터를 id, password 인증 필터 전에 넣어준다
 
    }
 
	//swagger 문서는 스프링 시큐리티 필터 무시
    @Override 
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
 
    }
}