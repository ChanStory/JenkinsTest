package com.myapp.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.service.ProductService;

/**
 * 상품 관련 컨트롤러
 * @author chans
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
@RestController
@RequestMapping("/")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 전체상품 조회
	 * @param body
	 * @return resultJson
	 */
	@GetMapping("/products")
	public JSONObject findAllProdect() {
		
		return productService.findAllProduct();
	}
	
	/**
	 * 상품 추가
	 * @param body
	 * @return resultJson
	 */
	@PostMapping("/product")
	public JSONObject addProduct() {
		return productService.addProduct();
	}
}