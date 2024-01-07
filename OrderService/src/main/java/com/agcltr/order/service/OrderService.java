package com.agcltr.order.service;

import java.util.List;

import com.agcltr.order.entity.Cart;
import com.agcltr.order.entity.Order;
import com.agcltr.order.exception.OrderNotFoundException;

public interface OrderService {
	
	List<Order> getAllOrders() throws OrderNotFoundException;
	Order placeOrder(String cartId);
	Order getOrderById(String orderId) throws OrderNotFoundException;
	List<Order> getOrderByUsername(String username) throws OrderNotFoundException;
	void deleteOrder(String orderId) throws OrderNotFoundException;


}
