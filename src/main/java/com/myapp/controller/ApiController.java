package com.myapp.controller;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.dto.JoinData;
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
	public JSONObject join(HttpServletRequest request) {
		JoinData joinData = null;
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			joinData = mapper.readValue(request.getReader().lines().collect(Collectors.joining()), JoinData.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loginService.join(joinData);
	}
}
