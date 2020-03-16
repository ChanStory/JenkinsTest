package com.myapp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * JWT 토큰 검증 필터
 * 
 * @author chans
 */

public class JwtAuthenticationFilter extends GenericFilterBean {
	 
    private JwtTokenProvider jwtTokenProvider;
 
    //jwt provier 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //Request로 넘어오는 Jwt Token의 유효성을 검증하는 filter를 filterChain에 등록
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        
        if(token != null && jwtTokenProvider.validateToken(token, "access")) {
        	
            Authentication auth = jwtTokenProvider.getAuthentication(token, "access");
            SecurityContextHolder.getContext().setAuthentication(auth);
            
        }else if(token != null && jwtTokenProvider.validateToken(token, "refresh")) {
        	
        	Authentication auth = jwtTokenProvider.getAuthentication(token, "refresh");
            SecurityContextHolder.getContext().setAuthentication(auth);
            
        }
        
        filterChain.doFilter(request, response);
    }
}