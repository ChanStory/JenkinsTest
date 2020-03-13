package com.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.common.CommonResult;
import com.myapp.common.SingleResult;
import com.myapp.service.LoginService;
import com.myapp.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 관련 컨트롤러
 * 
 * @author chans
 */

@Api(tags = {"1. Login"})
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
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
	 * @return SingleResult
	 */
    @ApiOperation(value = "로그인", notes = "회원 로그인을 한다")
    @GetMapping(value = "/login")
    public SingleResult<String> login(@ApiParam(value = "id", required = true) @RequestParam String id,
                                      @ApiParam(value = "password", required = true) @RequestParam String password) {
 
    	String jwtToken = loginService.login(id, password);
 
        //로그인이 성공하면 jwt token을 발급
        return responseService.getSingleResult(jwtToken);
    }
    
    /**
	 * 로그아웃
	 * 
	 * @param 
	 * @return CommonResult
	 */
    @ApiOperation(value = "로그아웃", notes = "로그아웃을 한다")
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
    @GetMapping(value = "/logout")
    public CommonResult logout(HttpServletRequest request) {
 
    	loginService.logout(request);
 
        return responseService.getSuccessResult();
    }
}
