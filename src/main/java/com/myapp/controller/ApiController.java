package com.myapp.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.service.LoginService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApiController {

	@Autowired
	LoginService loginService;
	
	@RequestMapping("/rest")
	public JSONObject restTest() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("test", "Hello");
		/*
		 * JSONArray req_array = new JSONArray(); req_array.add(jsonObject);
		 */
		return jsonObject;
	}
	
	@RequestMapping("/login")
	public JSONObject login() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("test", "Hello");
		return jsonObject;
	}
}
