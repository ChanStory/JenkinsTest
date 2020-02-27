package com.myapp.advice.exception;

/**
 * 패스워드가 형식에 안맞을 때 발생하는 예외
 * 
 * @author chans
 */
public class PasswordNotMatchException extends RuntimeException{
	public PasswordNotMatchException(String msg, Throwable t) {
        super(msg, t);
    }
 
    public PasswordNotMatchException(String msg) {
        super(msg);
    }
 
    public PasswordNotMatchException() {
        super();
    }
}
