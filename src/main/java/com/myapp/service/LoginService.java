package com.myapp.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.common.RoleType;
import com.myapp.dto.JoinData;
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
	
	public JSONObject join(JoinData joinData) {
		JSONObject resultJson = new JSONObject();
		
		Date nowDate = new Date();
		
		User joinUser = new User();
		joinUser.setId(joinData.getJoinId());
		joinUser.setUsername(joinData.getName());
		joinUser.setPassword(joinData.getJoinPassword());
		joinUser.setBirthDate(joinData.getBirthDate());
		joinUser.setPhoneNumber(joinData.getPhoneNumber());
		joinUser.setAddress(joinData.getAddress());
		joinUser.setEmail(joinData.getEmail());
		joinUser.setRoleType(RoleType.USER);
		joinUser.setCreatedDate(nowDate);
		joinUser.setLastModifiedDate(nowDate);
		
		userRepository.save(joinUser);
		
		resultJson.put("result", "success");
		
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