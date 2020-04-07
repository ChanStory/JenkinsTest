package com.myapp.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CORS 필터
 * 
 * @author chans
 */

public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //CORS정책 관련 설정 해줌
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request= (HttpServletRequest) servletRequest;

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); //요청이 허용될 도메인
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT"); //요청이 허용될 HTTP메소드
        response.setHeader("Access-Control-Allow-Headers", "*"); //요청 시 사용할 수 있는 HTTP Header
        response.setHeader("Access-Control-Allow-Credentials", "true"); //Credential 요청 사용 유무, true로 설정할 시 Access-Control-Allow-Origin헤더에 * 를 사용 할 수 없음
        response.setHeader("Access-Control-Max-Age", "180"); //요청 결과가 캐쉬에 남아있는 시간
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}