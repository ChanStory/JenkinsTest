package com.myapp.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.PasswordNotMatchException;
import com.myapp.advice.exception.UserNotFoundException;
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

	/**
	 * 전체 회원 조회
	 * @param 
	 * @return List<User>
	 */
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * 회원 조회
	 * @param 
	 * @return User
	 */
	public User findUser() {
		// SecurityContext에서 인증받은 회원의 정보를 얻어온다
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
       
		return userRepository.findByUid(id).orElseThrow(UserNotFoundException::new);
	}

	/**
	 * 회원 수정
	 * @param int msrl
	 * @param Map<String, String> updateMap
	 * @return 
	 */
	public void userUpdate(int msrl, Map<String, String> updateMap) {
		User updateUser= findUser();
		boolean isAdmin = false;
		
		//운영자 권한인지 체크함
		for(String role : updateUser.getRoles()) {
			if(role.equals("ADMIN")) isAdmin = true;
		}
		
		//운영자가 아니면서 다른회원의 정보를 수정하려할 시 AccessDeniedException 발생
		if(updateUser.getMsrl() != msrl && !isAdmin) {
			throw new AccessDeniedException("");
		}
		
		
		//userRepository.save(updateUser);
	}

}