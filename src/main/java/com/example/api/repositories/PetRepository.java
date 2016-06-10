package com.example.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.api.model.Pet;

public interface PetRepository extends CrudRepository<Pet, Long>
{
	Pet getById(Long id);

	Pet getByNameAndRaseAndOwner(String petName, String petRase, String petOwner);
}
