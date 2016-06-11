package com.example.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.api.model.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	User getByUsername(String username);
}
