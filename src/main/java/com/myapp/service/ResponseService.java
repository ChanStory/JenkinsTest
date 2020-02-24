package com.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myapp.common.CommonResult;
import com.myapp.common.ListResult;
import com.myapp.common.SingleResult;

/**
 * 요청에대한 응답을 해주는 서비스
 * 
 * @author chans
 */

@Service
public class ResponseService {
 
    //api 요청 결과에 대한 code, message를 정의
    public enum CommonResponse {
        SUCCESS(0, "성공하였습니디."),
        FAIL(-1, "실패하였습니다.");
 
        int code;
        String msg;
 
        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
 
        public int getCode() {
            return code;
        }
 
        public String getMsg() {
            return msg;
        }
    }
    
    /**
	 * 단일건 결과를 처리하는 메서드
	 * 
	 * @param T data
	 * @return SingleResult<T>
	 */
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
   
    /**
	 * 다중건 결과를 처리하는 메서드
	 * 
	 * @param List<T>
	 * @return ListResult<T>
	 */
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    
    /**
	 * 성공 결과만 처리하는 메서드
	 * 
	 * @param 
	 * @return CommonResult
	 */
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }
    
    /**
	 * 실패 결과만 처리하는 메서드
	 * 
	 * @param int code
	 * @param String msg
	 * @return CommonResult
	 */
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    
    /**
	 * 결과 모델에 api 요청 성공 데이터를 세팅해주는 메서드
	 * 
	 * @param CommonResult result
	 * @return 
	 */
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}