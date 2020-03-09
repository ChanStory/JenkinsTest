package com.myapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myapp.advice.exception.NegativeStockQuantityException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문상품 엔티티
 * 
 * @author chans
 */

@Builder //builder를 사용할수 있게 함
@Entity
@Getter @Setter
@NoArgsConstructor //인자없는 생성자를 자동으로 생성
@AllArgsConstructor //인자를 모두 갖춘 생성자를 자동으로 생성
@Table(name = "ORDER_ITEM")
public class OrderItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ITEM_ID")
    private long id; //id
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product; //상품

	@JsonIgnore //Order 객체가 json으로 serialize 될때 무한루프가 돌아서 ignore해줌
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order order; //주문
	
	private int orderPrice; //가격
	
	private int count; //갯수
	
	//product를 set 하면서 상품갯수를 차감함
	public void setProduct(Product product) {
		this.product = product;
		
		if(product.getStockQuantity() - count < 0) {
			throw new NegativeStockQuantityException();
		}
		
		product.setStockQuantity(product.getStockQuantity() - count);
	}
}
