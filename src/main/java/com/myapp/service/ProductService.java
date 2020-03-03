package com.myapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.myapp.advice.exception.ParamNameNotFoundException;
import com.myapp.advice.exception.ProductNotFoundException;
import com.myapp.dao.ProductReprository;
import com.myapp.entity.Product;



/**
 * 상품 관련 서비스
 * 
 * @author chans
 */
@Service
public class ProductService {

	@Autowired
	private ProductReprository productRepository;
	
	
	/**
	 * 상품추가
	 * @param Product product
	 * @return 
	 */
	public void addProduct(Product product) {
		
		productRepository.save(product);
		
	}

	/**
	 * 상품 전체 조회
	 * @param 
	 * @return List<Product>
	 */
	public List<Product> findAllProdects() {
		return productRepository.findAll();
	}
	
	/**
	 * 조건부 상품 조회
	 * @param String condition
	 * @param String value
	 * @return List<Product>
	 */
	public List<Product> findProducts(String condition, String value) {
		List<Product> productList = null;
		
		//상품 종류로 구분해 조회
		if(condition.equals("kind")) {
			
			return productRepository.findByKind(value);
			
		//상품을 최신순으로 요청한 갯수만큼 조회
		}else if(condition.equals("count")) {
			productList = productRepository.findAllByOrderByCreatedDateDesc();
			
			//조회된 productList 의 개수가 요청한 개수보다 많을경우 요청한 개수만큼만 반환해줌
			if(Integer.parseInt(value) < productList.size()) {
				return productList.subList(0, Integer.parseInt(value));
			}
			
		//요청한 조건이 맞지 않을때
		}else {
			throw new ParamNameNotFoundException();
		}
		
		return productList;
	}

	/**
	 * 회원 수정
	 * @param long msrl
	 * @param Map<String, String> updateMap
	 * @return 
	 */
	public void modifyProduct(long msrl, Map<String, String> updateMap) {
		Product product = productRepository.findById(msrl).orElseThrow(ProductNotFoundException::new);
		
		//변경을 요청한 값만 변경 해줌
		if(updateMap.get("name") != null) 		   	product.setName(updateMap.get("name"));
		if(updateMap.get("kind") != null) 			product.setKind(updateMap.get("kind"));
		if(updateMap.get("brand") != null) 		   	product.setBrand(updateMap.get("brand"));
		if(updateMap.get("price") != null) 		   	product.setPrice(Integer.parseInt(updateMap.get("price")));
		if(updateMap.get("imageName") != null) 	   	product.setImageName(updateMap.get("imageName"));
		if(updateMap.get("description") != null)   	product.setDescription(updateMap.get("description"));
		if(updateMap.get("stockQuantity") != null) 	product.setStockQuantity(Integer.parseInt(updateMap.get("stockQuantity")));
		
		productRepository.save(product);
	}

	/**
	 * 상품 삭제
	 * @param long msrl
	 * @return 
	 */
	public void deleteProduct(long msrl) {
		//삭제 시 상품번호에 맞는 상품이 없으면 ProductNotFoundException 발생
    	try {
    		productRepository.deleteById(msrl);
		} catch (EmptyResultDataAccessException ex) {
			throw new ProductNotFoundException();
		}
	}
}