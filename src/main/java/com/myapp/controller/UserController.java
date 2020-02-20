package com.myapp.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;


@Api(tags = {"1. User"})
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {

	private final UserService userService;	
	
	@ApiOperation(value = "ID 중복체크", notes = "가입하려는 ID의 중복을 체크한다")
	@GetMapping("/id-duplicate/{id}")
	public JSONObject idDuplicateCheck(@ApiParam(value = "회원아이디", required = true) @PathVariable String id) {
		return userService.idDuplicateCheck(id);
	}
	
	@ApiOperation(value = "로그인", notes = "로그인을 한다")
	@GetMapping("/login")
	public JSONObject login(@RequestParam Map params) {
		return userService.login(params);
	}
	
	@ApiOperation(value = "회원 전체조회", notes = "모든 회원을 조회한다")
	@GetMapping("/user")
	public JSONObject findAllUsers() {
		return userService.findAllUsers();
	}
	
	@ApiOperation(value = "회원 가입", notes = "회원을 입력한다")
	@PostMapping("/user")
	public JSONObject join(@RequestBody Map body) {
		return userService.join(body);
	}
}