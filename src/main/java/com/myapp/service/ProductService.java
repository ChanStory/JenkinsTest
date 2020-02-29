package com.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.dao.ProductReprository;
import com.myapp.entity.Product;



/**
 * 상품 관련 서비스
 * 
 * @author chans
 */
@Service
public class ProductService {

	@Autowired
	private ProductReprository productRepository;
	
	
	/**
	 * 상품추가
	 * @param 
	 * @return 
	 */
	public void addProduct() {
		
		Product product = new Product();
		product.setName("AMD 라이젠 5 2600 (피나클 릿지)");
		product.setPrice(133390);
		product.setImageName("imageTest2.jpg");
		product.setKind("cpu");
		product.setDescription("AMD 라이젠 5 2600 (피나클 릿지) 입니다.");
		product.setBrand("AMD");
		
		productRepository.save(product);
		
	}

	/**
	 * 조건부 상품 조회
	 * @param String condition
	 * @param String value
	 * @return List<Product>
	 */
	public List<Product> findProducts(String condition, String value) {
		List<Product> productList = null;
		
		if(condition.equals("kind")) {
			return productRepository.findByKind(value);
			
		}else if(condition.equals("count")) {
			productList = productRepository.findAllByOrderByCreatedDateDesc();
			
			//조회된 productList 의 개수가 요청한 개수보다 많을경우 요청한 개수만큼만 반환해줌
			if(Integer.parseInt(value) < productList.size()) {
				return productList.subList(0, Integer.parseInt(value));
			}
		}
		
		return productList;
	}
	
}