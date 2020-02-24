package com.myapp.advice.exception;

/**
 * 등록된 유저가 없을 때 발생하는 예외
 * 
 * @author chans
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
     
    public UserNotFoundException(String msg) {
        super(msg);
    }
     
    public UserNotFoundException() {
        super();
    }
}