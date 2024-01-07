package com.agcltr.cart.service;

import java.util.List;

import com.agcltr.cart.entity.Cart;
import com.agcltr.cart.entity.CartItem;
import com.agcltr.cart.exception.CartNotFoundException;
import com.agcltr.cart.exception.NotExistsException;

public interface CartService {
	
	double calculateTotalCost(Cart cart);
	
	List<Cart> getCartItems();
	Cart viewCart(String id) throws CartNotFoundException;
	String deleteCart(String id) throws CartNotFoundException;
	Cart viewByUsername(String username) throws CartNotFoundException;
	Cart addToCart(CartItem item, String cropId, String userName) throws NotExistsException;
	Cart updateCart(String id, Cart cart) throws CartNotFoundException;
	List<Cart> viewAllCarts();
	Cart removeFromCart(String cartId, String cropId, int quantity) throws NotExistsException;

}
