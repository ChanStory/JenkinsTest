package com.myapp.repository.mysqlRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.mysqlObject.User;
public interface MemberRepository extends JpaRepository<User, Long>
{

}