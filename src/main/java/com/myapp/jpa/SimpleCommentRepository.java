package com.myapp.jpa;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleCommentRepository extends JpaRepository<SimpleComment, Long>
{

}