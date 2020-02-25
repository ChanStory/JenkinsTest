package com.myapp.object;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.myapp.object.OrderItem;
/**
 * 주문 엔티티
 * 
 * @author chans
 */

@Builder // builder를 사용할수 있게 함
@Entity
@Getter @Setter
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성
@Table(name = "ORDERS")
public class Order{
	
	public enum OrderStatus {
		배송준비중,
		배송중,
		배송완료;
    }
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
    private Long id;
	
    @Column(nullable = false, unique = true, length = 30)
    private String uid;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String password;
    
    @Column(nullable = false, length = 100)
    private String name;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    
    private String delivery;
    
    private Date orderDate;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
}