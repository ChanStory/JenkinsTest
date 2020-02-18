package com.myapp.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.dao.ProductReprository;
import com.myapp.object.Product;



/**
 * 상품 관련 서비스
 * @author chans
 */
@Service
public class ProductService {

	@Autowired
	private ProductReprository productRepository;
	
	
	/**
	 * 상품추가
	 * @param 
	 * @return resultJson
	 */
	public JSONObject addProduct() {
		
		Product product = new Product();
		product.setName("AMD 라이젠 5 2600 (피나클 릿지)");
		product.setPrice(133390);
		product.setImageName("imageTest2.jpg");
		product.setDivision("cpu");
		product.setDescription("AMD 라이젠 5 2600 (피나클 릿지) 입니다.");
		product.setBrand("AMD");
		
		productRepository.save(product);
		
		return new JSONObject();
	}

	/**
	 * 상품 전체 조회
	 * @param 
	 * @return resultJson
	 */
	public JSONObject findAllProduct() {
		JSONObject jsonResult = new JSONObject();
		
		List<Product> productList = productRepository.findAll();
		
		jsonResult.put("productList", productList);
		System.out.println(jsonResult);
		return jsonResult;
	}

	/**
	 * 조건부 상품 조회
	 * @param String
	 * @return resultJson
	 */
	public JSONObject findProducts(String condition) {
		JSONObject jsonResult = new JSONObject();
		
		List<Product> productList = productRepository.findByDivision(condition);
		
		jsonResult.put("productList", productList);
		System.out.println(jsonResult);
		return jsonResult;
	}
	
}