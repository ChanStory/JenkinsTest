package com.myapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.LoginFailedException;
import com.myapp.advice.exception.UserNotFoundException;
import com.myapp.dao.UserRepository;
import com.myapp.object.User;
import com.myapp.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * 로그인 관련 서비스
 * @author chans
 */

@RequiredArgsConstructor
@Service
public class LoginService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
	
	/**
	 * 로그인
	 * @param String id
	 * @param String password
	 * @return String jwtToken
	 */
	public String login(String id, String password) {
		
		//입력받은 ID로 유저를 가져온다. 해당ID 유저가 없을 시 UserNotFoundException 발생
        User user = userRepository.findByUid(id).orElseThrow(UserNotFoundException::new);
        
        //입력된 password가 틀릴 시 LoginFailedException 발생
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new LoginFailedException();
        
        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
	}
}
