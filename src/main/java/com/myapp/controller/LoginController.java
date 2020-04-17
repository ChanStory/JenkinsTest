package com.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.common.CommonResult;
import com.myapp.service.LoginService;
import com.myapp.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * 로그인 관련 컨트롤러
 * 
 * @author chans
 */

@Api(tags = {"1. Login"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class LoginController {
	
	private final LoginService loginService;
    private final ResponseService responseService;
    
    /**
	 * 로그인
	 * 
	 * @param String id
	 * @param String password
	 * @return CommonResult
	 */
    @ApiOperation(value = "로그인", notes = "회원 로그인을 한다")
    @GetMapping(value = "/login")
    public CommonResult login(@ApiParam(value = "id", required = true) @RequestParam String id,
                  			  @ApiParam(value = "password", required = true) @RequestParam String password,
                  			  HttpServletResponse response) {
 
    	loginService.login(id, password, response);

        return responseService.getSuccessResult();
    }
    
    /**
   	 * 리프레쉬 토큰 로그인
   	 * 
   	 * @param HttpServletResponse response
   	 * @return CommonResult
   	 */
    @ApiOperation(value = "리프레쉬 토큰 로그인", notes = "리프레쉬 토큰으로 로그인을 한다")
    @GetMapping(value = "/login/refresh")
    public CommonResult refreshLogin(HttpServletResponse response) {
    	loginService.refreshLogin(response);
    	
        return responseService.getSuccessResult();
	}
       
    /**
	 * 로그아웃
	 * 
	 * @param 
	 * @return CommonResult
	 */
    @ApiOperation(value = "로그아웃", notes = "로그아웃을 한다")
    @GetMapping(value = "/logout")
    public CommonResult logout(HttpServletRequest request) {
 
    	loginService.logout(request);
 
        return responseService.getSuccessResult();
    }
    
    
}
