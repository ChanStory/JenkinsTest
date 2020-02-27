package com.myapp.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.MethodParameter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.myapp.advice.exception.PasswordNotMatchException;
import com.myapp.dao.UserRepository;
import com.myapp.object.User;

import lombok.RequiredArgsConstructor;


/**
 * 유저 관련 서비스
 * @author chans
 */

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
	/**
	 * 회원가입
	 * @param User user
	 * @param List<String> roles
	 * @return 
	 */
	public void join(User user, List<String> roles) {
		String password = user.getPassword();
		
		//User 객체에서 어노테이션으로 유효성 검사를 실행하면 passwordEncoder.encode 했을시에 예외가 떨어져 따로 검사함
		Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$"); //영어 대.소문자, 숫자, 특수문자를 1개이상씩 포함시켜 8~15자리
        Matcher matcher = pattern.matcher(password);
		
        if(!matcher.find()) {
        	throw new PasswordNotMatchException();
        }
        
		user.setPassword(passwordEncoder.encode(password));
		user.setRoles(roles);
		userRepository.save(user);
	}

}