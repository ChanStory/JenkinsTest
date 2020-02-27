package com.myapp.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.advice.exception.UserNotFoundException;
import com.myapp.common.CommonResult;
import com.myapp.common.ListResult;
import com.myapp.common.SingleResult;
import com.myapp.dao.UserRepository;
import com.myapp.object.User;
import com.myapp.service.ResponseService;
import com.myapp.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 유저 관련 컨트롤러
 * 
 * @author chans
 */

@Api(tags = {"2. User"})
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {
	
	private final UserService userService;
    private final UserRepository userRepository;
    private final ResponseService responseService;
 
    /**
	 * 회원가입
	 * 
	 * @param id 
	 * @param password 
	 * @param name 
	 * @param phoneNumber 
	 * @param birthDate 
	 * @param email 
	 * @param address 
	 * @return CommonResult
	 */
    @ApiOperation(value = "회원가입", notes = "회원가입을 한다")
    @PostMapping(value = "/user")
    public CommonResult join(@ApiParam(value = "회원가입시 필요한 데이터", 
    								   required = true) @Valid @RequestBody User user) {
    	
    	userService.join(user, Collections.singletonList("ROLE_USER"));
        
    	return responseService.getSuccessResult();
    }
    
    /**
	 * 전체 회원 조회
	 * 
	 * @param X-AUTH-TOKEN
	 * @return ListResult
	 */
    @ApiOperation(value = "전체 회원 조회", notes = "모든 회원을 조회한다")
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userRepository.findAll());
    }
 
    /**
	 * 회원 조회
	 * 
	 * @param X-AUTH-TOKEN
	 * @return SingleResult
	 */
    @ApiOperation(value = "회원 단건 조회", notes = "회원을 조회한다")
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById() {
    	
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        
        return responseService.getSingleResult(userRepository.findByUid(id).orElseThrow(UserNotFoundException::new));
    }
 
    /**
	 * 회원 수정
	 * 
	 * @param X-AUTH-TOKEN
	 * @param int msrl
	 * @param String name
	 * @return SingleResult
	 */
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
    @PutMapping(value = "/user")
    public SingleResult<User> modify( @ApiParam(value = "회원번호", required = true) @RequestParam int msrl,
            						  @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
    	User user = new User();
    	
//        User user = User.builder()
//                .msrl(msrl)
//                .name(name)
//                .build();
        
        return responseService.getSingleResult(userRepository.save(user));
    }

    /**
	 * 회원 삭제
	 * 
	 * @param X-AUTH-TOKEN
	 * @param int msrl
	 * @return SingleResult
	 */
    @ApiOperation(value = "회원 삭제", notes = "회원정보를 삭제한다")
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete( @ApiParam(value = "회원번호", required = true) @PathVariable int msrl) {
    	userRepository.deleteById(msrl);
    	
        return responseService.getSuccessResult();
    }
}