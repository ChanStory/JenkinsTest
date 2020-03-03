package com.myapp.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 상품 엔티티
 * 
 * @author chans
 */
@NoArgsConstructor //인자없는 생성자를 자동으로 생성
@AllArgsConstructor //인자를 모두 갖춘 생성자를 자동으로 생성
@EntityListeners(AuditingEntityListener.class) // JpaAuditing을 사용할수 있게 해줌
@Entity
@Getter @Setter
@ToString
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
    private long id; //id
    
    @Column(nullable = false)
    private String name; //이름
    
    @Column(nullable = false)
    private int price; //가격
    
    @Lob
    @Column(nullable = false)
    private String description; //상품설명
    
    private String imageName; //상품 이미지파일 이름
    
    @Column(nullable = false)
    private String kind; //종류
    
    @Column(nullable = false)
    private String brand; //브랜드
    
    @Column(nullable = false)
    private int stockQuantity; //재고수량
    
    @CreatedDate
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdDate; //등록일자
    
    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    private LocalDateTime lastModifiedDate; //마지막 수정일자
}