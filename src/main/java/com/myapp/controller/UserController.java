package com.myapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Api(tags = {"2. User"})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {
 
    private final UserRepository userRepository;
    private final ResponseService responseService; // 결과를 처리할 Service
 
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
        return responseService.getListResult(userRepository.findAll());
    }
 
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "회원번호(msrl)로 회원을 조회한다")
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById() {
    	if(log.isInfoEnabled()) log.info("findUserById in");
    	
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(log.isDebugEnabled()) log.debug("authentication 생성");
        
        String id = authentication.getName();
        
        if(log.isDebugEnabled()) log.debug("id : " + id);
        
        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userRepository.findByUid(id).orElseThrow(UserNotFoundException::new));
    }
 
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam int msrl,
            @ApiParam(value = "회원이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .msrl(msrl)
                .name(name)
                .build();
        return responseService.getSingleResult(userRepository.save(user));
    }
 
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable int msrl) {
    	userRepository.deleteById(msrl);
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }
}