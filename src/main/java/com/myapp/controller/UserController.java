package com.myapp.controller;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.access.AccessDeniedException;
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

import com.myapp.common.CommonResult;
import com.myapp.common.ListResult;
import com.myapp.common.SingleResult;
import com.myapp.entity.User;
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
    @PostMapping(value = "/join") //회원가입은 권한이 없는 사람도 접근이 가능해야 해서 join으로 매핑
    public CommonResult join( @ApiParam(value = "회원가입시 필요한 데이터", required = true) @Valid @RequestBody User user) {
    	
    	userService.join(user, Collections.singletonList("ROLE_USER"));
        
    	return responseService.getSuccessResult();
    }
    
    /**
	 * id중복체크
	 * 
	 * @param id
	 * @return CommonResult
	 */
    @ApiOperation(value = "id중복체크", notes = "id중복여부를 검사 한다")
    @GetMapping(value = "/id-duplicate-check") //회원가입은 권한이 없는 사람도 접근이 가능해야 해서 join으로 매핑
    public CommonResult idDuplicateCheck( @ApiParam(value = "중복체크할 id", required = true) @RequestParam String id) {
    	userService.idDuplicateCheck(id);
        
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
    public ListResult<User> findAllUsers() {
        return responseService.getListResult(userService.findAllUsers());
    }
 
    /**
	 * 회원 조회
	 * 
	 * @param X-AUTH-TOKEN
	 * @return SingleResult
	 */
    @ApiOperation(value = "회원 단건 조회", notes = "토큰으로 인증된 회원을 조회한다")
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById() {
        return responseService.getSingleResult(userService.findUser());
    }
 
    /**
	 * 회원 수정
	 * 
	 * @param X-AUTH-TOKEN
	 * @param long msrl
	 * @param String name
	 * @param String password
	 * @param String phoneNumber
	 * @param String address
	 * @param String email
	 * @param String role
	 * @return SingleResult
	 */
    //swagger에서 파라미터를 map 객체로 받는것을 지원하지 않아 여기 선언 후 updateMap으로 받음
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
    					 @ApiImplicitParam(name = "name", value = "회원이름", required = false, dataType = "String", paramType = "query"),
    					 @ApiImplicitParam(name = "password", value = "비밀번호", required = false, dataType = "String", paramType = "query"),
    					 @ApiImplicitParam(name = "phoneNumber", value = "휴대폰번호", required = false, dataType = "String", paramType = "query"),
    					 @ApiImplicitParam(name = "address", value = "주소", required = false, dataType = "String", paramType = "query"),
    					 @ApiImplicitParam(name = "email", value = "이메일", required = false, dataType = "String", paramType = "query"),
    					 @ApiImplicitParam(name = "role", value = "권한", required = false, dataType = "String", paramType = "query")})
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user/{msrl}")
	public CommonResult modify( @ApiParam(value = "회원번호", required = true) @PathVariable long msrl,
								@ApiParam(hidden = true) @RequestParam Map<String, String> updateMap) {
    	
    	if(log.isDebugEnabled()) {
    		log.debug("user update parameter : {}", updateMap);
    	}
    	
    	userService.modifyUser(msrl, updateMap);
        
        return responseService.getSuccessResult();
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
    public CommonResult delete( @ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
    	
    	//관리자의 요청이 아니라면 AccessDeniedException 발생
    	if(!userService.checkAuthAdmin(userService.findUser())) {
    		throw new AccessDeniedException("");
    	}
    	
    	userService.deleteUser(msrl);
    	
        return responseService.getSuccessResult();
    }
}