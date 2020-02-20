package com.myapp.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myapp.common.CommonResult;
import com.myapp.service.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j//log객체 생성 생략
@RequiredArgsConstructor
@RestControllerAdvice
public class AfterThrowingAdvice {
	
	private final ResponseService responseService;
	
	@ExceptionHandler(Exception.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult afterThrowing(HttpServletRequest request, Exception e) {
		if (log.isErrorEnabled()) {
			log.error(e.getMessage(), e);
		}
		return responseService.getFailResult();
	}

}
