package com.myapp.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.CUserNotFoundException;
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
		product.setKind("cpu");
		product.setDescription("AMD 라이젠 5 2600 (피나클 릿지) 입니다.");
		product.setBrand("AMD");
		
		productRepository.save(product);
		
		return new JSONObject();
	}

	/**
	 * 상품 전체 조회
	 * @param 
	 * @return resultJson
	 * @throws Exception 
	 */
	public JSONObject findAllProduct() throws Exception {
		if(true) {
			throw new CUserNotFoundException("message");
		}
		JSONObject jsonResult = new JSONObject();
		
		List<Product> productList = productRepository.findAll();
		
		jsonResult.put("productList", productList);
		return jsonResult;
	}

	/**
	 * 조건부 상품 조회
	 * @param String
	 * @return resultJson
	 */
	public JSONObject findProducts(String condition, String value) {
		JSONObject jsonResult = new JSONObject();
		List<Product> productList = null;
		
		if(condition.equals("kind")) {
			productList = productRepository.findByKind(value);
			
		}else if(condition.equals("count")) {
			productList = productRepository.findAllByOrderByCreatedDateDesc();
			
			//조회된 productList 의 개수가 요청한 개수보다 많을경우 요청한 개수만큼만 반환해줌
			if(Integer.parseInt(value) < productList.size()) {
				productList = productList.subList(0, Integer.parseInt(value));
			}
		}
		
		jsonResult.put("productList", productList);
		System.out.println(jsonResult);
		return jsonResult;
	}
	
}