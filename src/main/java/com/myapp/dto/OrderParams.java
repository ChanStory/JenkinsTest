package com.myapp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문 관련 DTO
 * 
 * @author chans
 */
@Getter @Setter
@NoArgsConstructor //인자없는 생성자를 자동으로 생성
@AllArgsConstructor //인자를 모두 갖춘 생성자를 자동으로 생성
public class OrderParams {

	private String orderUserId; //주문자 id
	
	private String address; //배송지
	
	private List<OrderProductParams> productList; //상품정보
	
}