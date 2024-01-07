package com.agcltr.order.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agcltr.order.entity.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>{

	List<Order> findAllByUserName(String username);
	

}
