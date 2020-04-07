package com.myapp.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
    private final RedisTemplate<String, String> redisTemplate;
	
	/**
	 * 로그인
	 * 
	 * @param String id
	 * @param String password
	 * @return Map<String, String>
	 */
	public void login(String id, String password, HttpServletResponse response) {
		//입력받은 ID로 유저를 가져온다
        User user = userService.findUser(id);
        
        //입력된 password가 틀릴 시 LoginFailedException 발생
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginFailedException();
        }
        
        //로그인이 성공하면 X-AUTH-TOKEN, X-AUTH-REFRESH-TOKEN을 세팅해줌
        setCookie(response, "X-AUTH-TOKEN", jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), "access"));
        setCookie(response, "X-AUTH-REFRESH-TOKEN", jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), "refresh"));
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
    	Cookie[] cookies = request.getCookies();
    	
    	System.out.println("logoutService cookies : " + cookies);
    	
    	for(Cookie cookie : cookies) {
    		System.out.println("cookie name : " + cookie.getName() + ", value : " + cookie.getValue());
    	}
    	
//		String accessToken = jwtTokenProvider.resolveAccessToken(request);
//		String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
//		
//		ValueOperations<String, String> vop = redisTemplate.opsForValue();
//        vop.set("access-" + jwtTokenProvider.getUserPk(accessToken, "access"), accessToken);
//        vop.set("refresh-" + jwtTokenProvider.getUserPk(refreshToken, "refresh"), refreshToken);
	}
	
	/**
	 * 쿠키 세팅
	 * 
	 * @param HttpServletRequest response
	 * @param String name
	 * @param String value
	 * @return 
	 */
	public void setCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(60 * 60 * 24* 14);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
	}
}
