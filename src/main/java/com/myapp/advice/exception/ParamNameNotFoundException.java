package com.myapp.advice.exception;

/**
 * 등록된 유저가 없을 때 발생하는 예외
 * 
 * @author chans
 */
public class ParamNameNotFoundException extends RuntimeException {
    public ParamNameNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
     
    public ParamNameNotFoundException(String msg) {
        super(msg);
    }
     
    public ParamNameNotFoundException() {
        super();
    }
}