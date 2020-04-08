package com.myapp.service;

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
	 * @return 
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
	 * @return 
	 */
	public void refreshLogin(HttpServletResponse response) {
		User user = userService.findUser();
		
		setCookie(response, "X-AUTH-TOKEN", jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), "access"));
	}
	
	/**
	 * 로그아웃
	 * 
	 * @param HttpServletRequest request
	 * @return 
	 */
	public void logout(HttpServletRequest request) {
    	Cookie[] cookies = request.getCookies();
    	
    	ValueOperations<String, String> vop = redisTemplate.opsForValue();
    	
    	//access, refresh token을 redis에 저장
    	for(Cookie cookie : cookies) {
    		String cookieValue = cookie.getValue();
    		
    		if(cookie.getName().equals("X-AUTH-TOKEN")) {
    			vop.set("access-" + jwtTokenProvider.getUserPk(cookieValue, "access"), cookieValue);
    		}else if(cookie.getName().equals("X-AUTH-REFRESH-TOKEN")) {
    			vop.set("refresh-" + jwtTokenProvider.getUserPk(cookieValue, "refresh"), cookieValue);
    		}
    	}
    	
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
