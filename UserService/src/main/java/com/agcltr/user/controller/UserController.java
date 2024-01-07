package com.agcltr.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.agcltr.user.entity.AuthRequest;
import com.agcltr.user.entity.User;
import com.agcltr.user.entity.UserLoginDTO;
import com.agcltr.user.exception.UserNotFoundException;
import com.agcltr.user.exception.WrongPasswordException;
import com.agcltr.user.service.JwtService;
import com.agcltr.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
    private UserService uservice;
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

    

    // User registration
    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        String registrationResponse = uservice.addUser(user);
        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO userLoginDTO) 
    throws UserNotFoundException, WrongPasswordException{
        User loggedInUser = uservice.login(userLoginDTO);
        return new ResponseEntity<>("Logged in succcesfully!", HttpStatus.OK);
   
    }

    // Get user details by email
//    @GetMapping("/details")
//    public ResponseEntity<User> getUserDetails(@RequestParam String email) {
//        try {
//            User user = uservice.;
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        } catch (UserNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/allUsers")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<List<User>>(uservice.fetchAllUsers(), HttpStatus.CREATED);
	}

	@GetMapping("/fetch/{id}")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public User getUser(@PathVariable int id) throws UserNotFoundException {
		return uservice.fetchUser(id);
	}

	@DeleteMapping("/deleteUserByUsername/{username}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FARMER','ROLE_DEALER') or (#username == authentication.name)")
	public ResponseEntity<?> deleteUserByUsername(@PathVariable String username) throws UserNotFoundException {
		uservice.deleteUserByUsername(username);
		return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	}
	@PutMapping("update_user/{userEmail}")
    public ResponseEntity<User> updateUserById(@RequestBody User user) throws UserNotFoundException {
        User usr = uservice.updateUserByEmail(user);
        return new ResponseEntity<User>(usr,HttpStatus.OK);
    }
	
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("Invalid User");
		}
	}
}
