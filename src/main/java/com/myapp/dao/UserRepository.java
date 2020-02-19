package com.myapp.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.object.User;
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUid(String email);
}