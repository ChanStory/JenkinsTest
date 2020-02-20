package com.myapp.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.service.ProductService;
import com.myapp.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"2. Product"})
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class ProductController {
	
	private final ProductService productService;
	
	@ApiOperation(value = "전체 상품 조회", notes = "전체 상품을 조회합니다")
	@GetMapping("/products")
	public JSONObject findAllProdect() throws Exception {
		
		return productService.findAllProduct();
	}
	
	@ApiOperation(value = "조건부 상품 조회", notes = "요청받은 조건으로 상품을 조회합니다")
	@GetMapping("/products/{condition}/{item}")
	public JSONObject findProducts(@ApiParam(value = "조건명", required = true) @PathVariable String condition, 
								   @ApiParam(value = "조건값", required = true) @PathVariable String value) {
		
		return productService.findProducts(condition, value);
	}
	
	@ApiOperation(value = "상품추가", notes = "상품을 추가합니다")
	@PostMapping("/product")
	public JSONObject addProduct() {
		return productService.addProduct();
	}
}