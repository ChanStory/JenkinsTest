package com.myapp.advice.exception;

/**
 * 로그인 실패시 발생하는 예외
 * 
 * @author chans
 */
public class TokenExpiredException extends RuntimeException{
	public TokenExpiredException(String msg, Throwable t) {
        super(msg, t);
    }
 
    public TokenExpiredException(String msg) {
        super(msg);
    }
 
    public TokenExpiredException() {
        super();
    }
}
