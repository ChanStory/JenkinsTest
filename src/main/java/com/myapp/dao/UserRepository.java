package com.myapp.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.object.User;
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUid(String uid);
}