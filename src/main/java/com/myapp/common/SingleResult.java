package com.myapp.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 요청 응답 시 전송되는 결과값
 * 하나의 데이터가 반환 됨
 * 
 * @author chans
 */


@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    private T data;
}