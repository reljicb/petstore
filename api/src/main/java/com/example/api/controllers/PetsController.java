package com.example.api.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ApiInputException;
import com.example.api.model.Pet;
import com.example.api.repositories.PetRepository;
import com.google.common.collect.Lists;

@RestController
public class PetsController extends AbstractApiController
{
	protected static final String PETS = "/pets";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PetRepository petRepo;

	@RequestMapping(value = PETS,
	      method = RequestMethod.GET,
	      produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Pet>> get()
	      throws ApiInputException
	{
		log.debug("GET pets");

		List<Pet> allPets = Lists.newArrayList(petRepo.findAll());

		return ResponseEntity.ok(allPets);
	}
}
