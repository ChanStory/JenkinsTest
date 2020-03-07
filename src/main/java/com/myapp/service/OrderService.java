package com.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myapp.advice.exception.UserNotFoundException;
import com.myapp.dao.OrderReprository;
import com.myapp.dto.OrderParams;
import com.myapp.entity.Order;
import com.myapp.entity.User;

import lombok.RequiredArgsConstructor;



/**
 * 상품 관련 서비스
 * 
 * @author chans
 */
@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderReprository orderRepository;
	
	private final UserService userService;
	
	/**
	 * 주문 전체 조회
	 * @param 
	 * @return List<Product>
	 */
	public List<Order> findAllOrders() {
		return orderRepository.findAll();
	}
	
	/**
	 * 회원 주문내역 조회
	 * @param long msrl
	 * @return List<Product>
	 */
	public List<Order> findOrders(long msrl) {
		User user = userService.findUser();
		
		return orderRepository.findByUser(user).orElseThrow(UserNotFoundException::new);
	}

	/**
	 * 주문 추가
	 * @param OrderParams orderParams
	 * @return 
	 */
	public void addOrder(OrderParams orderParams) {
	}
}