package com.agcltr.order.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agcltr.order.entity.Cart;
import com.agcltr.order.entity.Order;
import com.agcltr.order.entity.User;
import com.agcltr.order.exception.OrderNotFoundException;
import com.agcltr.order.external.CartServiceFeignClient;
import com.agcltr.order.external.UserServiceFeignClient;
import com.agcltr.order.repo.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orepo;

	@Autowired
	private CartServiceFeignClient cartFeignClient;

	@Autowired
	private UserServiceFeignClient userFeignClient;

	@Override
	public List<Order> getAllOrders() throws OrderNotFoundException {
		List<Order> list = orepo.findAll();
		if (list.isEmpty()) {
			throw new OrderNotFoundException("Order Not Found");
		}
		return list;
	}

	@Override
	public Order placeOrder(String cartId) {
		Cart c = cartFeignClient.viewCart(cartId);
		User u = userFeignClient.fetchByEmail(c.getUserName());

		Order order = new Order();
		String orderId = UUID.randomUUID().toString();
		order.setId(orderId);
		order.setOrderDate(LocalDate.now());
		order.setOrderStatus("Created");
		order.setUserName(c.getUserName());
		order.setTotalPrice(c.getTotalPrice());
		order.setOrders(c.getItems());
		order.setAddress(u.getAddress());

		cartFeignClient.deleteCart(cartId);
		return orepo.save(order);
	}

	@Override
	public void deleteOrder(String orderId) throws OrderNotFoundException {
		Optional<Order> optionalOrder = orepo.findById(orderId);
		if (optionalOrder.isEmpty()) {
			throw new OrderNotFoundException("No Order found");
		}
		orepo.delete(optionalOrder.get());
	}

	@Override
	public Order getOrderById(String orderId) throws OrderNotFoundException {
		Optional<Order> order = orepo.findById(orderId);
		if (order.isEmpty()) {
			throw new OrderNotFoundException("No Order found");
		}
		return order.get();
	}

	@Override
	public List<Order> getOrderByUsername(String username) throws OrderNotFoundException {

		List<Order> list = orepo.findAllByUserName(username);
		if (list.isEmpty()) {
			throw new OrderNotFoundException("No Orders Found");
		}
		return list;
	}

}
