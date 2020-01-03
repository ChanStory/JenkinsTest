package com.myapp.controller;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.jpa.Member;
import com.myapp.jpa.Orders;
import com.myapp.jpa.Product;
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

		//멤버 추가
        Member member = new Member();
        member.setUsername("태찬");
        member.setAge(25);
        
        //팀정보 추가
		Team team1 = new Team(); 
		team1.setId("team1"); 
		team1.setName("팀1");
		em.persist(team1);
		 
        member.setTeam(team1);
        em.persist(member);
        
        //상품 추가
        Product product = new Product();
        
        product.setId("상품1");
        product.setName("상품이름1");
        
        em.persist(product);
        
        //주문 추가
        Orders order = new Orders();
        order.setMember(member);
        order.setProduct(product);
        order.setOrderAmount(1);
        
        em.persist(order);
        
        //주문 출력
		
        Orders findOrder = em.find(Orders.class, (long)1);
		  
        Member findMember = findOrder.getMember(); Product findProduct =
        		findOrder.getProduct();
		  
        System.out.println("orderUserName : " + findMember.getUsername());
        System.out.println("orderProduct : " + findProduct.getName());
        System.out.println("orderAmount : " + findOrder.getOrderAmount());
		
	}
}
