package com.agcltr.order.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.agcltr.order.entity.User;

@FeignClient(url="http://localhost:8083/user" , name="USER-SERVICE")
public interface UserServiceFeignClient {
	
	@GetMapping("/fetchByEmail/{userEmail}")
	User fetchByEmail(@PathVariable String userEmail);

}
