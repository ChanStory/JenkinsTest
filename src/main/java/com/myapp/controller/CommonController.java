package com.myapp.controller;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.mysqlObject.Address;
import com.myapp.mysqlObject.Book;
import com.myapp.mysqlObject.Member;
import com.myapp.mysqlObject.Orders;
import com.myapp.mysqlObject.Period;
import com.myapp.mysqlObject.Product;
import com.myapp.mysqlObject.Team;
import com.myapp.repository.mysqlRepository.MemberRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class CommonController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@RequestMapping("/")
	public String common() {
		return "common/main";
	}
	
	@RequestMapping("/react")
	public String react() {
		return "common/react";
	}
	
	@RequestMapping("/jpatest/")
	public String jpaTest() {
		
		Member member = new Member();
        member.setUsername("태찬");
        member.setAge(25);
        
        Period period = new Period();
        period.setStartDate(new Date());
        
        Address address = new Address();
        address.setCity("부천시");
        address.setStreet("계남로 196");
        
        member.setWorkPeriod(period);
        member.setHomeAddress(address);
        
        memberRepository.save(member);
        //em.persist(member);
        
        Optional<Member> findMember = memberRepository.findById((long) 1);
        
        System.out.println("findMember startDate : " + findMember.get().getWorkPeriod().getStartDate());
        System.out.println("findMember address : " + findMember.get().getHomeAddress().getCity() + " " + findMember.get().getHomeAddress().getStreet());
		/*
		 * //엔티티 매니저 팩토리 생성 EntityManagerFactory emf =
		 * Persistence.createEntityManagerFactory("myapp"); EntityManager em =
		 * emf.createEntityManager(); //엔티티 매니저 생성
		 * 
		 * EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득
		 * 
		 * try {
		 * 
		 * 
		 * tx.begin(); //트랜잭션 시작 embeddedLogic(em); //비즈니스 로직 tx.commit();//트랜잭션 커밋
		 * 
		 * } catch (Exception e) { e.printStackTrace(); tx.rollback(); //트랜잭션 롤백 }
		 * finally { em.close(); //엔티티 매니저 종료 }
		 * 
		 * emf.close(); //엔티티 매니저 팩토리 종료
		 */        
		return "jpa/jpaTest";
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
	
	public static void inheritanceLogic(EntityManager em) {
		Book book = new Book();
		book.setAUTHOR("김영한");
		book.setName("JPA프로그래밍");
		book.setPrice(38000);
		
		em.persist(book);
		em.flush();
		
		em.clear();
		Book findBook = em.find(Book.class, (long)1);
		
		System.out.println("Book name : " + findBook.getName());
		System.out.println("Book Author : " + findBook.getAUTHOR());
	}
	
	public static void embeddedLogic(EntityManager em) {
		//멤버 추가
        Member member = new Member();
        member.setUsername("태찬");
        member.setAge(25);
        
        Period period = new Period();
        period.setStartDate(new Date());
        
        Address address = new Address();
        address.setCity("부천시");
        address.setStreet("계남로 196");
        
        member.setWorkPeriod(period);
        member.setHomeAddress(address);
        
        em.persist(member);
        
        Member findMember = em.find(Member.class, (long)1);
        
        System.out.println("findMember startDate : " + findMember.getWorkPeriod().getStartDate());
        System.out.println("findMember address : " + findMember.getHomeAddress().getCity() + " " + findMember.getHomeAddress().getStreet());
	}
}
