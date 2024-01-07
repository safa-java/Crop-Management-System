package com.agcltr.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agcltr.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByUserEmail(String userEmail);
	User findByUserName(String username);
	boolean existsByEmail(String email);

}
