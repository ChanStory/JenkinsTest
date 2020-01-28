package com.myapp.repository.mysqlRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.mysqlObject.Member;
public interface MemberRepository extends JpaRepository<Member, Long>
{

}