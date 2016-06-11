package com.example.api.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	protected static final String PETS = API_PATH + "/pets";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PetRepository petRepo;

	@PostConstruct
	public void postConstruct()
	{
		petRepo.save(Lists.newArrayList(
		      new Pet("Miuwah", "rabbit", "Alice")
		            .setColor("gray/white/beige"),
		      new Pet("Coco", "parot", "Bojan")
		            .setColor("yellow")
		            .setSkill("says hallo")));
	}

	@CrossOrigin()
	@RequestMapping(value = PETS,
	      method = RequestMethod.GET,
	      produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Pet>> get(final HttpServletRequest request)
	      throws ApiInputException, ServletException
	{
		log.debug("GET pets");

		UserController.checkAuthorization(UserController.ROLE_USER, request);

		List<Pet> allPets = Lists.newArrayList(petRepo.findAll());

		return ResponseEntity.ok(allPets);
	}
}
