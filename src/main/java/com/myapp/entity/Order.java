package com.myapp.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.myapp.entity.OrderItem;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
/**
 * 주문 엔티티
 * 
 * @author chans
 */

@Builder //builder를 사용할수 있게 함
@Entity
@Getter @Setter
@NoArgsConstructor //인자없는 생성자를 자동으로 생성
@AllArgsConstructor //인자를 모두 갖춘 생성자를 자동으로 생성
@Table(name = "ORDERS")
public class Order{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
    private Long id; //id
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private User user; //주문자

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<OrderItem>(); //주문 상품
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery; //배송정보
    
    private final Date orderDate = new Date(); //주문시간
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태
    
    public enum OrderStatus {
		ORDER,
		CANCEL
    }
}