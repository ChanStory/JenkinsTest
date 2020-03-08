package com.myapp.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@EntityListeners(AuditingEntityListener.class) // JpaAuditing을 사용할수 있게 해줌
@Table(name = "ORDERS")
public class Order{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
    private long id; //id
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private User user; //주문자

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<OrderItem>(); //주문 상품
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery; //배송정보
    
    @CreatedDate
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdDate; //등록일자
    
    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    private LocalDateTime lastModifiedDate; //마지막 수정일자
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태
    
    public enum OrderStatus {
		ORDER, //주문완료
		CANCEL //주문취소
    }

    /**
	 * 주문 제품 추가
	 * OrderItem 객체에도 연관관계 매핑을 위해 객체를 설정해줘야 해서 선언함
	 * 
	 * @param OrderItem orderItem
	 * @return 
	 */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    /**
	 * 배송정보 추가
	 * Delivery 객체에도 연관관계 매핑을 위해 객체를 설정해줘야 해서 선언함
	 * 
	 * @param Delivery delivery
	 * @return 
	 */
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}