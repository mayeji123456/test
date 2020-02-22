package com.springtest.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springtest.data.User;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends CrudRepository<User, Long> {

	public List<User> findByToken(String token);
	
	public List<User> findByAccountid(Long accountid);

}