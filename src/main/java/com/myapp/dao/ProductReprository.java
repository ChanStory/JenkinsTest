package com.myapp.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.object.Product;
public interface ProductReprository extends JpaRepository<Product, Long>
{

}