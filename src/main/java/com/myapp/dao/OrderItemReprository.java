package com.myapp.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.object.OrderItem;

/**
 * 주문 상품 Reprository
 * 
 * @author chans
 */

public interface OrderItemReprository extends JpaRepository<OrderItem, Long>
{
	
}