package com.myapp.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.myapp.advice.exception.AuthenticationEntryPointException;

import lombok.extern.slf4j.Slf4j;
 
/**
 * 권한 없음 예외 핸들러
 * 
 * @author chans
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
 
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
    	response.sendRedirect("/exception/entrypoint");
    }
}