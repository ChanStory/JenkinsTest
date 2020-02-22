package com.myapp.controller;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.advice.exception.LoginFailedException;
import com.myapp.advice.exception.UserNotFoundException;
import com.myapp.common.CommonResult;
import com.myapp.common.SingleResult;
import com.myapp.dao.UserRepository;
import com.myapp.object.User;
import com.myapp.security.JwtTokenProvider;
import com.myapp.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"1. Login"})
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class LoginController {
 
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
 
    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @GetMapping(value = "/login")
    public SingleResult<String> login(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
 
        User user = userRepository.findByUid(id).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new LoginFailedException();
 
        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
    }
 
    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @GetMapping(value = "/join")
    public CommonResult join(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name) {
 
    	userRepository.save(User.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();
    }
}
