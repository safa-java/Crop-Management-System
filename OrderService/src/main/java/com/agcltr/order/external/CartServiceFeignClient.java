package com.agcltr.order.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.agcltr.order.entity.Cart;

@FeignClient(url="http://localhost:8082/cart" , name="CART-SERVICE")
public interface CartServiceFeignClient {
	
    @GetMapping("/view/{cartId}")
	public Cart viewCart(@PathVariable String cartId);
    
    @DeleteMapping("/delete/{id}")
	public String deleteCart(@PathVariable String id);

}
