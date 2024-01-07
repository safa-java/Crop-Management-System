package com.agcltr.crops.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agcltr.crops.entity.Crops;

@Repository
public interface CropsRepository extends MongoRepository<Crops, String>{
	

	public List<Crops> findByCropType(String name);

	public List<Crops> findByCropName(String type);


}
