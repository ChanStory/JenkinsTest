package com.myapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.common.CommonResult;
import com.myapp.common.ListResult;
import com.myapp.common.SingleResult;
import com.myapp.dao.ProductReprository;
import com.myapp.object.Product;
import com.myapp.object.User;
import com.myapp.service.ProductService;
import com.myapp.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * 상품 관련 컨트롤러
 * 
 * @author chans
 */

@Api(tags = {"3. Product"})
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class ProductController {

	private final ResponseService responseService;
	private final ProductService productService;
	private final ProductReprository productRepository;
	
	/**
	 * 전체 상품 조회
	 * 
	 * @param 
	 * @return ListResult
	 */
	@ApiOperation(value = "전체 상품 조회", notes = "전체 상품을 조회합니다")
	@GetMapping("/products")
	public ListResult<Product> findAllProdect() throws Exception {
		return responseService.getListResult(productRepository.findAll());
	}
	
	/**
	 * 조건부 상품 조회
	 * 
	 * @param String condition
	 * @param String value
	 * @return ListResult
	 */
	@ApiOperation(value = "조건부 상품 조회", notes = "요청받은 조건으로 상품을 조회합니다")
	@GetMapping("/products/{condition}/{item}")
	public ListResult<Product> findProducts(@ApiParam(value = "조건명", required = true) @PathVariable String condition, 
								   			@ApiParam(value = "조건값", required = true) @PathVariable String value) {
		
		return responseService.getListResult(productService.findProducts(condition, value));
	}
	
	/**
	 * 상품 추가
	 * 
	 * @param String condition
	 * @param String value
	 * @return CommonResult
	 */
	@ApiOperation(value = "상품추가", notes = "상품을 추가합니다")
	@PostMapping("/product")
	public CommonResult addProduct(@ApiParam(value = "상품명", required = true) @PathVariable String name,
								   @ApiParam(value = "가격", required = true) @PathVariable int price,
								   @ApiParam(value = "상품설명", required = true) @PathVariable String description,
								   @ApiParam(value = "이미지파일명", required = true) @PathVariable String imageName,
								   @ApiParam(value = "상품종류", required = true) @PathVariable String kind,
								   @ApiParam(value = "상품브랜드", required = true) @PathVariable String brand) {
		productService.addProduct();
		
		Product product = new Product();
		product.setName("AMD 라이젠 5 2600 (피나클 릿지)");
		product.setPrice(133390);
		product.setImageName("imageTest2.jpg");
		product.setKind("cpu");
		product.setDescription("AMD 라이젠 5 2600 (피나클 릿지) 입니다.");
		product.setBrand("AMD");
		
		return responseService.getSuccessResult();
	}
}