
package com.myapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.UserNotFoundException;
import com.myapp.dao.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * CustomUserDetailService
 * 
 * @author chans
 */

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	/**
	 * 유저 ID로 유저를 검색
	 * 
	 * @param String uid
	 * @return UserDetails
	 */
	public UserDetails loadUserByUsername(String uid) {
		return userRepository.findByUid(uid).orElseThrow(UserNotFoundException::new);
	}
}
