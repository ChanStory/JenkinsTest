package com.myapp.jpaRepository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.jpa.Member;

public interface MemberRepository extends JpaRepository<Member, Long>
{

}