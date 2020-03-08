package com.myapp.advice.exception;

/**
 * 등록된 유저가 없을 때 발생하는 예외
 * 
 * @author chans
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
     
    public OrderNotFoundException(String msg) {
        super(msg);
    }
     
    public OrderNotFoundException() {
        super();
    }
}