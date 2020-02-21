package com.myapp.advice;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myapp.advice.exception.CUserNotFoundException;
import com.myapp.common.CommonResult;
import com.myapp.service.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j//log객체 생성 생략
@RequiredArgsConstructor
@RestControllerAdvice
public class AfterThrowingAdvice {
	
	private final Environment env;
	
	private final ResponseService responseService;
	
	@ExceptionHandler(Exception.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult defaultException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException {
		//이클립스 인코딩 설정, @propertysource 인코딩 속성을 적용해도 한글이 깨져서 일일히 인코딩 변경 해줌..
		String code = new String(env.getProperty("exception.unKnown.code").getBytes("ISO-8859-1"), "UTF-8");
		String msg = new String(env.getProperty("exception.unKnown.msg").getBytes("ISO-8859-1"), "UTF-8");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
	}
	
	@ExceptionHandler(CUserNotFoundException.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult userNotFoundException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException {
		String code = new String(env.getProperty("exception.useNotFound.code").getBytes("ISO-8859-1"), "UTF-8");
		String msg = new String(env.getProperty("exception.useNotFound.msg").getBytes("ISO-8859-1"), "UTF-8");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
	}
}
