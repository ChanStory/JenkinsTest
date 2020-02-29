package com.myapp.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.UserNotFoundException;
import com.myapp.advice.exception.ValidNotMatchException;
import com.myapp.dao.UserRepository;
import com.myapp.entity.User;

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
	 * @exception ValidNotMatchException
	 */
	public void join(User user, List<String> roles) {
		String password = user.getPassword();
		
		//User 객체에서 어노테이션으로 유효성 검사를 실행하면 passwordEncoder.encode 했을시에 예외가 떨어져 따로 검사함
		validationCheck(password, "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$"); //영어 대.소문자, 숫자, 특수문자를 1개이상씩 포함시켜 8~15자리
        
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
	 * @exception ValidNotMatchException
	 */
	public void userUpdate(int msrl, Map<String, String> updateMap) {
		User updateUser= findUser();
		boolean isAdmin = false;
		
		//관리자 권한인지 체크함
		for(String role : updateUser.getRoles()) {
			if(role.equals("ADMIN")) isAdmin = true;
			
			//권한 변경은 관리자만 할수있도록 함
			if(updateMap.get("role") != null) {
				updateUser.setRoles(Collections.singletonList(updateMap.get("role")));
			}
		}
		
		//관리자가 아니면서 다른회원의 정보를 수정하려할 시 AccessDeniedException 발생
		if(updateUser.getMsrl() != msrl && !isAdmin) {
			throw new AccessDeniedException("");
		}
		
		//변경을 요청한 값만 유효성 검사 후 변경 해줌 (이름, 주소는 유효성 검사를 실행하지 않음)
		if(updateMap.get("name") != null) {
			updateUser.setName(updateMap.get("name"));
		}
		if(updateMap.get("email") != null) {
			validationCheck(updateMap.get("email"), "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]+$");
			updateUser.setEmail(updateMap.get("email"));
		}
		if(updateMap.get("address") != null) {
			updateUser.setAddress(updateMap.get("address"));
		}
		if(updateMap.get("password") != null) {
			validationCheck(updateMap.get("password"), "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$"); //영어 대.소문자, 숫자, 특수문자를 1개이상씩 포함시켜 8~15자리
			updateUser.setPassword(passwordEncoder.encode(updateMap.get("password")));
		}
		if(updateMap.get("phoneNumber") != null) {
			validationCheck(updateMap.get("phoneNumber"), "^01(?:0|1|[6-9])(\\\\d{3}|\\\\d{4})(\\\\d{4})$");
			updateUser.setPhoneNumber(updateMap.get("phoneNumber"));
		}
		
		userRepository.save(updateUser);
	}
	
	/**
	 * 유효성 검사
	 * @param String str
	 * @param String regex
	 * @return 
	 * @exception ValidNotMatchException
	 */
	public void validationCheck(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
		
        if(!matcher.find()) {
        	throw new ValidNotMatchException();
        }
	}
}