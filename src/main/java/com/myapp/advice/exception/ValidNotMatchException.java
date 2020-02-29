package com.myapp.advice.exception;

/**
 * 패스워드가 형식에 안맞을 때 발생하는 예외
 * 
 * @author chans
 */
public class ValidNotMatchException extends RuntimeException{
	public ValidNotMatchException(String msg, Throwable t) {
        super(msg, t);
    }
 
    public ValidNotMatchException(String msg) {
        super(msg);
    }
 
    public ValidNotMatchException() {
        super();
    }
}
