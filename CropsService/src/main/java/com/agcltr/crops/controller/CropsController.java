package com.agcltr.crops.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agcltr.crops.entity.Crops;
import com.agcltr.crops.exception.CropNotFoundException;
import com.agcltr.crops.service.CropsServiceImpl;

@RestController
@RequestMapping("/crops")
@CrossOrigin(origins = "http://localhost:3000")
public class CropsController {
	
	@Autowired
	private CropsServiceImpl cservice;
	

	    @PostMapping
	    public ResponseEntity<Crops> createCrops(@RequestBody Crops crops) {
	        Crops createdCrops = cservice.createCrops(crops);
	        return new ResponseEntity<>(createdCrops, HttpStatus.CREATED);
	    }

	    @GetMapping("/allcrops")
	    public ResponseEntity<List<Crops>> getAllCrops() {
	        List<Crops> allCrops = cservice.getAllCrops();
	        return new ResponseEntity<>(allCrops, HttpStatus.OK);
	    }

	    @GetMapping("/search")
	    public ResponseEntity<List<Crops>> getCropsByName(@RequestParam String name) {
	        List<Crops> crops = cservice.getCropsByName(name);
	        return new ResponseEntity<>(crops, HttpStatus.OK);
	    }

	    @GetMapping("/getCropByType/{type}")
	    public ResponseEntity<List<Crops>> getCropsByType(@PathVariable String type) {
	        List<Crops> crops = cservice.getCropsByType(type);
	        return new ResponseEntity<>(crops, HttpStatus.OK);
	    }

	    @GetMapping("/getCropById/{cropId}")
	    public ResponseEntity<Crops> getCropById(@PathVariable String cropId) {
	        Crops crops = cservice.getCropById(cropId).orElse(null);
	        if (crops != null) {
	            return new ResponseEntity<>(crops, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @PutMapping("/updateCrop/{id}")
	    public ResponseEntity<Crops> updateCrops(@PathVariable String id, @RequestBody Crops updatedCrops) throws CropNotFoundException {
	        Crops updatedCrop = cservice.updateCrops(id, updatedCrops);
	        if (updatedCrop != null) {
	            return new ResponseEntity<>(updatedCrop, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	    @DeleteMapping("/deleteCrop/{id}")
	    public ResponseEntity<Void> deleteCrops(@PathVariable String id) {
	        cservice.deleteCrops(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	

}
