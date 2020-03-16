package com.myapp.advice;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myapp.advice.exception.AuthenticationEntryPointException;
import com.myapp.advice.exception.IdDuplicateException;
import com.myapp.advice.exception.LoginFailedException;
import com.myapp.advice.exception.NegativeStockQuantityException;
import com.myapp.advice.exception.OrderNotFoundException;
import com.myapp.advice.exception.ParamNameNotFoundException;
import com.myapp.advice.exception.ProductNotFoundException;
import com.myapp.advice.exception.TokenExpiredException;
import com.myapp.advice.exception.UserNotFoundException;
import com.myapp.advice.exception.ValidNotMatchException;
import com.myapp.common.CommonResult;
import com.myapp.service.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 에러로깅, 예외처리 클래스
 * 
 * @author chans
 */

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class AfterThrowingAdvice {
	
	private final Environment env;
	
	private final ResponseService responseService;
	
	/**
	 * 일반적인 예외
	 * 
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 500
	 */
	@ExceptionHandler(Exception.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult defaultException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException {
		return exceptionResult("exception.unKnown.code", "exception.unKnown.msg", e);
	}
	
	/**
	 * 등록된 유저가 없을 때 발생하는 예외
	 * 
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 401
	 */
	@ExceptionHandler(UserNotFoundException.class) 
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	public CommonResult userNotFoundException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.userNotFound.code", "exception.userNotFound.msg", e);
	}
	
	/**
	 * id중복체크 시 중복된 id가 있으면 발생하는 예외
	 * 
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 409
	 */
	@ExceptionHandler(IdDuplicateException.class) 
    @ResponseStatus(HttpStatus.CONFLICT)
	public CommonResult idDuplicateException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.idDuplicate.code", "exception.idDuplicate.msg", e);
	}
	
	/**
	 * 로그인 실패 예외
	 * 
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 401
	 */
	@ExceptionHandler(LoginFailedException.class) 
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	public CommonResult loginFailedException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.loginFailed.code", "exception.loginFailed.msg", e);
	}
	
	/**
	 * 리소스 접근 권한 자체가 없을 때 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 401
	 */
	@ExceptionHandler(AuthenticationEntryPointException.class) 
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	public CommonResult authConfigException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.entryPoint.code", "exception.entryPoint.msg", e);
	}
	
	/**
	 * 리소스 접근 권한이 부족할 때 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 403
	 */
	@ExceptionHandler(AccessDeniedException.class) 
    @ResponseStatus(HttpStatus.FORBIDDEN)
	public CommonResult accessDeniedException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.accessDenied.code", "exception.accessDenied.msg", e);
	}
	
	/**
	 * 회원가입 시 입력받은 데이터의 유효성검사가 실패하면 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 400
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)  
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult methodArgumentNotValidException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.methodArgumentNotValid.code", "exception.methodArgumentNotValid.msg", e);
	}
	
	/**
	 * 입력받은 값의 유효성검사가 실패하면 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 400
	 */
	@ExceptionHandler(ValidNotMatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult passwordNotMatchException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.methodArgumentNotValid.code", "exception.methodArgumentNotValid.msg", e);
	}
	
	/**
	 * 입력받은 요청 변수의 이름이 잘못된 경우 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 400
	 */
	@ExceptionHandler(ParamNameNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult paramNameNotFoundException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.paramNameNotFound.code", "exception.paramNameNotFound.msg", e);
	}
	
	/**
	 * 조회한 상품이 존재하지 않을 때 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 400
	 */
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult productNotFoundException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.productNotFound.code", "exception.productNotFound.msg", e);
	}
	
	/**
	 * 조회한 주문이 존재하지 않을 때 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 400
	 */
	@ExceptionHandler(OrderNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult orderNotFoundException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		return exceptionResult("exception.orderNotFound.code", "exception.orderNotFound.msg", e);
	}
	
	/**
	 * 구매하려는 상품의 갯수가 부족할 때 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 400
	 */
	@ExceptionHandler(NegativeStockQuantityException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public CommonResult NegativeStockQuantityException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{		
		return exceptionResult("exception.negativeStockQuantity.code", "exception.negativeStockQuantity.msg", e);
	}
	
	/**
	 * 구매하려는 상품의 갯수가 부족할 때 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 401
	 */
	@ExceptionHandler(TokenExpiredException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public CommonResult TokenExpiredException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{		
		return exceptionResult("exception.tokenExpired.code", "exception.tokenExpired.msg", e);
	}
	
	/**
	 * 에러 로깅, result처리
	 *  
	 * @param String code
	 * @param String msg
	 * @param Exception e
	 * @return CommonResult
	 */
	private CommonResult exceptionResult(String code, String msg, Exception e) throws UnsupportedEncodingException {
		String encodingCode = encodingProperty(code);
		String encodingMsg = encodingProperty(msg);
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", encodingCode, encodingMsg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(encodingCode), encodingMsg);
	}
	
	/**
	 * 에러코드, 메세지 인코딩
	 *  
	 * @param String prop
	 * @return String
	 */
	private String encodingProperty(String prop) throws UnsupportedEncodingException {
		//이클립스, 스프링부트 인코딩 설정, @propertysource 인코딩 속성을 적용해도 한글이 깨져서 일일히 인코딩 변경 해줌..
		return new String(env.getProperty(prop).getBytes("ISO-8859-1"), "UTF-8");
	}
}
