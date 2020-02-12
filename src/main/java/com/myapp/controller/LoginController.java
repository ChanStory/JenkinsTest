package com.myapp.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.service.LoginService;


/**
 * 로그인 관련 컨트롤러
 * @author chans
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
@RestController
@RequestMapping("/")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	//id 중복체크
	@GetMapping("/id-duplicate")
	public JSONObject idDuplicateCheck(@RequestParam Map params) {
		return loginService.idDuplicateCheck((String) params.get("joinId"));
	}
	
	//로그인
	@GetMapping("/login")
	public JSONObject login(@RequestParam Map params) {
		return loginService.login(params);
	}
	
	//회원가입
	@PostMapping("/user")
	public JSONObject join(@RequestBody Map body) {
		return loginService.join(body);
	}
}