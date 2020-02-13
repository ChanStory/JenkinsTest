package com.myapp.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.service.ProductService;

/**
 * 상품 관련 컨트롤러
 * @author chans
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
@RestController
@RequestMapping("/product")
public class ProductController {
	private ProductService productService;
	
	/**
	 * 상품 추가
	 * @param body
	 * @return resultJson
	 */
	@PostMapping("/")
	public JSONObject join(@RequestBody Map body) {
		return productService.addProduct(body);
	}
}
