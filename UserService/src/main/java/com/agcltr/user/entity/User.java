package com.agcltr.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
	
	@Id
	private int id;
    private String userName;
    private String userEmail;
	private String userPassword;
	private Long   mobileNumber;
	private String role;
	
	private Address address;
	
	

}
