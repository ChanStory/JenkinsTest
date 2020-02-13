package com.myapp.service;

import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.dao.ProductReprository;
import com.myapp.dao.UserRepository;
import com.myapp.object.User;


/**
 * 상품 관련 서비스
 * @author chans
 */
@Service
public class ProductService {

	@Autowired
	private ProductReprository productRepository;
	
	
	/**
	 * 로그인
	 * @param loginMap
	 * @return resultJson
	 */
	public JSONObject addProduct(Map productMap) {
		return new JSONObject();
	}
	
}