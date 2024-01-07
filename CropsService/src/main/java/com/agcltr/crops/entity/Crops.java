package com.agcltr.crops.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("crops")
public class Crops {

	@MongoId
	private String id;
	
	private String cropName;
	
	private String farmName;
	
	private String cropType;
	
	private int stockAvailable;
	
	private String farmLocation;
	
	private double pricePerKg;
	
	private Long contact;
	
	private String image;
	

}

