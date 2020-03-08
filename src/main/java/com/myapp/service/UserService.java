package com.myapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.EmptyResultDataAccessException;
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
	 * 회원 조회
	 * @param String uid
	 * @return User
	 */
	public User findUser(String uid) {       
		return userRepository.findByUid(uid).orElseThrow(UserNotFoundException::new);
	}
	
	/**
	 * 회원 수정
	 * @param long msrl
	 * @param Map<String, String> updateMap
	 * @return 
	 * @exception ValidNotMatchException
	 */
	public void modifyUser(long msrl, Map<String, String> updateMap) {
		User requestUser = findUser();
		User modifyUser = userRepository.findById(msrl).orElseThrow(UserNotFoundException::new);
		
		//관리자 권한인지 체크함
		boolean isAdmin = checkAuthAdmin(requestUser);
		
		//관리자가 아니면서 다른회원의 정보를 수정하려할 시 AccessDeniedException 발생
		if(requestUser.getMsrl() != msrl && !isAdmin) {
			throw new AccessDeniedException("");
		}
		
		//변경을 요청한 값만 변경 해줌
		if(updateMap.get("name") != null) 		 modifyUser.setName(updateMap.get("name"));
		if(updateMap.get("email") != null) 		 modifyUser.setEmail(updateMap.get("email"));
		if(updateMap.get("address") != null) 	 modifyUser.setAddress(updateMap.get("address"));
		if(updateMap.get("phoneNumber") != null) modifyUser.setPhoneNumber(updateMap.get("phoneNumber"));
		if(updateMap.get("password") != null) {
			//User 객체에서 어노테이션으로 유효성 검사를 실행하면 passwordEncoder.encode 했을시에 예외가 떨어져 따로 검사함
			validationCheck(updateMap.get("password"), "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$"); //영어 대.소문자, 숫자, 특수문자를 1개이상씩 포함시켜 8~15자리
			modifyUser.setPassword(passwordEncoder.encode(updateMap.get("password")));
		}
		
		//권한변경은 관리자만 가능
		if(isAdmin && updateMap.get("role") != null) {
			//immutable한 리스트를 생성하면 UnsupportedOperationException 발생.. 회원가입때는 이상없었는데 수정할때는 문제가 생김
			List<String> roles = new ArrayList<String>();
			roles.add(updateMap.get("role"));
			modifyUser.setRoles(roles);
		}
		
		//저장할 때 user에서 어노테이션으로 설정해준 유효성 검사 실행
		userRepository.save(modifyUser);
	}
	

	/**
	 * 회원 삭제
	 * @param long msrl
	 * @return 
	 * @exception UserNotFoundException
	 */
	public void deleteUser(long msrl) {
		
		//삭제 시 회원번호에 맞는 회원이 없으면 UserNotFoundException 발생
    	try {
    		userRepository.deleteById(msrl);
		} catch (EmptyResultDataAccessException ex) {
			throw new UserNotFoundException();
		}
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

	/**
	 * 관리자 권한체크
	 * @param User user
	 * @return boolean
	 */
	public boolean checkAuthAdmin(User user) {
		
		for(String role : user.getRoles()) {
			if(role.equals("ROLE_ADMIN")) return true;
		}
		
		return false;
	}
}