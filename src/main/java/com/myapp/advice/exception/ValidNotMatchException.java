package com.myapp.advice.exception;

/**
 * 파라미터가 유효성검사에 실패할 때 발생하는 예외
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
