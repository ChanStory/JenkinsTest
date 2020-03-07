package com.myapp.dao;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entity.Order;
import com.myapp.entity.User;

/**
 * 주문 관련 Reprository
 * 
 * @author chans
 */

public interface OrderReprository extends JpaRepository<Order, Long> {
	Optional<List<Order>> findByUser(User user);
}