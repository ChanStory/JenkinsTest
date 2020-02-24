package com.myapp.advice.exception;

/**
 * 로그인 실패시 발생하는 예외
 * 
 * @author chans
 */
public class LoginFailedException extends RuntimeException{
	public LoginFailedException(String msg, Throwable t) {
        super(msg, t);
    }
 
    public LoginFailedException(String msg) {
        super(msg);
    }
 
    public LoginFailedException() {
        super();
    }
}
