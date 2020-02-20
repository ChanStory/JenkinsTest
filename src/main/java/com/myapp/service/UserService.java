package com.myapp.service;

import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.dao.UserRepository;
import com.myapp.object.User;


/**
 * 유저 관련 서비스
 * @author chans
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 로그인
	 * @param loginMap
	 * @return resultJson
	 */
	public JSONObject login(Map<String, String> loginMap) {
		JSONObject resultJson = new JSONObject();
		String id = loginMap.get("loginId");
		String password = loginMap.get("loginPassword");
		
		//user객체를 id로 검색 없을 시 null
		/*
		 * User user = userRepository.findById(id).orElse(null);
		 * 
		 * if(user != null) { if(user.getPassword().equals(password)) {
		 * resultJson.put("result", "success"); }else { resultJson.put("result",
		 * "notMatch"); }
		 * 
		 * }else { resultJson.put("result", "doesNotExist"); }
		 */
		
		return resultJson;
	}
	
	/**
	 * 회원가입
	 * @param userMap
	 * @return resultJson
	 */
	public JSONObject join(Map<String, String> userMap) {
		User joinUser = new User();
		userRepository.save(joinUser);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", "success");
		
		return resultJson;
	}
	
	/**
	 * id 중복체크
	 * @param idString
	 * @return resultJson
	 */
	public JSONObject idDuplicateCheck(String idString) {
		JSONObject resultJson = new JSONObject();
		/*
		 * Optional<User> userOptional = userRepository.findById(idString);
		 * 
		 * if(userOptional.isPresent()) { resultJson.put("duplicateResult",
		 * "canNotUsed"); }else { resultJson.put("duplicateResult", "available"); }
		 */

		return resultJson;
	}

	public JSONObject findAllUsers() {
		
		return null;
	}
}