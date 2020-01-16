package com.myapp.repository.h2Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.h2Object.Member;

public interface MemberRepository extends JpaRepository<Member, Long>
{

}