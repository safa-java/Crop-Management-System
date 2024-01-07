package com.agcltr.cart.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agcltr.cart.entity.Cart;
import com.agcltr.cart.entity.CartItem;

@Repository
public interface CartRepository extends MongoRepository<Cart, String>{
	Cart findByUserName(String userName);
}
