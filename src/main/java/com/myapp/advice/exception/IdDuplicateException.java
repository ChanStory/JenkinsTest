package com.myapp.advice.exception;

/**
 * 등록된 유저가 없을 때 발생하는 예외
 * 
 * @author chans
 */
public class IdDuplicateException extends RuntimeException {
    public IdDuplicateException(String msg, Throwable t) {
        super(msg, t);
    }
     
    public IdDuplicateException(String msg) {
        super(msg);
    }
     
    public IdDuplicateException() {
        super();
    }
}