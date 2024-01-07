package com.agcltr.order.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="orders")
public class Order {
	
	@MongoId
	private String id;
	private LocalDate orderDate;
	private String userName;
	private double totalPrice;
	private String modeOfPayment;
	private String orderStatus;
	private Address address;
	
	private List<CartItem> orders = new ArrayList<>();	
	

}
