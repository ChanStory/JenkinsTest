package com.myapp.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApiController {

	
	@RequestMapping("/rest")
	public JSONObject restTest() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("test", "Hello");
		/*
		 * JSONArray req_array = new JSONArray(); req_array.add(jsonObject);
		 */
		return jsonObject;
	}
}
