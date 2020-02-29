package com.myapp.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.entity.Delivery;

/**
 * 배송정보 Reprository
 * 
 * @author chans
 */

public interface DeliveryReprository extends JpaRepository<Delivery, Long>
{
	
}