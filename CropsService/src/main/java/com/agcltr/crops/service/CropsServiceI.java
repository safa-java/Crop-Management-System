package com.agcltr.crops.service;

import java.util.List;
import java.util.Optional;

import com.agcltr.crops.entity.Crops;
import com.agcltr.crops.exception.CropNotFoundException;

public interface CropsServiceI {
	
	 Crops createCrops(Crops crops);
	 List<Crops> getAllCrops();
	 Optional<Crops> getCropById(String cropId);
	 List<Crops> getCropsByName(String name);
	 List<Crops> getCropsByType(String type);
	 Crops updateCrops(String id, Crops updatedCrops) throws CropNotFoundException;
	 void deleteCrops(String id);
	

}
