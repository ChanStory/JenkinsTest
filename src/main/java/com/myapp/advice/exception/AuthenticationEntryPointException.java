package com.myapp.advice.exception;

/**
 * 리소스 접근 권한 자체가 없을 때 발생하는 예외
 * 
 * @author chans
 */
public class AuthenticationEntryPointException extends RuntimeException {
    public AuthenticationEntryPointException(String msg, Throwable t) {
        super(msg, t);
    }
 
    public AuthenticationEntryPointException(String msg) {
        super(msg);
    }
 
    public AuthenticationEntryPointException() {
        super();
    }
}