package com.myapp.advice;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myapp.advice.exception.AuthenticationEntryPointException;
import com.myapp.advice.exception.LoginFailedException;
import com.myapp.advice.exception.UserNotFoundException;
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
		String code = encodingProperty("exception.unKnown.code");
		String msg = encodingProperty("exception.unKnown.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
	}
	
	@ExceptionHandler(UserNotFoundException.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult userNotFoundException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		String code = encodingProperty("exception.useNotFound.code");
		String msg = encodingProperty("exception.useNotFound.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
	}
	
	@ExceptionHandler(LoginFailedException.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult loginFailedException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		String code = encodingProperty("exception.loginFailed.code");
		String msg = encodingProperty("exception.loginFailed.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
	}
	
	@ExceptionHandler(AuthenticationEntryPointException.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult authConfigException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		String code = encodingProperty("exception.entryPoint.code");
		String msg = encodingProperty("exception.entryPoint.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
	}
	
	@ExceptionHandler(AccessDeniedException.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult accessDeniedException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		String code = encodingProperty("exception.accessDenied.code");
		String msg = encodingProperty("exception.accessDenied.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
	}
	
	//이클립스, 스프링부트 인코딩 설정, @propertysource 인코딩 속성을 적용해도 한글이 깨져서 일일히 인코딩 변경 해줌..
	private String encodingProperty(String prop) throws UnsupportedEncodingException {
		return new String(env.getProperty(prop).getBytes("ISO-8859-1"), "UTF-8");
	}
}
