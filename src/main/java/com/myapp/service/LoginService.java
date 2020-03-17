package com.myapp.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.LoginFailedException;
import com.myapp.entity.User;
import com.myapp.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * 로그인 관련 서비스
 * @author chans
 */

@RequiredArgsConstructor
@Service
public class LoginService {

	private final UserService userService;
	
    private final PasswordEncoder passwordEncoder;
    
    private final JwtTokenProvider jwtTokenProvider;
    
    private final RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 로그인
	 * 
	 * @param String id
	 * @param String password
	 * @return Map<String, String>
	 */
	public Map<String, String> login(String id, String password) {
		//입력받은 ID로 유저를 가져온다
        User user = userService.findUser(id);
        
        //입력된 password가 틀릴 시 LoginFailedException 발생
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginFailedException();
        }
        
        //access토큰과 refresh토큰 두개를 발급해줌
        Map<String, String> jwtMap = new HashMap<String, String>();
        jwtMap.put("access", jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), "access"));
        jwtMap.put("refresh", jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), "refresh"));
        
        return jwtMap;
	}

	/**
	 * 리프레쉬 토큰 로그인
	 * 
	 * @param HttpServletRequest request
	 * @return Map<String, String>
	 */
	public Map<String, String> refreshLogin(HttpServletRequest request) {
		User user = userService.findUser();
		
		Map<String, String> jwtMap = new HashMap<String, String>();
        jwtMap.put("access", jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), "access"));
		
		return jwtMap;
	}
	
	/**
	 * 로그아웃
	 * 
	 * @param HttpServletRequest request
	 * @return 
	 */
	public void logout(HttpServletRequest request) {
		String token = jwtTokenProvider.resolveToken(request);
		
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        //vop.set("test", token);
        System.out.println((String)vop.get("test"));
	}
}
