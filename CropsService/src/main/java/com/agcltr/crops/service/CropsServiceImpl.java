package com.agcltr.crops.service;

import org.springframework.stereotype.Service;

import com.agcltr.crops.entity.Crops;
import com.agcltr.crops.exception.CropNotFoundException;
import com.agcltr.crops.repo.CropsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CropsServiceImpl implements CropsServiceI {

	@Autowired
    private CropsRepository cropsRepo;

   
    
    public Crops createCrops(Crops crops) {
    	String cropId = UUID.randomUUID().toString();
		crops.setId(cropId);
        return cropsRepo.save(crops);
    }

    
    public List<Crops> getAllCrops() {
        return cropsRepo.findAll();
    }

    public Optional<Crops> getCropById(String cropId) {
        return cropsRepo.findById(cropId);
    }
    public List<Crops> getCropsByType(String type) {
        return cropsRepo.findByCropType(type);
    }

    public List<Crops> getCropsByName(String name) {
        return cropsRepo.findByCropName(name);
    }

   
    public Crops updateCrops(String id, Crops updatedCrops) throws CropNotFoundException {
        Optional<Crops> existingCrops = cropsRepo.findById(id);
        if (existingCrops.isPresent()) {
            Crops crops = existingCrops.get();
            crops.setCropName(updatedCrops.getCropName());
            crops.setFarmName(updatedCrops.getFarmName());
            crops.setCropType(updatedCrops.getCropType());
            crops.setStockAvailable(updatedCrops.getStockAvailable());
            crops.setFarmLocation(updatedCrops.getFarmLocation());
            crops.setPricePerKg(updatedCrops.getPricePerKg());
            crops.setContact(updatedCrops.getContact());
           crops.setImage(updatedCrops.getImage());            
            return cropsRepo.save(crops);
        } else {
            throw new CropNotFoundException("Crop with id "+ id+" is not available!");
        }
    }

   
	    public void deleteCrops(String id) {
	        cropsRepo.deleteById(id);
	    }
	
//update quantity method to update the quantity after the order is placed

}

