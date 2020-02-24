package com.myapp.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 요청 응답 시 전송되는 결과값
 * 
 * @author chans
 */

@Getter
@Setter
public class CommonResult {
 
    @ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean success;
 
    @ApiModelProperty(value = "응답 코드 번호")
    private int code;
 
    @ApiModelProperty(value = "응답 메시지")
    private String msg;
}