package com.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.ParamNameNotFoundException;
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
	 * @param Product product
	 * @return 
	 */
	public void addProduct(Product product) {
		
		productRepository.save(product);
		
	}

	/**
	 * 상품 전체 조회
	 * @param 
	 * @return List<Product>
	 */
	public List<Product> findAllProdects() {
		return productRepository.findAll();
	}
	
	/**
	 * 조건부 상품 조회
	 * @param String condition
	 * @param String value
	 * @return List<Product>
	 */
	public List<Product> findProducts(String condition, String value) {
		List<Product> productList = null;
		
		//상품 종류로 구분해 조회
		if(condition.equals("kind")) {
			
			return productRepository.findByKind(value);
			
		//상품을 최신순으로 요청한 갯수만큼 조회
		}else if(condition.equals("count")) {
			productList = productRepository.findAllByOrderByCreatedDateDesc();
			
			//조회된 productList 의 개수가 요청한 개수보다 많을경우 요청한 개수만큼만 반환해줌
			if(Integer.parseInt(value) < productList.size()) {
				return productList.subList(0, Integer.parseInt(value));
			}
			
		//요청한 조건이 맞지 않을때
		}else {
			throw new ParamNameNotFoundException();
		}
		
		return productList;
	}
	
	
	
}