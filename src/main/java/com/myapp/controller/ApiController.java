package com.myapp.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.repository.UserRepository;
import com.myapp.service.LoginService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApiController {

	@Autowired
	LoginService loginService;
	
	
	
	@RequestMapping("/id-duplicate")
	public JSONObject idDuplicateCheck(@RequestParam Map params) {
		
		return loginService.idDuplicateCheck((String) params.get("joinId"));
	}
	
	@RequestMapping("/login")
	public JSONObject login() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("test", "Hello");
		return jsonObject;
	}
	
	@RequestMapping("/join")
	public JSONObject join(@RequestParam Map params) {
		JSONObject jsonObject = new JSONObject();
		loginService.join(params);
		
		return jsonObject;
	}
}
