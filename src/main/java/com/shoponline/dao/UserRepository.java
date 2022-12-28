package com.shoponline.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoponline.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	/**
	 * This function will return a User,
	 * if exists mapped with the passed email.
	 * @param userEmail
	 * @return
	 */
	@Query("Select u from User u where u.userEmail = :userEmail")
	Optional<User> findByUserEmail(@Param("userEmail") String userEmail);
}
