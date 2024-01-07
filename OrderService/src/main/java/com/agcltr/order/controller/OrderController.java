package com.agcltr.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agcltr.order.entity.Order;
import com.agcltr.order.exception.OrderNotFoundException;
import com.agcltr.order.service.OrderServiceImpl;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins="http://localhost:3000")
public class OrderController {
	
	@Autowired
	private OrderServiceImpl oservice;
	
	@GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders() throws OrderNotFoundException {
        List<Order> orders = oservice.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    @PostMapping("/placeOrder/{cartId}")
    public ResponseEntity<Order> placeOrder(@PathVariable String cartId) {
        Order order = oservice.placeOrder(cartId);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
    
    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) throws OrderNotFoundException {
        Order order = oservice.getOrderById(orderId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getOrderByCustomerId/{username}")
    public ResponseEntity<List<Order>> getOrderByCustomerId(@PathVariable String username) throws OrderNotFoundException {
        List<Order> orders = oservice.getOrderByUsername(username);
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }
	

}
