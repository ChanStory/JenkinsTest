
package com.myapp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.UserNotFoundException;
import com.myapp.dao.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetails loadUserByUsername(String userPk) {
		return userRepository.findById(Integer.valueOf(userPk)).orElseThrow(UserNotFoundException::new);
	}
}
