package com.myapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 엔티티
 * 
 * @author chans
 */
@Builder //builder를 사용할수 있게 함
@NoArgsConstructor //인자없는 생성자를 자동으로 생성
@AllArgsConstructor //인자를 모두 갖춘 생성자를 자동으로 생성
@Entity
@Getter @Setter
@Table(name="PRODUCT",
	   uniqueConstraints = { //유니크 제약조건
			@UniqueConstraint(name = "ID_UNIQUE", 
							  columnNames = "PRODUCT_ID")}
)
@SequenceGenerator(
	name = "PRODUCT_SEQ_GENERATOR",
	sequenceName = "PRODUCT_SEQ",
	initialValue = 1, //시퀀스 초기값
	allocationSize = 1 //시퀀스 증가량
)
public class Product {
	
	
	@Id
    @Column(name = "PRODUCT_ID")
	@ApiModelProperty(hidden = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "PRODUCT_SEQ_GENERATOR")
    private Long id; //id
    
    @Column(nullable = false, length = 100)
    private String name; //이름
    
    @Column(nullable = false, length = 100)
    private int price; //가격
    
    @Lob
    private String description; //상품설명
    
    private String imageName; //상품 이미지파일 이름
    
    private String kind; //종류
    
    private String brand; //브랜드
    
    private int stockQuantity; //재고수량
    
    
    @ApiModelProperty(hidden = true)
    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDateTime createdDate = LocalDateTime.now(); //등록일자
    
}