package com.myapp.service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.common.RoleType;
import com.myapp.object.User;
import com.myapp.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;
	
	public JSONObject login(Map<String, String> map) {
		JSONObject resultJson = new JSONObject();
		String id = map.get("loginId");
		String password = map.get("loginPassword");
		
		User user = userRepository.findById(id).orElse(null);
		
		if(user != null) {
			if(user.getPassword().equals(password)) {
				resultJson.put("result", "success");
			}else {
				resultJson.put("result", "notMatch");
			}
			
		}else {
			resultJson.put("result", "doesNotExist");
		}
		
		return resultJson;
	}
	
	public JSONObject join(Map<String, String> map) {
		JSONObject resultJson = new JSONObject();
		
		Date nowDate = new Date();
		
		User joinUser = new User();
		joinUser.setId(map.get("joinId"));
		joinUser.setUsername(map.get("name"));
		joinUser.setPassword(map.get("joinPassword"));
		joinUser.setBirthDate(map.get("birthDate"));
		joinUser.setPhoneNumber(map.get("phoneNumber"));
		joinUser.setAddress(map.get("address"));
		joinUser.setEmail(map.get("email"));
		joinUser.setRoleType(RoleType.USER);
		joinUser.setCreatedDate(nowDate);
		joinUser.setLastModifiedDate(nowDate);
		
		userRepository.save(joinUser);
		
		resultJson.put("result", "success");
		
		return resultJson;
	}
	
	public JSONObject idDuplicateCheck(String id) {
		JSONObject resultJson = new JSONObject();
		Optional<User> userOptional = userRepository.findById(id);
		
		if(userOptional.isPresent()) {
			resultJson.put("duplicateResult", "canNotUsed");
		}else {
			resultJson.put("duplicateResult", "available");
		}

		return resultJson;
	}
}