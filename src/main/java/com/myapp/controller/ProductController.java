package com.myapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.common.CommonResult;
import com.myapp.common.ListResult;
import com.myapp.common.SingleResult;
import com.myapp.entity.Product;
import com.myapp.service.ProductService;
import com.myapp.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	
	/**
	 * 전체 상품 조회
	 * 
	 * @param 
	 * @return ListResult
	 */
	@ApiOperation(value = "전체 상품 조회", notes = "전체 상품을 조회함")
	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/products")
	public ListResult<Product> findAllProdects() throws Exception {
		return responseService.getListResult(productService.findAllProdects());
	}
	
	/**
	 * 조건부 상품 조회
	 * 
	 * @param String condition
	 * @param String value
	 * @return ListResult
	 */
	@ApiOperation(value = "조건부 상품 조회", notes = "요청받은 조건으로 상품을 조회함")
	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/products/{condition}/{value}")
	public ListResult<Product> findProducts(@ApiParam(value = "조건명", required = true) @PathVariable String condition, 
								   			@ApiParam(value = "조건값", required = true) @PathVariable String value) {
		
		return responseService.getListResult(productService.findProducts(condition, value));
	}
	
	/**
	 * 상품 추가
	 * 
	 * @param String name
	 * @param int price
	 * @param String description
	 * @param String imageName
	 * @param String kind
	 * @param String brand
	 * @param int stockQuantity
	 * @return CommonResult
	 */
	@ApiOperation(value = "상품추가", notes = "상품을 추가함")
	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PostMapping("/product")
	public CommonResult addProduct(@ApiParam(value = "상품 입력 시 필요한 데이터", required = true) @RequestBody Product product) {
		productService.addProduct(product);
				
		return responseService.getSuccessResult();
	}
}