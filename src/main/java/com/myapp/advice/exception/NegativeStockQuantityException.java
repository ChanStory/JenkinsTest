package com.myapp.advice.exception;

/**
 * 등록된 유저가 없을 때 발생하는 예외
 * 
 * @author chans
 */
public class NegativeStockQuantityException extends RuntimeException {
    public NegativeStockQuantityException(String msg, Throwable t) {
        super(msg, t);
    }
     
    public NegativeStockQuantityException(String msg) {
        super(msg);
    }
     
    public NegativeStockQuantityException() {
        super();
    }
}