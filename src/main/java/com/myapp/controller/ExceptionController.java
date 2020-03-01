package com.myapp.controller;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.advice.exception.AuthenticationEntryPointException;
import com.myapp.common.CommonResult;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 예외처리 컨트롤러
 * 
 * @author chans
 */

@ApiIgnore
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {
 
	/**
	 * AuthenticationEntryPointException 예외 발생
	 * 
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/entrypoint") //어떤 http메소드 요청에서 예외가 일어날지 모르니 모든 메소드를 다 받아줌
    public CommonResult entrypointException() {
        throw new AuthenticationEntryPointException();
    }
    
    /**
	 * AccessDeniedException 예외 발생
	 * 
	 * @param 
	 * @return 
	 */
	@RequestMapping(value = "/accessdenied")
    public CommonResult accessdeniedException() throws AccessDeniedException {
        throw new AccessDeniedException("");
    }
}