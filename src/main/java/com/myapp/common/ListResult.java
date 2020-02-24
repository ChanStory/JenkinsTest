package com.myapp.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 요청 응답 시 전송되는 결과값
 * 여러개의 데이터가 list에 담겨 반환 됨
 * 
 * @author chans
 */

@Getter
@Setter
public class ListResult<T> extends CommonResult {
    private List<T> list;
}