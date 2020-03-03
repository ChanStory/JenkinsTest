package com.myapp.advice.exception;

/**
 * 파라미터가 유효성검사에 실패할 때 발생하는 예외
 * 
 * @author chans
 */
public class ProductNotFoundException extends RuntimeException{
	public ProductNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
 
    public ProductNotFoundException(String msg) {
        super(msg);
    }
 
    public ProductNotFoundException() {
        super();
    }
}
