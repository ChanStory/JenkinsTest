package com.myapp.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

/**
 * JWT 토큰 검증 필터
 * 
 * @author chans
 */

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	 
    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, String> redisTemplate;
    
    //Request로 넘어오는 Jwt Token의 유효성을 검증하는 filter를 filterChain에 등록
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);
        
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        //토큰 유효성 체크
        if(accessToken != null && jwtTokenProvider.validateToken(accessToken, "access")) {
        	
        	//로그아웃으로 만료된 토큰일경우 
        	if(accessToken.equals(vop.get("access-" + jwtTokenProvider.getUserPk(accessToken, "access")))) {
        		httpResponse.sendRedirect("/exception/token-expired");
        	}
        	
            Authentication auth = jwtTokenProvider.getAuthentication(accessToken, "access");
            SecurityContextHolder.getContext().setAuthentication(auth);
            
        }else if(refreshToken != null && jwtTokenProvider.validateToken(refreshToken, "refresh")) {
        	
        	//로그아웃으로 만료된 토큰일경우
			if(refreshToken.equals(vop.get("refresh-" + jwtTokenProvider.getUserPk(refreshToken, "refresh")))) {
				httpResponse.sendRedirect("/exception/token-expired");
			}
			
        	Authentication auth = jwtTokenProvider.getAuthentication(refreshToken, "refresh");
            SecurityContextHolder.getContext().setAuthentication(auth);
            
        }
        
        filterChain.doFilter(request, response);
    }
}