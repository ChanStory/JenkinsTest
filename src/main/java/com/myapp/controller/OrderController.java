package com.myapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.common.CommonResult;
import com.myapp.common.ListResult;
import com.myapp.dto.OrderParams;
import com.myapp.dto.OrderProductParams;
import com.myapp.entity.Order;
import com.myapp.service.OrderService;
import com.myapp.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * 주문 관련 컨트롤러
 * 
 * @author chans
 */

@Api(tags = {"4. Order"})
@CrossOrigin(origins = "*", allowedHeaders = "*")//크로스 도메인 해결을 위한 어노테이션
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class OrderController {

	private final ResponseService responseService;
	private final OrderService orderService;
	
	/**
	 * 전체 주문 조회
	 * 
	 * @param X-AUTH-TOKEN
	 * @param 
	 * @return ListResult
	 */
	@ApiOperation(value = "전체 주문 조회", notes = "전체 주문을 조회함")
	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/orders")
	public ListResult<Order> findAllOrders(){
		return responseService.getListResult(orderService.findAllOrders());
	}
	
	/**
	 * 회원 주문내역 조회
	 * 
	 * @param X-AUTH-TOKEN
	 * @param long msrl
	 * @return ListResult
	 */
	@ApiOperation(value = "회원 주문내역 조회", notes = "요청한 회원의 주문내역을 조회함")
	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/orders/{msrl}")
	public ListResult<Order> findOrders(@ApiParam(value = "회원번호", required = true) @PathVariable long msrl) {
		return responseService.getListResult(orderService.findOrders(msrl));
	}
	
	/**
	 * 주문 추가
	 * 
	 * @param X-AUTH-TOKEN
	 * @param String orderUserId
	 * @param String address
	 * @param List<OrderProductParams> productList
	 * @return CommonResult
	 */
	@ApiOperation(value = "주문 추가", notes = "주문을 추가함")
	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PostMapping("/order")
	public CommonResult addOrder(@ApiParam(value = "주문 입력 시 필요한 데이터\n배송지, 주문자id, 상품id, 상품갯수를 입력받는다", required = true) @RequestBody OrderParams orderParams) {
		orderService.addOrder(orderParams);
				
		return responseService.getSuccessResult();
	}
	
	/**
	 * 주문 내용 수정
	 * 
	 * @param X-AUTH-TOKEN
	 * @param long id
	 * @param String address
	 * @param List<OrderProductParams> productList
	 * @return SingleResult
	 */
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "주문 내용 수정", notes = "주문 내용을 수정한다")
    @PutMapping(value = "/order/{id}")
	public CommonResult modifyOrder( @ApiParam(value = "상품번호", required = true) @PathVariable long id,
									   @ApiParam(value = "배송지", required = false) @RequestParam String address,
									   @ApiParam(value = "상품리스트", required = false) @RequestParam List<OrderProductParams> productList) {
    	
    	orderService.modifyOrder(id, address, productList);
    	
        return responseService.getSuccessResult();
    }
    
    /**
	 * 주문 삭제
	 * 
	 * @param X-AUTH-TOKEN
	 * @param long id
	 * @return SingleResult
	 */
    @ApiOperation(value = "주문 삭제", notes = "주문을 삭제한다")
    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
    @DeleteMapping(value = "/order/{id}")
    public CommonResult deleteOrder( @ApiParam(value = "주문번호", required = true) @PathVariable long id) {
    	orderService.deleteOrder(id);
    	
        return responseService.getSuccessResult();
    }
    
    /**
	 * 주문 취소
	 * 
	 * @param X-AUTH-TOKEN
	 * @param long id
	 * @return ListResult
	 */
	@ApiOperation(value = "주문 취소", notes = "주문을 취소함")
	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping("/order/cancel/{id}")
	public CommonResult cancelOrder (@ApiParam(value = "주문번호", required = true) @PathVariable long id){
		orderService.cancelOrder(id);
		
		return responseService.getSuccessResult();
	}
	
	/**
	 * 배송상태 변경
	 * 
	 * @param X-AUTH-TOKEN
	 * @param long id
	 * @return ListResult
	 */
	@ApiOperation(value = "배송상태 변경", notes = "배송상태를 변경함")
	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping("/order/delevery/{status}")
	public CommonResult orderDeliveryUpdate (@ApiParam(value = "주문번호", required = true) @PathVariable long id,
											 @ApiParam(value = "배송상태", required = true) @PathVariable String status){
		
		orderService.orderDeliveryUpdate(id, status);
		
		return responseService.getSuccessResult();
	}
}