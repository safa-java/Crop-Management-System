package com.agcltr.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	
	private String cropId; 
    private String cropName;
    private int quantity;
    private double subTotal;
    
    
}
