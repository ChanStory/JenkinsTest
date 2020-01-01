package com.myapp.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.jpa.Member;
import com.myapp.jpa.Team;

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

        Long id = (long) 1;
        Member member1 = new Member();
        member1.setUsername("지한");
        member1.setAge(2);
        
        Member member2 = new Member();
        member2.setUsername("태찬");
        member2.setAge(25);
        
        //팀정보 추가
        Team team1 = new Team();
        team1.setId("team1");
        team1.setName("팀1");
        em.persist(team1);
        
        Team team2 = new Team();
        team2.setId("team2");
        team2.setName("팀2");
        em.persist(team2);
        
        member1.setTeam(team1);
        member2.setTeam(team1);
        //등록
        em.persist(member1);
        em.persist(member2);
        
        //수정
        member1.setTeam(team2);
        //한 건 조회
		/*
		 * Member findMember = em.find(Member.class, id);
		 * System.out.println("findMember=" + findMember.getUsername() + ", age=" +
		 * findMember.getAge());
		 */
        //목록 조회
        String jpql = "select m from Member m join m.team t where t.name=:teamName";
        List<Member> list = em.createQuery(jpql, Member.class).setParameter("teamName", "팀2").getResultList();
        
        for(Member member: list) {
        	System.out.println("member name : " + member.getUsername());
        }
        //삭제
        //em.remove(member);

    }
}
