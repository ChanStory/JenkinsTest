package com.myapp.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entity.Order;

/**
 * 주문 관련 Reprository
 * 
 * @author chans
 */

public interface OrderReprository extends JpaRepository<Order, Long>
{
	
}