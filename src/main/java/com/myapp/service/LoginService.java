package com.myapp.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.object.User;
import com.myapp.repository.UserRepository;

import java.util.*;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;
	
	public JSONObject login() {
		JSONObject resultJson = new JSONObject();
		
		return resultJson;
	}
	
	public JSONObject join(Map params) {
		JSONObject resultJson = new JSONObject();
		
		return resultJson;
	}
	
	public JSONObject idDuplicateCheck(String id) {
		JSONObject result = new JSONObject();
		Optional<User> userOptional = userRepository.findById(id);
		
		if(userOptional.isPresent()) {
			result.put("duplicateResult", "canNotUsed");
		}else {
			result.put("duplicateResult", "available");
		}

		return result;
	}
}
