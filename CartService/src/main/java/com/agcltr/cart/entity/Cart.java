package com.agcltr.cart.entity;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "cart")
public class Cart {
	
    @MongoId
    private String id; 
    
    private String userName;

    private List<CartItem> items = new ArrayList<>();
    
    private double totalPrice;

    
}


