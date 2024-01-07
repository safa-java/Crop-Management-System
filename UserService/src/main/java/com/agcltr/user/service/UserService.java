package com.agcltr.user.service;

import java.util.List;

import com.agcltr.user.entity.User;
import com.agcltr.user.entity.UserLoginDTO;
import com.agcltr.user.exception.UserNotFoundException;
import com.agcltr.user.exception.WrongPasswordException;

public interface UserService {
		
	public List<User> fetchAllUsers();
	public User fetchUser(int id) throws UserNotFoundException;
	public String addUser(User userInfo);
	public void deleteUserByUsername(String username) throws UserNotFoundException;
	public User login(UserLoginDTO user) throws UserNotFoundException , WrongPasswordException;
	public User updateUserByEmail(User user) throws UserNotFoundException;
	

}
