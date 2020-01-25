package com.myapp.filters;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class CorsFilter extends GenericFilterBean{
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException
	{
		// cors 필터적용, CrossOrigin어노테이션이 적용되지 않을 시 테스트 요망
		/*
		 * HttpServletResponse response = (HttpServletResponse) res;
		 * response.setHeader("Access-Control-Allow-Origin", "*");
		 * response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
		 * response.setHeader("Access-Control-Max-Age", "3600");
		 * response.setHeader("Access-Control-Allow-Headers",
		 * "x-requested-with, origin, content-type, accept");
		 */

		
		chain.doFilter(req, res);
	}
}
