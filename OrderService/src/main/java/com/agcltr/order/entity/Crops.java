package com.agcltr.order.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Crops {
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
