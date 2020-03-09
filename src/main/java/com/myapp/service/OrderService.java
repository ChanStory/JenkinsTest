package com.myapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.OrderNotFoundException;
import com.myapp.advice.exception.ProductNotFoundException;
import com.myapp.advice.exception.UserNotFoundException;
import com.myapp.dao.OrderReprository;
import com.myapp.dto.OrderParams;
import com.myapp.dto.OrderProductParams;
import com.myapp.entity.Delivery;
import com.myapp.entity.Delivery.DeliveryStatus;
import com.myapp.entity.Order;
import com.myapp.entity.Order.OrderStatus;
import com.myapp.entity.OrderItem;
import com.myapp.entity.Product;
import com.myapp.entity.User;

import lombok.RequiredArgsConstructor;



/**
 * 주문 관련 서비스
 * 
 * @author chans
 */
@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderReprository orderRepository;
	
	private final UserService userService;
	
	private final ProductService productService;
	
	/**
	 * 주문 전체 조회
	 * 
	 * @param 
	 * @return List<Product>
	 */
	public List<Order> findAllOrders() {
		return orderRepository.findAll();
	}
	
	/**
	 * 회원 주문내역 조회
	 * 
	 * @param long msrl
	 * @return List<Product>
	 * @exception UserNotFoundException
	 */
	public List<Order> findOrders(long msrl) {
		User user = userService.findUser();
		
		//관리자가 아닌 유저가 다른 회원의 주문내역을 조회하려고 할때
		if(user.getMsrl() != msrl && !userService.checkAuthAdmin(user)) {
			throw new AccessDeniedException("");
		}
		
		List<Order> list = orderRepository.findByUser(user).orElseThrow(UserNotFoundException::new);
		
		for(Order order : list) {
			
			System.out.println(order);
		}
		
		return list;
	}

	/**
	 * 주문 추가
	 * 
	 * @param OrderParams orderParams
	 * @return 
	 */
	public void addOrder(OrderParams orderParams) {
		//user객체 생성
		User user = userService.findUser(orderParams.getOrderUserId());
			
		//delivery 객체 생성
		Delivery delivery = Delivery.builder().address(orderParams.getAddress())
			.status(Delivery.DeliveryStatus.READY)
			.build();
		
		//order객체 생성
		Order order = Order.builder().user(user)
            .status(Order.OrderStatus.ORDER)
            .build();
		
		//Delivery객체에도 연관관계를 설정해주기 위해 set메소드를 사용함
		order.setDelivery(delivery);
				
		//파라미터로 받아온 orderItem들을 추가해줌
		for(OrderProductParams productParams : orderParams.getProductList()) {
			
			Product product = productService.findProduct(productParams.getProductId());
			
			OrderItem orderItem = OrderItem.builder()
				.count(productParams.getProductCount())
				.orderPrice(product.getPrice() * productParams.getProductCount())
				.build();
			
			orderItem.setProduct(product);
			order.addOrderItem(orderItem);
		}
		
		orderRepository.save(order);
	}
	
	/**
	 * 주문 내용 수정
	 * 
	 * @param long id
	 * @param String address
	 * @param List<OrderProductParams> productList
	 * @return 
	 * @exception ProductNotFoundException
	 */
	public void modifyOrder(long id, String address, List<OrderProductParams> productList) {
		//id값으로 주문 객체를 가져옴
		Order order = orderRepository.findById(id).orElseThrow(ProductNotFoundException::new);
		
		//요청한 회원의 주문 내용을 수정하는지 체크함
		requestUserCheckByOrder(order);
				
		//배송지 값이 null이 아닐 때만 배송지를 수정
		if(address != null) {			
			order.getDelivery().setAddress(address);
		}
		
		//상품리스트 값이 null이 아닐 때만 상품 리스트를 수정
		if(productList != null) {
			//원래 있던 주문상품들을 없앰
			order.setOrderItems(new ArrayList<OrderItem>());
			
			//새로 입력받은 상품들을 추가해줌
			for(OrderProductParams productParams : productList) {
				
				Product product = productService.findProduct(productParams.getProductId());
				
				OrderItem orderItem = OrderItem.builder().product(product)
					.count(productParams.getProductCount())
					.orderPrice(product.getPrice() * productParams.getProductCount())
					.build();
				
				order.addOrderItem(orderItem);
			}
		}
		
		//수정
		orderRepository.save(order);
	}

	/**
	 * 주문 삭제
	 * 
	 * @param long id
	 * @return 
	 */
	public void deleteOrder(long id) {
		//id값으로 주문 객체를 가져옴
		Order order = orderRepository.findById(id).orElseThrow(ProductNotFoundException::new);
				
		//요청한 회원의 주문을  삭제하는지 체크함
		requestUserCheckByOrder(order);
		
		//주문상태가 취소가 아니면 감소했던 상품갯수를 다시 증가시킴
		if(!order.getStatus().equals(OrderStatus.CANCEL)) {
			recoverStockQuantity(order);
		}
		
		//삭제 시 주문번호에 맞는 주문이 없으면 OrderNotFoundException 발생
		try {
			orderRepository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new OrderNotFoundException();
		}
	}

	/**
	 * 주문 취소
	 * 
	 * @param long id
	 * @return 
	 * @exception ProductNotFoundException
	 */
	public void cancelOrder(long id) {
		Order order = orderRepository.findById(id).orElseThrow(ProductNotFoundException::new);
		
		//요청한 회원의 주문을  삭제하는지 체크함
		requestUserCheckByOrder(order);
				
		//차감됬던 상품갯수를 다시 증가시켜줌
		recoverStockQuantity(order);
		
		order.setStatus(OrderStatus.CANCEL);
		
		orderRepository.save(order);
	}

	/**
	 * 배송상태 변경
	 * 
	 * @param long id
	 * @param String status 
	 * @return
	 * @exception ProductNotFoundException
	 */
	public void orderDeliveryUpdate(long id, String status) {
		Order order = orderRepository.findById(id).orElseThrow(ProductNotFoundException::new);
		
		if(status.equals("delivery")) {
			order.getDelivery().setStatus(DeliveryStatus.DELIVERY);
		}else if(status.equals("complete")) {
			order.getDelivery().setStatus(DeliveryStatus.COMPLETE);
		}
	}
	
	/**
	 * 관리자가 아닌 유저가 다른 회원을 수정하는지 체크
	 * 
	 * @param Order order 
	 * @return
	 * @exception AccessDeniedException
	 */
	public void requestUserCheckByOrder(Order order) {
		User user =  userService.findUser();
		if(order.getUser().getMsrl() !=user.getMsrl() && !userService.checkAuthAdmin(user)) {
			throw new AccessDeniedException("");
		}
	}
	
	/**
	 * 주문 취소, 삭제시에 차감되었던 상품갯수를 증가시킴
	 * 
	 * @param Order order 
	 * @return
	 */
	public void recoverStockQuantity(Order order) {
		for(OrderItem orderItem : order.getOrderItems()) {
			Product product = orderItem.getProduct();
			product.setStockQuantity(product.getStockQuantity() + orderItem.getCount());
		}
	}
}