package com.myapp.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.object.Product;
public interface ProductReprository extends JpaRepository<Product, Long>
{
	List<Product> findByDivision(String division);
}