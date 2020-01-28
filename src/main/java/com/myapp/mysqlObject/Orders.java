package com.myapp.mysqlObject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@SequenceGenerator(
		name = "ORDER_SEQ_GENERATOR",
		sequenceName = "ORDER_SEQ",
		initialValue = 1, allocationSize = 1
		)
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "ORDER_SEQ_GENERATOR")
	@Column(name = "ORDER_ID")
	private Long Id;
	
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
	private int orderAmount;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	
	public void setMember(Member member) {
    	if(this.member != null) {
    		this.member.getOrders().remove(this);
    	}
    	this.member = member;
    	member.getOrders().add(this);
    }
	
	public void setProduct(Product product) {
    	if(this.product != null) {
    		this.product.getOrders().remove(this);
    	}
    	this.product = product;
    	product.getOrders().add(this);
    }
}
