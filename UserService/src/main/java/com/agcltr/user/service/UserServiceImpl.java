package com.agcltr.user.service;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.agcltr.user.entity.User;
import com.agcltr.user.entity.UserLoginDTO;
import com.agcltr.user.exception.UserNotFoundException;
import com.agcltr.user.exception.WrongPasswordException;
import com.agcltr.user.repo.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	boolean flag;
	
	private HashSet<String> emailIds = new HashSet<>();

	@Override
	public List<User> fetchAllUsers() {
		return urepo.findAll();
	}

	@Override
	public User fetchUser(int id) throws UserNotFoundException {
		Optional<User> user = urepo.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
	
		
		
	}

	@Override
	public String addUser(User userInfo) {
		
	    if (urepo.existsByEmail(userInfo.getUserEmail())) {
	        return "User already exists";
	    } else {
	        userInfo.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
	        urepo.save(userInfo);
	        return "User saved successfully!";
	    }
	}

	@Override
	public void deleteUserByUsername(String username) throws UserNotFoundException{
		User userToDelete = urepo.findByUserEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
		
		
	}

	@Override
	public User login(UserLoginDTO user) throws UserNotFoundException, WrongPasswordException {
		User existingUser = urepo.findByUserEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + user.getEmail()));

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getUserPassword())) {
            throw new WrongPasswordException("Wrong password for user with email: " + user.getEmail());
        }

        return existingUser;
	
		
	}

	@Override
	public User updateUserByEmail(User user) throws UserNotFoundException {
	
		Optional<User> existingUser = urepo.findByUserEmail(user.getUserEmail());
		if(existingUser.isPresent()) {
			User u = existingUser.get();
	        u.setMobileNumber(user.getMobileNumber());
	        u.setUserName(user.getUserName());
	        u.setAddress(user.getAddress());
	        u.setUserPassword(user.getUserPassword());
	        urepo.save(u);
	        return u;
	    } else {
	        throw new UserNotFoundException("User not found with email: " + user.getUserEmail());
	    }
			
	}
	

}
