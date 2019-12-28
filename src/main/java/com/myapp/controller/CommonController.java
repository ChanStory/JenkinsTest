package com.myapp.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.jpa.Member;

@Controller
public class CommonController {
	
	@RequestMapping("/")
	public String common() {
		return "redirect:/SimpleComment/";
	}
	
	@RequestMapping("/jpatest/")
	public String jpaTest() {
		
		//엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myapp");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {


            tx.begin(); //트랜잭션 시작
            logic(em);  //비즈니스 로직
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
        
		return "SimpleComment/jpaTest";
	}
	
	
	public static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("jihan");
        member.setAge(2);
System.out.println("log1" + member.getId() + member.getUsername() + member.getAge());
        //등록
        em.persist(member);
        System.out.println("log2" + member.getId() + member.getUsername() + member.getAge());
        //수정
        member.setAge(20);
        System.out.println("log3" + member.getId() + member.getUsername() + member.getAge());
        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());
        System.out.println("log4" + member.getId() + member.getUsername() + member.getAge());
        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size=" + members.size());
        System.out.println("log5" + member.getId() + member.getUsername() + member.getAge());
        //삭제
        em.remove(member);

    }
}
