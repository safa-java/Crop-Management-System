package com.agcltr.cart.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.agcltr.cart.entity.Crops;

@FeignClient(url="http://localhost:8081/crops/", name="CROP-SERVICE")
public interface CropServiceFeignClient {
	
	@GetMapping("/getCropById/{cropId}")
    public Crops getCropById(@PathVariable String cropId);
	
	@PutMapping("/updateCrop/{id}")
    public Crops updateCrops(@PathVariable String id, @RequestBody Crops updatedCrops);

}
