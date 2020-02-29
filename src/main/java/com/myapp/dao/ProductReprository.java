package com.myapp.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entity.Product;

/**
 * 상품 관련 Reprository
 * 
 * @author chans
 */

public interface ProductReprository extends JpaRepository<Product, Long>
{
	List<Product> findByKind(String kind);
	List<Product> findAllByOrderByCreatedDateDesc();
}