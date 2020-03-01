package com.myapp.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entity.User;

/**
 * 회원 관련 Reprository
 * 
 * @author chans
 */

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUid(String uid);
}