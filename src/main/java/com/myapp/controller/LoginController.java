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

/**
 * 로그인 관련 컨트롤러
 * 
 * @author chans
 */

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
 
    /**
	 * 로그인
	 * 
	 * @param String id
	 * @param String password
	 * @return SingleResult
	 */
    @ApiOperation(value = "로그인", notes = "회원 로그인을 한다")
    @GetMapping(value = "/login")
    public SingleResult<String> login(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
 
    	//입력받은 ID로 유저를 가져온다. 해당ID 유저가 없을 시 UserNotFoundException 발생
        User user = userRepository.findByUid(id).orElseThrow(UserNotFoundException::new);
        
        //입력된 password가 틀릴 시 LoginFailedException 발생
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new LoginFailedException();
 
        //로그인이 성공하면 jwt token을 발급
        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
    }
 
    /**
	 * 회원가입
	 * 
	 * @param String id
	 * @param String password
	 * @param String name
	 * @return SingleResult
	 */
    @ApiOperation(value = "회원가입", notes = "회원가입을 한다")
    @GetMapping(value = "/join")
    public CommonResult join(@ApiParam(value = "회원ID", required = true) @RequestParam String id,
                             @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                             @ApiParam(value = "이름", required = true) @RequestParam String name) {
    	
    	userRepository.save(User.builder().uid(id)
						                  .password(passwordEncoder.encode(password))
						                  .name(name)
						                  .roles(Collections.singletonList("ROLE_USER"))
						                  .build());
    	
        return responseService.getSuccessResult();
    }
}
