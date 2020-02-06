package com.myapp.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.object.User;
public interface UserRepository extends JpaRepository<User, String>
{

}