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
import com.myapp.advice.exception.LoginFailedException;
import com.myapp.advice.exception.ParamNameNotFoundException;
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
		String code = encodingProperty("exception.unKnown.code");
		String msg = encodingProperty("exception.unKnown.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
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
		String code = encodingProperty("exception.useNotFound.code");
		String msg = encodingProperty("exception.useNotFound.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
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
		String code = encodingProperty("exception.loginFailed.code");
		String msg = encodingProperty("exception.loginFailed.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
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
		String code = encodingProperty("exception.entryPoint.code");
		String msg = encodingProperty("exception.entryPoint.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
	}
	
	/**
	 * 리소스 접근 권한이 부족할 때 발생하는 예외
	 *  
	 * @param HttpServletRequest request
	 * @param Exception e
	 * @return CommonResult
	 * @responseStatus 401
	 */
	@ExceptionHandler(AccessDeniedException.class) 
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	public CommonResult accessDeniedException(HttpServletRequest request, Exception e) throws UnsupportedEncodingException{
		String code = encodingProperty("exception.accessDenied.code");
		String msg = encodingProperty("exception.accessDenied.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
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
		String code = encodingProperty("exception.methodArgumentNotValid.code");
		String msg = encodingProperty("exception.methodArgumentNotValid.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
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
		String code = encodingProperty("exception.methodArgumentNotValid.code");
		String msg = encodingProperty("exception.methodArgumentNotValid.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
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
		String code = encodingProperty("exception.paramNameNotFound.code");
		String msg = encodingProperty("exception.paramNameNotFound.msg");
		
		if (log.isErrorEnabled()) {
			log.error("exception code : {}, msg : {}", code, msg, e);
		}
		
		return responseService.getFailResult(Integer.parseInt(code), msg);
	}
	
	//이클립스, 스프링부트 인코딩 설정, @propertysource 인코딩 속성을 적용해도 한글이 깨져서 일일히 인코딩 변경 해줌..
	private String encodingProperty(String prop) throws UnsupportedEncodingException {
		return new String(env.getProperty(prop).getBytes("ISO-8859-1"), "UTF-8");
	}
}
