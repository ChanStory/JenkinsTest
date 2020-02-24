package com.myapp.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.object.User;

/**
 * 회원 관련 Reprository
 * 
 * @author chans
 */

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUid(String uid);
}